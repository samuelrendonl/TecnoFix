package Controlador;

import Modelo.ConexionBD;
import Modelo.Dispositivo;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.control.*;
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
    private ComboBox<String> comboBoxEmpleado;
    @FXML
    private Button btnAgregar;
    @FXML
    private Button btnSubirFoto;
    @FXML
    private ImageView imageViewFoto;
    @FXML
    private Button btnInicio;

    private File imagenSeleccionada;
    private Integer idDispositivoEdicion = null;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarEmpleados();
    }

    public void setDispositivoParaEditar(Dispositivo disp) {
        if (disp != null) {
            idDispositivoEdicion = disp.getIdDispositivo();
            txtCliente.setText(disp.getNombreCliente());
            txtNombre.setText(disp.getNombreDispositivo());
            txtMarca.setText(disp.getMarcaDispositivo());
            txtDaño.setText(disp.getDañoDispositivo());
            comboBoxEmpleado.setValue(disp.getEmpleadoAsignado());

            try (Connection conn = ConexionBD.conectar();
                 PreparedStatement ps = conn.prepareStatement("SELECT FotoDispositivo FROM dispositivos WHERE id_dispositivo = ?")) {
                ps.setInt(1, idDispositivoEdicion);
                ResultSet rs = ps.executeQuery();
                if (rs.next() && rs.getBinaryStream("FotoDispositivo") != null) {
                    Image img = new Image(rs.getBinaryStream("FotoDispositivo"));
                    imageViewFoto.setImage(img);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    private void cargarEmpleados() {
        List<String> empleados = new ArrayList<>();
        String sql = "SELECT nombre FROM empleados WHERE rol = 'empleado'";

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
        String daño = txtDaño.getText();
        String empleadoSeleccionado = comboBoxEmpleado.getValue();

        if (cliente.isEmpty() || nombreDispositivo.isEmpty() || marca.isEmpty() ||
                daño.isEmpty() || empleadoSeleccionado == null) {

            mostrarAlerta(Alert.AlertType.WARNING, "Campos incompletos", "Por favor, complete todos los campos antes de guardar.");
            return;
        }

        try (Connection conn = ConexionBD.conectar()) {

            // 🔹 Obtener ID del empleado
            String sqlEmpleado = "SELECT id_empleado FROM empleados WHERE nombre = ?";
            PreparedStatement psEmpleado = conn.prepareStatement(sqlEmpleado);
            psEmpleado.setString(1, empleadoSeleccionado);
            ResultSet rsEmpleado = psEmpleado.executeQuery();
            int idEmpleado = 0;
            if (rsEmpleado.next()) {
                idEmpleado = rsEmpleado.getInt("id_empleado");
            }

            if (idDispositivoEdicion == null) {
                // INSERTAR nuevo dispositivo
                String sqlDispositivo = "INSERT INTO dispositivos (NombreCliente, NombreDispositivo, MarcaDispositivo, DañoDispositivo, EmpleadoAsignado, FotoDispositivo) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psDispositivo = conn.prepareStatement(sqlDispositivo, Statement.RETURN_GENERATED_KEYS);

                psDispositivo.setString(1, cliente);
                psDispositivo.setString(2, nombreDispositivo);
                psDispositivo.setString(3, marca);
                psDispositivo.setString(4, daño);
                psDispositivo.setString(5, empleadoSeleccionado);

                if (imagenSeleccionada != null) {
                    FileInputStream fis = new FileInputStream(imagenSeleccionada);
                    psDispositivo.setBinaryStream(6, fis, (int) imagenSeleccionada.length());
                } else {
                    psDispositivo.setNull(6, java.sql.Types.BLOB);
                }

                psDispositivo.executeUpdate();

                ResultSet rsDispositivo = psDispositivo.getGeneratedKeys();
                int idDispositivo = 0;
                if (rsDispositivo.next()) {
                    idDispositivo = rsDispositivo.getInt(1);
                }

                // INSERTAR reparación
                String sqlReparacion = "INSERT INTO reparaciones (fecha_recepcion, descripcion_problema, estado, costo, id_dispositivo, id_empleado) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement psReparacion = conn.prepareStatement(sqlReparacion);
                psReparacion.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
                psReparacion.setString(2, daño);
                psReparacion.setString(3, "Pendiente");
                psReparacion.setDouble(4, 0.0);
                psReparacion.setInt(5, idDispositivo);
                psReparacion.setInt(6, idEmpleado);
                psReparacion.executeUpdate();

                // 🔹 SUMAR 1 al campo Asignaciones del empleado
                String sqlSumar = "UPDATE empleados SET Asignaciones = COALESCE(Asignaciones, 0) + 1 WHERE id_empleado = ?";
                PreparedStatement psSumar = conn.prepareStatement(sqlSumar);
                psSumar.setInt(1, idEmpleado);
                psSumar.executeUpdate();

                mostrarAlerta(Alert.AlertType.INFORMATION, "Reparación asignada", "El dispositivo fue registrado correctamente.");

            } else {
                // ACTUALIZAR dispositivo existente
                String sqlUpdate = "UPDATE dispositivos SET NombreCliente=?, NombreDispositivo=?, MarcaDispositivo=?, DañoDispositivo=?, FotoDispositivo=? WHERE id_dispositivo=?";
                PreparedStatement psUpdate = conn.prepareStatement(sqlUpdate);
                psUpdate.setString(1, cliente);
                psUpdate.setString(2, nombreDispositivo);
                psUpdate.setString(3, marca);
                psUpdate.setString(4, daño);

                if (imagenSeleccionada != null) {
                    FileInputStream fis = new FileInputStream(imagenSeleccionada);
                    psUpdate.setBinaryStream(5, fis, (int) imagenSeleccionada.length());
                } else {
                    psUpdate.setNull(5, java.sql.Types.BLOB);
                }

                psUpdate.setInt(6, idDispositivoEdicion);
                psUpdate.executeUpdate();

                // Verificar empleado anterior
                String sqlEmpleadoAnterior = "SELECT id_empleado FROM reparaciones WHERE id_dispositivo = ?";
                PreparedStatement psAnterior = conn.prepareStatement(sqlEmpleadoAnterior);
                psAnterior.setInt(1, idDispositivoEdicion);
                ResultSet rsAnterior = psAnterior.executeQuery();

                int idEmpleadoAnterior = 0;
                if (rsAnterior.next()) {
                    idEmpleadoAnterior = rsAnterior.getInt("id_empleado");
                }

                // Actualizar reparación
                String sqlUpdateRep = "UPDATE reparaciones SET descripcion_problema=?, id_empleado=? WHERE id_dispositivo=?";
                PreparedStatement psUpdateRep = conn.prepareStatement(sqlUpdateRep);
                psUpdateRep.setString(1, daño);
                psUpdateRep.setInt(2, idEmpleado);
                psUpdateRep.setInt(3, idDispositivoEdicion);
                psUpdateRep.executeUpdate();
                
                
// 🔹 AJUSTAR ASIGNACIONES SI CAMBIÓ EL EMPLEADO (bloque robusto con debug)
if (idEmpleadoAnterior != idEmpleado && idEmpleadoAnterior != 0) {
    try {
        // 1) Leer valor actual de Asignaciones del empleado anterior
        String sqlGetAnterior = "SELECT COALESCE(Asignaciones, 0) AS asignaciones_actual FROM empleados WHERE id_empleado = ?";
        PreparedStatement psGetAnterior = conn.prepareStatement(sqlGetAnterior);
        psGetAnterior.setInt(1, idEmpleadoAnterior);
        ResultSet rsGetAnterior = psGetAnterior.executeQuery();

        int asignacionesPrev = 0;
        if (rsGetAnterior.next()) {
            asignacionesPrev = rsGetAnterior.getInt("asignaciones_actual");
        }

        System.out.println("DEBUG: idEmpleadoAnterior=" + idEmpleadoAnterior + " AsignacionesAntes=" + asignacionesPrev);

        // 2) Calcular nuevo valor (evitar negativos)
        int nuevoValorPrev = asignacionesPrev - 1;
        if (nuevoValorPrev < 0) nuevoValorPrev = 0;

        // 3) Actualizar con el nuevo valor concreto
        String sqlUpdatePrev = "UPDATE empleados SET Asignaciones = ? WHERE id_empleado = ?";
        PreparedStatement psUpdatePrev = conn.prepareStatement(sqlUpdatePrev);
        psUpdatePrev.setInt(1, nuevoValorPrev);
        psUpdatePrev.setInt(2, idEmpleadoAnterior);
        int filasPrev = psUpdatePrev.executeUpdate();
        System.out.println("DEBUG: Update anterior filas afectadas=" + filasPrev + " NuevoValor=" + nuevoValorPrev);

        // 4) Leer valor actual del nuevo empleado (por seguridad)
        String sqlGetNuevo = "SELECT COALESCE(Asignaciones, 0) AS asignaciones_actual FROM empleados WHERE id_empleado = ?";
        PreparedStatement psGetNuevo = conn.prepareStatement(sqlGetNuevo);
        psGetNuevo.setInt(1, idEmpleado);
        ResultSet rsGetNuevo = psGetNuevo.executeQuery();

        int asignacionesNuevo = 0;
        if (rsGetNuevo.next()) {
            asignacionesNuevo = rsGetNuevo.getInt("asignaciones_actual");
        }

        System.out.println("DEBUG: idEmpleadoNuevo=" + idEmpleado + " AsignacionesAntes=" + asignacionesNuevo);

        // 5) Sumar 1 al nuevo empleado (evitar NULL con COALESCE al select ya hecho)
        int nuevoValorNuevo = asignacionesNuevo + 1;
        String sqlUpdateNuevo = "UPDATE empleados SET Asignaciones = ? WHERE id_empleado = ?";
        PreparedStatement psUpdateNuevo = conn.prepareStatement(sqlUpdateNuevo);
        psUpdateNuevo.setInt(1, nuevoValorNuevo);
        psUpdateNuevo.setInt(2, idEmpleado);
        int filasNuevo = psUpdateNuevo.executeUpdate();
        System.out.println("DEBUG: Update nuevo filas afectadas=" + filasNuevo + " NuevoValor=" + nuevoValorNuevo);

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}



                mostrarAlerta(Alert.AlertType.INFORMATION, "Actualizado", "Los cambios se guardaron correctamente.");
            }

            limpiarCampos();

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta(Alert.AlertType.ERROR, "Error", "No se pudo guardar la reparación.\n" + e.getMessage());
        }
    }

    private void limpiarCampos() {
        txtCliente.clear();
        txtNombre.clear();
        txtMarca.clear();
        txtDaño.clear();
        comboBoxEmpleado.setValue(null);
        imageViewFoto.setImage(null);
        imagenSeleccionada = null;
        idDispositivoEdicion = null;
        btnAgregar.setText("Agregar Dispositivo");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alerta = new Alert(tipo);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }
}
