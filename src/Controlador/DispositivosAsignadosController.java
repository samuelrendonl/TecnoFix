package Controlador;

import Modelo.ConexionBD;
import Modelo.Dispositivo;
import java.awt.event.MouseEvent;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DispositivosAsignadosController implements Initializable {

    @FXML private Button btnInicio, btnVolver, btnRealizarReparacion;
    @FXML private TableView<Dispositivo> tablareparaciones;
    @FXML private TableColumn<Dispositivo, Integer> colIdReparacion;
    @FXML private TableColumn<Dispositivo, String> colNombreCliente;
    @FXML private TableColumn<Dispositivo, String> colNombreDispositivo;
    @FXML private TableColumn<Dispositivo, String> colNombreEmpleado;
    @FXML private TableColumn<Dispositivo, String> colFechaRecepcion;
    @FXML private TableColumn<Dispositivo, String> colEstado;
    @FXML private TableColumn<Dispositivo, String> colDescripcion;

    private final ObservableList<Dispositivo> listaDispositivos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDispositivos();
    }

    private void configurarColumnas() {
        colIdReparacion.setCellValueFactory(new PropertyValueFactory<>("idReparacion"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        colNombreEmpleado.setCellValueFactory(new PropertyValueFactory<>("empleadoAsignado"));
        colFechaRecepcion.setCellValueFactory(new PropertyValueFactory<>("fechaRecepcion"));
        colEstado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
    }
    


    private void cargarDispositivos() {
        listaDispositivos.clear();

        try (Connection conn = ConexionBD.conectar()) {
            String sql = """
                SELECT 
                    r.id_reparacion,
                    d.nombrecliente,
                    d.nombredispositivo,
                    e.nombre AS empleadoAsignado,
                    r.fecha_recepcion,
                    r.estado,
                    r.descripcion_problema AS descripcion,
                    d.fotodispositivo
                FROM reparaciones r
                JOIN dispositivos d ON r.id_dispositivo = d.id_dispositivo
                JOIN empleados e ON r.id_empleado = e.id_empleado
                WHERE e.nombre = ?;
            """;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Main.nombreEmpleado);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Dispositivo dispositivo = new Dispositivo(
                    rs.getInt("id_reparacion"),
                    rs.getString("nombrecliente"),
                    rs.getString("nombredispositivo"),
                    rs.getString("empleadoAsignado"),
                    rs.getString("fecha_recepcion"),
                    rs.getString("estado"),
                    rs.getString("descripcion")
                );

          
                byte[] img = rs.getBytes("fotodispositivo");
                dispositivo.setImagenDispositivo(img);

                listaDispositivos.add(dispositivo);
            }

            tablareparaciones.setItems(listaDispositivos);

        } catch (SQLException e) {
            mostrarAlerta("Error al cargar los dispositivos", e.getMessage());
        }
    }

    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
    }

    @FXML
    private void RealizarReparacionAction(ActionEvent event) {
        Dispositivo seleccion = tablareparaciones.getSelectionModel().getSelectedItem();

        if (seleccion == null) {
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Sin selección");
            alerta.setHeaderText(null);
            alerta.setContentText("Debe seleccionar una reparación antes de continuar.");
            alerta.showAndWait();
            return;
        }

        
        Main.reparacionSeleccionada = seleccion;
        Main.changeScene("RealizaReparacion.fxml", "Realizar Reparación");
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}




 

