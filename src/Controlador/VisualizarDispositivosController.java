/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.ConexionBD;
import Modelo.Dispositivo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * FXML Controller class
 *
 * @author Mi PC
 */
public class VisualizarDispositivosController implements Initializable {
        @FXML
    private Button btnInicio, btnvolver;
    @FXML
    private void InicioAction(ActionEvent event) {
        
    if ("admin".equals(Main.tipoUsuario)) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    } else if ("empleado".equals(Main.tipoUsuario)) {
        Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
    }
}
    
    @FXML
    private TableView<Dispositivo> tableDispositivos;

    @FXML
    private TableColumn<Dispositivo, Integer> colId;
    @FXML
    private TableColumn<Dispositivo, String> colNombreCliente;
    @FXML
    private TableColumn<Dispositivo, String> colNombreDispositivo;
    @FXML
    private TableColumn<Dispositivo, String> colMarcaDispositivo;
    @FXML
    private TableColumn<Dispositivo, String> colDañoDispositivo;
    @FXML
    private TableColumn<Dispositivo, String> colEmpleadoAsignado;
    
    
     ObservableList<Dispositivo> listaDispositivos = FXCollections.observableArrayList();
     
   private void cargarDispositivos() {
    String sql = """
        SELECT d.id_dispositivo,
               d.NombreCliente,
               d.NombreDispositivo,
               d.MarcaDispositivo,
               d.DañoDispositivo,
               COALESCE(e.nombre, 'Sin asignar') AS EmpleadoAsignado
        FROM dispositivos d
        LEFT JOIN reparaciones r ON d.id_dispositivo = r.id_dispositivo
        LEFT JOIN empleados e ON r.id_empleado = e.id_empleado;
    """;

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        listaDispositivos.clear();

        while (rs.next()) {
            Dispositivo disp = new Dispositivo(
                rs.getInt("id_dispositivo"),
                rs.getString("NombreCliente"),
                rs.getString("NombreDispositivo"),
                rs.getString("MarcaDispositivo"),
                rs.getString("DañoDispositivo"),
                rs.getString("EmpleadoAsignado")
            );
            listaDispositivos.add(disp);
        }

        colId.setCellValueFactory(new PropertyValueFactory<>("idDispositivo"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        colMarcaDispositivo.setCellValueFactory(new PropertyValueFactory<>("marcaDispositivo"));
        colDañoDispositivo.setCellValueFactory(new PropertyValueFactory<>("dañoDispositivo"));
        colEmpleadoAsignado.setCellValueFactory(new PropertyValueFactory<>("empleadoAsignado"));

        tableDispositivos.setItems(listaDispositivos);

    } catch (Exception e) {
        e.printStackTrace();
    }
}

    
   @Override
public void initialize(URL url, ResourceBundle rb) {
   
    tableDispositivos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);


    colId.setStyle("-fx-alignment: CENTER;");
    colNombreCliente.setStyle("-fx-alignment: CENTER;");
    colNombreDispositivo.setStyle("-fx-alignment: CENTER;");
    colMarcaDispositivo.setStyle("-fx-alignment: CENTER;");
    colDañoDispositivo.setStyle("-fx-alignment: CENTER;");
    colEmpleadoAsignado.setStyle("-fx-alignment: CENTER;");

    // 🔹 Cargar datos desde la BD
    cargarDispositivos();
}

        @FXML
    private void volverAction(ActionEvent event){
        if ("admin".equals(Main.tipoUsuario)) {
    Main.changeScene("Dispositivos.fxml", "Dispositivos");
}else if("empleado".equals(Main.tipoUsuario))
     Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");  
    }


}
