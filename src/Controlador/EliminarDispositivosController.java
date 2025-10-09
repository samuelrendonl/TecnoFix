package Controlador;

import Modelo.ConexionBD;
import Modelo.Dispositivo;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

public class EliminarDispositivosController implements Initializable {

    @FXML
    private Button btnInicio, btnElimarDispositivo, btnvolver;
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

    private final ObservableList<Dispositivo> listaDispositivos = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        configurarColumnas();
        cargarDispositivos();
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("idDispositivo"));
        colNombreCliente.setCellValueFactory(new PropertyValueFactory<>("nombreCliente"));
        colNombreDispositivo.setCellValueFactory(new PropertyValueFactory<>("nombreDispositivo"));
        colMarcaDispositivo.setCellValueFactory(new PropertyValueFactory<>("marcaDispositivo"));
        colDañoDispositivo.setCellValueFactory(new PropertyValueFactory<>("dañoDispositivo"));
        colEmpleadoAsignado.setCellValueFactory(new PropertyValueFactory<>("empleadoAsignado"));
    }

private void cargarDispositivos() {
    listaDispositivos.clear();
    String sql = "SELECT id_dispositivo, NombreCliente, NombreDispositivo, MarcaDispositivo, DañoDispositivo, EmpleadoAsignado "
               + "FROM dispositivos";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Dispositivo d = new Dispositivo(
                    rs.getInt("id_dispositivo"),
                    rs.getString("NombreCliente"),
                    rs.getString("NombreDispositivo"),
                    rs.getString("MarcaDispositivo"),
                    rs.getString("DañoDispositivo"),
                    rs.getString("EmpleadoAsignado")
            );
            listaDispositivos.add(d);
        }

        tableDispositivos.setItems(listaDispositivos);

    } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudieron cargar los dispositivos.");
    }
}

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    @FXML
    private void EliminarDispositivosAction(ActionEvent event) {
        Dispositivo seleccionado = tableDispositivos.getSelectionModel().getSelectedItem();
        if (seleccionado == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Ningún dispositivo seleccionado", "Selecciona un dispositivo para eliminar.");
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText("¿Seguro que deseas eliminar este dispositivo?");
        confirmacion.setContentText(
                "Cliente: " + seleccionado.getNombreCliente() + "\n"
                + "Dispositivo: " + seleccionado.getNombreDispositivo() + "\n"
                + "Marca: " + seleccionado.getMarcaDispositivo() + "\n"
                + "Técnico: " + seleccionado.getEmpleadoAsignado()
        );

        Optional<ButtonType> resultado = confirmacion.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            eliminarDispositivoBD(seleccionado.getIdDispositivo());
            listaDispositivos.remove(seleccionado);
            mostrarAlerta(Alert.AlertType.INFORMATION, "Eliminado", "El dispositivo fue eliminado correctamente.");
        }
    }

    private void eliminarDispositivoBD(int idDispositivo) {
        String sql = "DELETE FROM dispositivos WHERE id_dispositivo = ?";
        try (Connection conn = ConexionBD.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idDispositivo);
            stmt.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un error al eliminar el dispositivo de la base de datos.");
        }
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
    
    
            @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("Dispositivos.fxml", "Dispositivos");  
    }

}
