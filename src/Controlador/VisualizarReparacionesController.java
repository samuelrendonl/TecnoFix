package Controlador;

import Modelo.ConexionBD;
import Modelo.Reparacion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

public class VisualizarReparacionesController implements Initializable {

    @FXML
    private Button btnInicio, btnvolver;
    @FXML
    private TableView<Reparacion> tablareparaciones;
    @FXML
    private TableColumn<Reparacion, Integer> colIdReparacion;
    @FXML
    private TableColumn<Reparacion, String> colFechaRecepcion;
    @FXML
    private TableColumn<Reparacion, String> colDescripcion;
    @FXML
    private TableColumn<Reparacion, String> colEstado;
    @FXML
    private TableColumn<Reparacion, String> colNombreCliente;
    @FXML
    private TableColumn<Reparacion, String> colNombreDispositivo;
    @FXML
    private TableColumn<Reparacion, String> colNombreEmpleado;

    private ObservableList<Reparacion> listaReparaciones;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarReparaciones();
    }

    private void configurarColumnas() {
        colIdReparacion.setCellValueFactory(new PropertyValueFactory<>("idReparacion"));
        colFechaRecepcion.setCellValueFactory(new PropertyValueFactory<>("fechaRecepcion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));
    }

private void cargarReparaciones() {
    listaReparaciones = FXCollections.observableArrayList();

    String sql = "SELECT r.id_reparacion, r.Fecha_Recepcion, r.descripcion_problema, r.Estado, " +
                 "d.NombreCliente, d.NombreDispositivo, d.EmpleadoAsignado " +
                 "FROM reparaciones r " +
                 "INNER JOIN dispositivos d ON r.id_dispositivo = d.id_dispositivo";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        int contador = 0;
        while (rs.next()) {
            Reparacion rep = new Reparacion(
                    rs.getInt("id_reparacion"),
                    rs.getString("Fecha_Recepcion"),
                    rs.getString("descripcion_problema"),
                    rs.getString("Estado"),
                    rs.getString("NombreCliente"),
                    rs.getString("NombreDispositivo"),
                    rs.getString("EmpleadoAsignado")
            );
            listaReparaciones.add(rep);
            contador++;
        }

        tablareparaciones.setItems(listaReparaciones);
        System.out.println("Reparaciones cargadas: " + contador);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}



    
        
        @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("GestionDeReparaciones.fxml", "Gestor De Reparaciones");  
    }
    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }
    
}


