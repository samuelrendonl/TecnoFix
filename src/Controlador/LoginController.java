package Controlador;

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
    private Button btnCrear;

    @FXML
    private void ActionIniciar(ActionEvent event) {
        String usuario = txtUsuario.getText().trim();
        String password = txtContraseña.getText().trim();

        if (usuario.isEmpty() || password.isEmpty()) {
            mostrarAlerta(Alert.AlertType.WARNING, "Campos Vacíos", "Debe completar todos los campos.");
            return;
        }

        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) {
                mostrarAlerta(Alert.AlertType.ERROR, "Error", "No hay conexión con la base de datos.");
                return;
            }

            String sql = "SELECT * FROM usuarios WHERE usuario = ? AND password_hash = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // 🔹 Guardamos los datos del usuario
                Main.nombreUsuario = rs.getString("usuario");
                Main.tipoUsuario = rs.getString("rol");
                Main.nombreEmpleado = rs.getString("nombre");


                // 🔹 Redirección según el rol
                if ("admin".equals(Main.tipoUsuario)) {
                    Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
                } else if ("empleado".equals(Main.tipoUsuario)) {
                    Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
                }
                return;
            }

            mostrarAlerta(Alert.AlertType.ERROR, "Acceso denegado", "Usuario o contraseña incorrectos.");

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "Ocurrió un problema: " + e.getMessage());
        }
    }

    @FXML
    private void ActionCrear(ActionEvent event) {
        Main.changeScene("CrearUsuario.fxml", "Crear Usuario");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {}
}
