package Controlador;

import Modelo.ConexionBD;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class EstEmpleadosController implements Initializable {

    @FXML
    private ComboBox<String> comboEmpleados;
    @FXML
    private Label lblId;
    @FXML
    private Label lblNombre;
    @FXML
    private Label lblUsuario;
    @FXML
    private Label lblAsignaciones;
    @FXML
    private Label lblReparaciones;

    private Connection conexion;
            @FXML
    private Button btnInicio, btnvolver;
    
            @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("Estadisticas.fxml", "Gestor De Estadisticas");      
    }
        @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conexion = ConexionBD.conectar();
        cargarEmpleados();

        // Evento: cuando cambia el empleado seleccionado
        comboEmpleados.setOnAction(e -> {
            String nombreEmpleado = comboEmpleados.getSelectionModel().getSelectedItem();
            if (nombreEmpleado != null) {
                mostrarEstadisticas(nombreEmpleado);
            }
        });
    }

    private void cargarEmpleados() {
        ObservableList<String> empleados = FXCollections.observableArrayList();
        String sql = "SELECT nombre FROM empleados WHERE rol = 'empleado'";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                empleados.add(rs.getString("nombre"));
            }
            comboEmpleados.setItems(empleados);
        } catch (SQLException e) {
            System.out.println("Error al cargar empleados: " + e.getMessage());
        }
    }

    private void mostrarEstadisticas(String nombreEmpleado) {
        String sql = """
                SELECT e.id_empleado, e.nombre, e.usuario, e.Asignaciones,
                       COUNT(r.id_reparacion) AS ReparacionesCompletadas
                FROM empleados e
                LEFT JOIN reparaciones r ON e.id_empleado = r.id_empleado AND r.estado = 'Completada'
                WHERE e.nombre = ?
                GROUP BY e.id_empleado, e.nombre, e.usuario, e.Asignaciones
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, nombreEmpleado);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblId.setText(String.valueOf(rs.getInt("id_empleado")));
                lblNombre.setText(rs.getString("nombre"));
                lblUsuario.setText(rs.getString("usuario"));
                lblAsignaciones.setText(String.valueOf(rs.getInt("Asignaciones")));
                lblReparaciones.setText(String.valueOf(rs.getInt("ReparacionesCompletadas")));
            }
        } catch (SQLException e) {
            System.out.println("Error al mostrar estadísticas: " + e.getMessage());
        }
    }
}
