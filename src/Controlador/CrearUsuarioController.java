

/**
 * FXML Controller class
 *
 * @author Mi PC
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

public class CrearUsuarioController implements Initializable {

    @FXML
    private Button btnCrearCuenta;
        private Button VolverInicio;
    @FXML
    private TextField txtCrearNombre;
    @FXML
    private TextField txtCrearUsuario;
    @FXML
    private PasswordField txtCrearConraseña;
    @FXML
    private PasswordField txtConfirmarContraseña;
    
    @FXML
    private void ActionCrearCuenta(ActionEvent event) {
        String nombre = txtCrearNombre.getText();
        String usuario = txtCrearUsuario.getText();
        String password_hash = txtCrearConraseña.getText();
        String confirmar = txtConfirmarContraseña.getText();

        
        if (nombre.isEmpty() || usuario.isEmpty() || password_hash.isEmpty() || confirmar.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Debes llenar todos los campos.");
            txtCrearNombre.requestFocus();
            return;
        }

        
        if (!password_hash.equals(confirmar)) {
            mostrarAlerta(Alert.AlertType.WARNING, "Contraseña Incorrecta", "Las contraseñas no coinciden.");
            txtCrearConraseña.requestFocus();
            return;
        }

        
String sql = "INSERT INTO usuarios (nombre, usuario, password_hash, rol) VALUES (?, ?, ?, ?)";

try (Connection conn = ConexionBD.conectar();
     PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, nombre);
    ps.setString(2, usuario);
    ps.setString(3, password_hash);
    ps.setString(4, "admin");

    int filas = ps.executeUpdate();

    if (filas > 0) {
        mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito", "Usuario administrador creado correctamente.");
        Main.changeScene("Login.fxml", "Iniciar Sesión");
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

    
    
    private void VolverInicio(ActionEvent event){
    Main.changeScene("Login.fxml", "Iniciar Sesión");
        
    }
    
        @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
}


