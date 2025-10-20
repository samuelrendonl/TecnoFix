package Controlador;

import Modelo.ConexionBD;
import Modelo.Dispositivo;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class EditarDispositivoController implements Initializable {

    @FXML
    private Button btnInicio, btnEditarDispositivo, btnvolver;
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

    private ObservableList<Dispositivo> listaDispositivos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDispositivos();
    }

private void cargarDispositivos() {
    String sql = """
        SELECT d.id_dispositivo,
               d.NombreCliente,
               d.NombreDispositivo,
               d.MarcaDispositivo,
               d.DañoDispositivo,
               e.nombre AS EmpleadoAsignado
        FROM dispositivos d
        LEFT JOIN reparaciones r ON d.id_dispositivo = r.id_dispositivo
        LEFT JOIN empleados e ON r.id_empleado = e.id_empleado;
    """;

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        listaDispositivos.clear();

        while (rs.next()) {
            listaDispositivos.add(new Dispositivo(
                    rs.getInt("id_dispositivo"),
                    rs.getString("NombreCliente"),
                    rs.getString("NombreDispositivo"),
                    rs.getString("MarcaDispositivo"),
                    rs.getString("DañoDispositivo"),
                    rs.getString("EmpleadoAsignado")
            ));
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
        new Alert(Alert.AlertType.ERROR, "Error al cargar dispositivos: " + e.getMessage()).showAndWait();
    }
}


    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

@FXML
private void EditarDispositivosAction(ActionEvent event) {
    Dispositivo seleccionado = tableDispositivos.getSelectionModel().getSelectedItem();

    if (seleccionado == null) {
        new Alert(Alert.AlertType.WARNING, "Selecciona un dispositivo para editar.").showAndWait();
        return;
    }

    try {
        // Cambia de escena y obtiene el controlador del nuevo FXML
        AgregarDispositivoController controller =
            (AgregarDispositivoController) Main.changeScene("AgregarDispositivo.fxml", "Editar Dispositivo");

        // Pasa el dispositivo seleccionado al nuevo controlador
        if (controller != null) {
            controller.setDispositivoParaEditar(seleccionado);
        }

    } catch (Exception e) {
        e.printStackTrace();
        new Alert(Alert.AlertType.ERROR, "Error al abrir el formulario de edición: " + e.getMessage()).showAndWait();
    }
}

    
    
        @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("Dispositivos.fxml", "Dispositivos");  
    }

    
}
