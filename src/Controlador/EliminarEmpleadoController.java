package Controlador;

import Modelo.ConexionBD;
import Modelo.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;
import javafx.event.ActionEvent;

public class EliminarEmpleadoController {

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
    private Button btnEliminar,btnInicio, btnvolver;

    private ObservableList<Empleado> listaEmpleados = FXCollections.observableArrayList();

     @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    
    
    @FXML
    public void initialize() {
        // Configurar las columnas
        colId.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getIdEmpleado()).asObject());
        colNombre.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        colUsuario.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getUsuario()));
        colRol.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getRol()));

        cargarEmpleados();
    }

    private void cargarEmpleados() {
        listaEmpleados.clear();
        Connection conn = ConexionBD.conectar();
        if (conn == null) return;

        String sql = "SELECT id_empleado, nombre, usuario, rol FROM empleados";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                listaEmpleados.add(new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("usuario"),
                        rs.getString("rol")
                ));
            }

            tablaEmpleados.setItems(listaEmpleados);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los empleados.");
        }
    }

    @FXML
    private void EliminarEmpleadoAction(ActionEvent event) {
        Empleado seleccionado = tablaEmpleados.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Advertencia", "Selecciona un empleado para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar este empleado?");
        confirmacion.setContentText(
                "Nombre: " + seleccionado.getNombre() +
                        "\nUsuario: " + seleccionado.getUsuario() +
                        "\nRol: " + seleccionado.getRol()
        );

        confirmacion.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                eliminarDeBaseDatos(seleccionado);
            }
        });
    }

    private void eliminarDeBaseDatos(Empleado empleado) {
        Connection conn = ConexionBD.conectar();
        if (conn == null) return;

        String sql = "DELETE FROM empleados WHERE id_empleado = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, empleado.getIdEmpleado());
            int filas = pstmt.executeUpdate();

            if (filas > 0) {
                listaEmpleados.remove(empleado);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Empleado eliminado",
                        "Se eliminó correctamente:\n\n" +
                                "Nombre: " + empleado.getNombre() + "\n" +
                                "Usuario: " + empleado.getUsuario() + "\n" +
                                "Rol: " + empleado.getRol());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Error al eliminar el empleado de la base de datos.");
        }
    }

        @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("GestionDeTrabajadores.fxml", "Gestor De Trabajadores");  
    }
    
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String contenido) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(contenido);
        alerta.showAndWait();
    }
}
