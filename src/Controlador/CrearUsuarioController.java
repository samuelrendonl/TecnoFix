package Controlador;

import Modelo.ConexionBD;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class CrearUsuarioController implements Initializable {

    @FXML
    private Button btnCrearCuenta;

    @FXML
    private Button VolverInicio;

    @FXML
    private TextField txtCrearNombre;

    @FXML
    private TextField txtCrearUsuario;

    @FXML
    private PasswordField txtCrearContraseña;

    @FXML
    private PasswordField txtConfirmarContraseña;
    


    @FXML
    private void ActionCrearCuenta(ActionEvent event) throws SQLException {
        String nombre = txtCrearNombre.getText();
        String usuario = txtCrearUsuario.getText();
        String password_hash = txtCrearContraseña.getText();
        String confirmar = txtConfirmarContraseña.getText();

        if (nombre.isEmpty() || usuario.isEmpty() || password_hash.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Debes llenar todos los campos.");
            txtCrearNombre.requestFocus();
            return;
        }

        if (!password_hash.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña Incorrecta", "Las contraseñas no coinciden.");
            txtCrearNombre.requestFocus();
            return;
        }

         String sqlUsuarios = "INSERT INTO usuarios (nombre, usuario, password_hash, rol) VALUES (?, ?, ?, ?)";
        String sqlAdmin = "INSERT INTO empleados (nombre, usuario, password_hash, rol ) VALUES (?, ?, ?, ?)";    
        
        
              try (Connection conn = ConexionBD.conectar()) {

            // Desactivamos autocommit para manejar ambas inserciones como una sola transacción
            conn.setAutoCommit(false);

            try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuarios);
                 PreparedStatement psAdmin = conn.prepareStatement(sqlAdmin)) {

                // Inserción en usuarios
                psUsuario.setString(1, nombre);
                psUsuario.setString(2, usuario);
                psUsuario.setString(3, password_hash);
                psUsuario.setString(4, "empleado");
                psUsuario.executeUpdate();

                // Inserción en empleados
                psAdmin.setString(1, nombre);
                psAdmin.setString(2, usuario);
                psAdmin.setString(3, password_hash);
                psAdmin.setString(4, "empleado");
             conn.commit();

    
                mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario creado correctamente.");
                Main.changeScene("Login.fxml", "Iniciar Sesión");
            

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el usuario: " + e.getMessage());
        }
    }

    }
    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    private void VolverInicio(ActionEvent event) {
        txtCrearNombre.clear();
        txtCrearUsuario.clear();
        txtCrearContraseña.clear();
        txtConfirmarContraseña.clear();
        Main.changeScene("Login.fxml", "Iniciar Sesión");
    }
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
}




    


