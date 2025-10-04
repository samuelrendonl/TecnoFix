/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import Controlador.Main.TestConexion;
import Modelo.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginController implements Initializable {

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField txtContraseña; 

    @FXML
    private Button btnIniciar;


@FXML
private void ActionIniciar(ActionEvent event) {
    String usuario = txtUsuario.getText();
    String contraseña = txtContraseña.getText();

    if (usuario == null || usuario.trim().isEmpty() ||
        contraseña == null || contraseña.trim().isEmpty()) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Campos Vacíos");
        alert.setHeaderText(null);
        alert.setContentText("Se requiere completar los campos para iniciar sesión.");
        alert.showAndWait();

        txtUsuario.requestFocus();
        return;
    }

    String sql = "SELECT rol FROM usuarios WHERE usuario = ? AND password_hash = ?";

    try (Connection conn = ConexionBD.conectar();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, usuario);
        ps.setString(2, contraseña);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            String rol = rs.getString("rol");
            Main.tipoUsuario = rol.toLowerCase(); // <- aquí guardas el rol

            if ("admin".equalsIgnoreCase(rol)) {
                Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
                System.out.println("Sesión iniciada como Administrador");
            } else if ("empleado".equalsIgnoreCase(rol)) {
                Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
                System.out.println("Sesión iniciada como Empleado");
            }
        } else {
            System.out.println("Credenciales incorrectas");
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}


     @FXML
    private Button btnCrear;
     

     @FXML
    private void ActionCrear(ActionEvent event) {
        System.out.println("Boton de Crear Usuario Presionado");
    Main.changeScene("CrearUsuario.fxml", "Panel Administrador");    
    }

    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Inicialización si la necesitas
    }  
    
    
    

    
}


