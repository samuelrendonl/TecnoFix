/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;
import Modelo.ConexionBD;
import Modelo.Empleado;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Mi PC
 */
public class VisualizarEmpleadosController implements Initializable {
    @FXML
    private Button btnInicio, btnvolver;
    @FXML
    private TableView<Empleado> tablaEmpleados;
    @FXML
    private TableColumn<Empleado, Integer> colId;
    @FXML
    private TableColumn<Empleado, String> colNombre;
    @FXML
    private TableColumn<Empleado, String> colUsuario;
    @FXML
    private TableColumn<Empleado, String> colRol;
    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("GestionDeTrabajadores.fxml", "Gestor De Trabajadores");  
    }
    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }
    
     @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarEmpleados();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idEmpleado"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colUsuario.setCellValueFactory(new PropertyValueFactory<>("usuario"));
        colRol.setCellValueFactory(new PropertyValueFactory<>("rol"));
    }

    private void cargarEmpleados() {
        listaEmpleados.clear();
        String sql = "SELECT id_empleado, nombre, usuario, rol FROM empleados";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Empleado emp = new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("rol")
                );
                listaEmpleados.add(emp);
            }

            tablaEmpleados.setItems(listaEmpleados);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar", 
                    "No se pudieron cargar los empleados: " + e.getMessage());
        }
    }

    
        private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

