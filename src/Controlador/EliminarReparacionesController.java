package Controlador;

import Modelo.ConexionBD;
import Modelo.Reparacion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EliminarReparacionesController implements Initializable {

    @FXML
    private Button btnInicio, btnvolver, btneliminarReparacion;
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
    colIdReparacion.setCellValueFactory(new PropertyValueFactory<>("idReparacion"));
    colFechaRecepcion.setCellValueFactory(new PropertyValueFactory<>("fechaRecepcion"));
    colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
    colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
    colNombreDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
    colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<>("nombreEmpleado"));

    cargarReparaciones();
}


    // ==================== NAVEGACIÓN ====================
    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("GestionDeReparaciones.fxml", "Gestor De Reparaciones");
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }



    // ==================== CARGAR REPARACIONES ====================
private void cargarReparaciones() {
    listaReparaciones = FXCollections.observableArrayList();

String sql = """
    SELECT r.id_reparacion,
           r.fecha_recepcion,
           r.descripcion_problema AS descripcion,
           r.estado,
           d.NombreCliente AS nombreCliente,
           d.NombreDispositivo AS nombreDispositivo,
           u.nombre AS nombreEmpleado
    FROM reparaciones r
    INNER JOIN dispositivos d ON r.id_dispositivo = d.id_dispositivo
    INNER JOIN usuarios u ON r.id_empleado = u.id_usuario
""";


    try (Connection conn = ConexionBD.conectar()) {
        if (conn == null) {
            System.err.println( "No se pudo conectar a la base de datos.");
            return;
        } else {
            System.out.println("Conexión a BD exitosa.");
        }

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        boolean hayDatos = false;

        while (rs.next()) {
            hayDatos = true;
            Reparacion reparacion = new Reparacion(
                rs.getInt("id_reparacion"),
                rs.getString("fecha_recepcion"),
                rs.getString("descripcion"),
                rs.getString("estado"),
                rs.getString("nombreCliente"),
                rs.getString("nombreDispositivo"),
                rs.getString("nombreEmpleado")
            );

            listaReparaciones.add(reparacion);
            System.out.println("Reparación cargada: " + reparacion);
        }

        if (!hayDatos) {
            System.out.println("No se encontraron reparaciones en la base de datos.");
        }

        tablareparaciones.setItems(listaReparaciones);

    } catch (SQLException e) {
        e.printStackTrace();
        System.err.println("Error al cargar reparaciones: " + e.getMessage());
    }
}





    @FXML
    private void EliminarEmpleadoAction(ActionEvent event) {
        Reparacion seleccionada = tablareparaciones.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Sin selección", "Por favor selecciona una reparación para eliminar.");
            return;
        }

        String mensaje = String.format("""
            ¿Deseas eliminar la reparación seleccionada?
            
            ID: %d
            Cliente: %s
            Dispositivo: %s
            Empleado: %s
            Estado: %s
            Descripción: %s
            """,
            seleccionada.getIdReparacion(),
            seleccionada.getNombreCliente(),
            seleccionada.getNombreDispositivo(),
            seleccionada.getNombreEmpleado(),
            seleccionada.getEstado(),
            seleccionada.getDescripcion()
        );

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("Eliminar reparación");
        confirmacion.setContentText(mensaje);

        Optional<ButtonType> resultado = confirmacion.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            eliminarReparacion(seleccionada);
        }
    }

    private void eliminarReparacion(Reparacion r) {
        String sql = "DELETE FROM reparaciones WHERE idReparacion = ?";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, r.getIdReparacion());
            int filasAfectadas = stmt.executeUpdate();

            if (filasAfectadas > 0) {
                listaReparaciones.remove(r);
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "La reparación fue eliminada correctamente.");
            } else {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo eliminar la reparación.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error de base de datos", e.getMessage());
        }
    }

    // ==================== ALERTAS ====================
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
