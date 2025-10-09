package Controlador;

import Modelo.ConexionBD;
import Modelo.Empleado;
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
    private Button btnInicio, btnvolver;

    private boolean modoEdicion = false;
    private int idEmpleadoEditar;

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

        // Validaciones
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

        try (Connection conn = ConexionBD.conectar()) {
            if (modoEdicion) {
                // 🔹 MODO EDICIÓN → Actualizar empleado existente
                String sqlUpdate = "UPDATE empleados SET nombre = ?, usuario = ?, password_hash = ? WHERE id_empleado = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                    ps.setString(1, nombre);
                    ps.setString(2, usuario);
                    ps.setString(3, password_hash);
                    ps.setInt(4, idEmpleadoEditar);

                    int filas = ps.executeUpdate();

                    if (filas > 0) {
                        mostrarAlerta(Alert.AlertType.INFORMATION, "Actualizado", "Empleado actualizado correctamente.");
                        limpiarCampos();
                    } else {
                        mostrarAlerta(Alert.AlertType.WARNING, "Error", "No se encontró el empleado a actualizar.");
                    }
                }
            } else {
                
                String sqlUsuarios = "INSERT INTO usuarios (nombre, usuario, password_hash, rol) VALUES (?, ?, ?, ?)";
                String sqlEmpleados = "INSERT INTO empleados (nombre, usuario, password_hash, rol ) VALUES (?, ?, ?, ?)";

                conn.setAutoCommit(false);

                try (PreparedStatement psUsuario = conn.prepareStatement(sqlUsuarios);
                     PreparedStatement psEmpleado = conn.prepareStatement(sqlEmpleados)) {

                    psUsuario.setString(1, nombre);
                    psUsuario.setString(2, usuario);
                    psUsuario.setString(3, password_hash);
                    psUsuario.setString(4, "empleado");
                    psUsuario.executeUpdate();

                    psEmpleado.setString(1, nombre);
                    psEmpleado.setString(2, usuario);
                    psEmpleado.setString(3, password_hash);
                    psEmpleado.setString(4, "empleado");
                    psEmpleado.executeUpdate();

                    conn.commit();

                    mostrarAlerta(Alert.AlertType.INFORMATION, "Éxito",
                            "Empleado creado correctamente y registrado como usuario.");

                    limpiarCampos();

                } catch (SQLException e) {
                    conn.rollback();
                    mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo crear el empleado: " + e.getMessage());
                    e.printStackTrace();
                }
            }

        } catch (SQLException e) {
            mostrarAlerta(Alert.AlertType.ERROR, "Error de Conexión", "No se pudo conectar a la base de datos.");
            e.printStackTrace();
        }
    }

    // 🔸 Método para recibir datos del empleado desde Editar
    public void setModoEdicion(Empleado empleado) {
        this.modoEdicion = true;
        this.idEmpleadoEditar = empleado.getIdEmpleado();

        txtCrearNombreEmpleado.setText(empleado.getNombre());
        txtCrearUsuarioEmpleado.setText(empleado.getUsuario());

        // Si deseas ocultar los campos de contraseña en edición:
        txtCrearConraseñaEmpleado.setPromptText("Nueva contraseña");
        txtConfirmarContraseñaEmpleado.setPromptText("Confirmar contraseña");
    }

    private void limpiarCampos() {
        txtCrearNombreEmpleado.clear();
        txtCrearUsuarioEmpleado.clear();
        txtCrearConraseñaEmpleado.clear();
        txtConfirmarContraseñaEmpleado.clear();
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
    
    @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("GestionDeTrabajadores.fxml", "Gestor De Trabajadores");  
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {


    }
}
