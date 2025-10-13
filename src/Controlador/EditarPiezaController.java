/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;


import Modelo.ConexionBD;
import Modelo.Pieza;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

/**
 * FXML Controller class
 *
 * @author Mi PC
 */
public class EditarPiezaController implements Initializable {

    @FXML
    private Button btnInicio, btnvolver, btnEditarPieza;
    
            @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("GestionDePiezas.fxml", "Gestor De Piezas");   
    }
        @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }


    @FXML private TableView<Pieza> tablaPiezas;
    @FXML private TableColumn<Pieza, Integer> colId;
    @FXML private TableColumn<Pieza, String> colNombre;
    @FXML private TableColumn<Pieza, String> colTipo;
    @FXML private TableColumn<Pieza, Integer> colPrecio;
    @FXML private TableColumn<Pieza, Integer> colStock;
    @FXML private Button btnEditar, btnVolver;

    private ObservableList<Pieza> listaPiezas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("cantidad"));

        cargarPiezas();
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
                        rs.getInt("cantidad"),
                        rs.getBytes("imagen")
                ));
            }
            tablaPiezas.setItems(listaPiezas);
        } catch (SQLException e) {
            mostrarAlerta("Error al cargar piezas", e.getMessage(), AlertType.ERROR);
        }
    }

    @FXML
    private void EditarPiezaAction(ActionEvent event) {
        Pieza piezaSeleccionada = tablaPiezas.getSelectionModel().getSelectedItem();

        if (piezaSeleccionada == null) {
            mostrarAlerta("Error", "Debe seleccionar una pieza para editar.", AlertType.WARNING);
            return;
        }

        // Guardamos la pieza seleccionada en una variable estática temporal
        AgregarPiezaController.piezaAEditar = piezaSeleccionada;

        // Cambiamos de escena al formulario de agregar pieza
        Main.changeScene("AgregarPieza.fxml", "Editar Pieza");
    }


    private void mostrarAlerta(String titulo, String mensaje, AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}

