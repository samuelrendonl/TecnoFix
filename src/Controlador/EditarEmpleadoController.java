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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EditarEmpleadoController implements Initializable {

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
    @FXML
    private Button btnEditarEmpleado;
    @FXML
    private Button btnInicio;

    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    @FXML
    private void EditarEmpleadoAction(ActionEvent event) {
        Empleado empleadoSeleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();

        if (empleadoSeleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Por favor selecciona un empleado para editar.");
            return;
        }

        try {
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/AgregarTrabajador.fxml"));
            Scene scene = new Scene(loader.load());

            
            AgregarTrabajadorController controller = loader.getController();
            controller.setModoEdicion(empleadoSeleccionado);

            
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Editar Empleado");
            stage.show();

            
            Stage actual = (Stage) btnEditarEmpleado.getScene().getWindow();
            actual.close();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo abrir la ventana de edición: " + e.getMessage());
        }
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
            mostrarAlerta(Alert.AlertType.ERROR, "Error al cargar", "No se pudieron cargar los empleados: " + e.getMessage());
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
