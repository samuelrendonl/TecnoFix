package Controlador;

import Modelo.ConexionBD;
import Modelo.Pieza;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 * Controlador para eliminar piezas desde la base de datos.
 * 
 * @author Samuel Rendón
 */
public class EliminarPiezaController implements Initializable {

    @FXML
    private Button btnInicio, btnvolver, btnEliminarPieza;

    @FXML
    private TableView<Pieza> tablaPiezas;
    @FXML
    private TableColumn<Pieza, Integer> colId;
    @FXML
    private TableColumn<Pieza, String> colNombre;
    @FXML
    private TableColumn<Pieza, String> colTipo;
    @FXML
    private TableColumn<Pieza, Double> colPrecio;
    @FXML
    private TableColumn<Pieza, Integer> colStock;

    private ObservableList<Pieza> listaPiezas = FXCollections.observableArrayList();

    // 🔹 Botón para volver
    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("GestionDePiezas.fxml", "Gestor De Piezas");
    }

    // 🔹 Botón para ir al inicio (panel administrador)
    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    // 🔹 Método principal para eliminar pieza
    @FXML
    private void EliminarPiezaAction(ActionEvent event) {
        Pieza piezaSeleccionada = tablaPiezas.getSelectionModel().getSelectedItem();

        if (piezaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Debes seleccionar una pieza para eliminar.", Alert.AlertType.WARNING);
            return;
        }

        Alert confirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        confirmacion.setTitle("Confirmar eliminación");
        confirmacion.setHeaderText(null);
        confirmacion.setContentText("¿Seguro que deseas eliminar la pieza '" + piezaSeleccionada.getNombre() + "'?");

        if (confirmacion.showAndWait().get() == ButtonType.OK) {
            try (Connection conn = ConexionBD.conectar()) {
                String sql = "DELETE FROM pieza WHERE id_pieza = ?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, piezaSeleccionada.getId());

                int filas = ps.executeUpdate();
                if (filas > 0) {
                    listaPiezas.remove(piezaSeleccionada);
                    mostrarAlerta("Éxito", "La pieza fue eliminada correctamente.", Alert.AlertType.INFORMATION);
                } else {
                    mostrarAlerta("Error", "No se pudo eliminar la pieza.", Alert.AlertType.ERROR);
                }

            } catch (SQLException e) {
                mostrarAlerta("Error", "Ocurrió un error al eliminar la pieza: " + e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    private void cargarPiezas() {
        listaPiezas.clear();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT * FROM pieza";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaPiezas.add(new Pieza(
                        rs.getInt("id_pieza"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getInt("precio"),
                        rs.getInt("Cantidad"),
                        rs.getBytes("imagen")
                ));
            }

            tablaPiezas.setItems(listaPiezas);

        } catch (SQLException e) {
            mostrarAlerta("Error", "No se pudieron cargar las piezas: " + e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarPiezas();
    }
}

