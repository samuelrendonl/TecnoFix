package Controlador;

import Modelo.ConexionBD;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class RealizaReparacionController implements Initializable {

    @FXML private Label lblIdReparacion;
    @FXML private Label lblNombreCliente;
    @FXML private Label lblNombreDispositivo;
    @FXML private Label lblDescripcion;
    @FXML private ImageView imagenDispositivoaReparar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
    int idReparacion = (Main.reparacionSeleccionada != null) ? Main.reparacionSeleccionada.getIdReparacion() : -1;

        if (idReparacion > 0) {
            lblIdReparacion.setText(String.valueOf(idReparacion));
            cargarDatosReparacion(idReparacion);
        } else {
            System.out.println("No se recibió un ID de reparación válido.");
        }
    }

private void cargarDatosReparacion(int idReparacion) {
    try {
        Connection conexion = ConexionBD.conectar();

        // 1️⃣ Obtener el id_dispositivo asociado a esta reparación
        String sqlDispositivoId = "SELECT id_dispositivo FROM reparaciones WHERE id_reparacion = ?";
        PreparedStatement psId = conexion.prepareStatement(sqlDispositivoId);
        psId.setInt(1, idReparacion);
        ResultSet rsId = psId.executeQuery();

        if (!rsId.next()) {
            System.out.println("No se encontró dispositivo para la reparación " + idReparacion);
            return;
        }

        int idDispositivo = rsId.getInt("id_dispositivo");
        rsId.close();
        psId.close();

        // 2️⃣ Obtener los datos reales del dispositivo
        String sql = "SELECT NombreCliente, NombreDispositivo, DañoDispositivo, FotoDispositivo FROM dispositivos WHERE id_dispositivo = ?";
        PreparedStatement ps = conexion.prepareStatement(sql);
        ps.setInt(1, idDispositivo);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            lblNombreCliente.setText(rs.getString("NombreCliente"));
            lblNombreDispositivo.setText(rs.getString("NombreDispositivo"));
            lblDescripcion.setText(rs.getString("DañoDispositivo"));

            byte[] imagenBytes = rs.getBytes("FotoDispositivo");
            if (imagenBytes != null && imagenBytes.length > 0) {
                Image imagen = new Image(new ByteArrayInputStream(imagenBytes));
                imagenDispositivoaReparar.setImage(imagen);
            } else {
                imagenDispositivoaReparar.setImage(null);
            }
        } else {
            System.out.println("No se encontró dispositivo con id " + idDispositivo);
        }

        rs.close();
        ps.close();
        conexion.close();

    } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Error al cargar los datos de la reparación: " + e.getMessage());
    }
}




    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("DispositivosAsignados.fxml", "Dispositivos Asignados");
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
    }

    @FXML
    private void RealizarReparacionAction(ActionEvent event) {
        Main.changeScene("CompletarReparacion.fxml", "Completar Reparación");
    }
}
