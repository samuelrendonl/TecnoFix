/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */



package Controlador;

import Modelo.ConexionBD;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AgregarTrabajadorController implements Initializable {

    @FXML
    private Button btnCrarCuentaEmpleado;

    @FXML
    private TextField txtCrearNombreEmpleado;
    @FXML
    private TextField txtCrearUsuarioEmpleado;
    @FXML
    private PasswordField txtCrearConraseñaEmpleado;
    @FXML
    private PasswordField txtConfirmarContraseñaEmpleado;
    
        @FXML
    private Button btnInicio;
     @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    @FXML
    private void ActionCrearCuentaEmpleado(ActionEvent event) {
        String nombre = txtCrearNombreEmpleado.getText();
        String usuario = txtCrearUsuarioEmpleado.getText();
        String password_hash = txtCrearConraseñaEmpleado.getText();
        String confirmar = txtConfirmarContraseñaEmpleado.getText();

        
        if (nombre.isEmpty() || usuario.isEmpty() || password_hash.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Debes llenar todos los campos.");
            txtCrearNombreEmpleado.requestFocus();
            return;
        }

        
        if (!password_hash.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña Incorrecta", "Las contraseñas no coinciden.");
            txtCrearConraseñaEmpleado.requestFocus();
            return;
        }

        
String sql = "INSERT INTO usuarios (nombre, usuario, password_hash, rol) VALUES (?, ?, ?, ?)";

try (Connection conn = ConexionBD.conectar();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, nombre);
    ps.setString(2, usuario);
    ps.setString(3, password_hash);
    ps.setString(4, "empleado");

    int filas = ps.executeUpdate();

    if (filas > 0) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario empleado creado correctamente.");
       
    }

} catch (Exception e) {
    e.printStackTrace();
    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el usuario: " + e.getMessage());
}
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    

    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
}

