package Controlador;

import Modelo.ConexionBD;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class EstReparacionesController implements Initializable {

    @FXML
    private Button btnInicio, btnvolver;
    @FXML
    private Label lblRepComp, lblRepPend, lblRepNoComp;

    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("Estadisticas.fxml", "Gestor De Estadisticas");
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEstadisticasReparaciones();
    }

    private void cargarEstadisticasReparaciones() {
        String sql = "SELECT estado, COUNT(*) AS cantidad FROM reparaciones GROUP BY estado";

        try (Connection conn = ConexionBD.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            int completadas = 0;
            int pendientes = 0;
            int noCompletadas = 0;

            while (rs.next()) {
                String estado = rs.getString("estado");
                int cantidad = rs.getInt("cantidad");

                switch (estado.toLowerCase()) {
                    case "completada":
                        completadas = cantidad;
                        break;
                    case "pendiente":
                        pendientes = cantidad;
                        break;
                    case "no completada":
                        noCompletadas = cantidad;
                        break;
                }
            }

            lblRepComp.setText(String.valueOf(completadas));
            lblRepPend.setText(String.valueOf(pendientes));
            lblRepNoComp.setText(String.valueOf(noCompletadas));

        } catch (SQLException e) {
            mostrarAlerta("Error al cargar estadísticas", e.getMessage());
            e.printStackTrace();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
