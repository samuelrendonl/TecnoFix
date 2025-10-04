/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Modelo.ConexionBD;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;  
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AgregarDispositivoController implements Initializable {

    @FXML
    private TextField txtCliente;
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtMarca;
    @FXML
    private TextField txtDaño;
    @FXML
    private Button btnAgregar;

    @FXML
    private ComboBox<String> comboBoxEmpleado;
    
    @FXML
private Button btnSubirFoto;

@FXML
private ImageView imageViewFoto;

private File imagenSeleccionada;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpleados();
    }
    
    @FXML
    private Button btnInicio;
     @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    private void cargarEmpleados() {
        List<String> empleados = new ArrayList<>();
        String sql = "SELECT nombre FROM usuarios WHERE rol = 'empleado'";

        try (Connection conn = ConexionBD.conectar(); 
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                empleados.add(rs.getString("nombre"));
            }
            
            
            ObservableList<String> empleadosList = FXCollections.observableArrayList(empleados);

            
            comboBoxEmpleado.setItems(empleadosList);
            comboBoxEmpleado.setPromptText("Seleccionar empleado");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @FXML
public void subirFoto() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar foto del dispositivo");
    fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
    );

    File file = fileChooser.showOpenDialog(btnSubirFoto.getScene().getWindow());
    if (file != null) {
        imagenSeleccionada = file;
        Image image = new Image(file.toURI().toString());
        imageViewFoto.setImage(image);
    }
}


@FXML
public void asignarReparacion() {
    String cliente = txtCliente.getText();
    String nombreDispositivo = txtNombre.getText();
    String marca = txtMarca.getText();
    String dano = txtDaño.getText();
    String empleadoSeleccionado = comboBoxEmpleado.getValue();

    if (cliente.isEmpty() || nombreDispositivo.isEmpty() || marca.isEmpty() || 
        dano.isEmpty() || empleadoSeleccionado == null) {
        
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.WARNING);
        alerta.setTitle("Campos incompletos");
        alerta.setHeaderText(null);
        alerta.setContentText("Por favor, complete todos los campos antes de asignar la reparación.");
        alerta.showAndWait();
        return;
    }

String sql = "INSERT INTO reparaciones (NombreCliente, NombreDispositivo, MarcaDispositivo, DañoDispositivo, EmpleadoAsignado, FotoDispositivo) "
           + "VALUES (?, ?, ?, ?, ?, ?)";

try (Connection conn = ConexionBD.conectar();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, cliente);
    ps.setString(2, nombreDispositivo);
    ps.setString(3, marca);
    ps.setString(4, dano);
    ps.setString(5, empleadoSeleccionado);

    if (imagenSeleccionada != null) {
        FileInputStream fis = new FileInputStream(imagenSeleccionada);
        ps.setBinaryStream(6, fis, (int) imagenSeleccionada.length());
    } else {
        ps.setNull(6, java.sql.Types.BLOB);
    }

    ps.executeUpdate();


        
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alerta.setTitle("Reparación asignada");
        alerta.setHeaderText(null);
        alerta.setContentText("La reparación ha sido asignada correctamente.");
        alerta.showAndWait();

        
        txtCliente.clear();
        txtNombre.clear();
        txtMarca.clear();
        txtDaño.clear();
        comboBoxEmpleado.setValue(null);
        imageViewFoto.setImage(null);
        imagenSeleccionada = null;

    } catch (Exception e) {
        e.printStackTrace();
        javafx.scene.control.Alert alerta = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("No se pudo asignar la reparación");
        alerta.setContentText("Error: " + e.getMessage());
        alerta.showAndWait();
    }
}
}

