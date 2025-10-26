
package Controlador;
import java.sql.Connection;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import Modelo.ConexionBD;
import Modelo.Dispositivo;
import Modelo.Empleado;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;


public class CompletarReparacionController implements Initializable {

@FXML
private Button btnAggImagen, btnCompletarReparacion,btnInicio, btnVolver;
@FXML
private ComboBox<String> comboTipo;
@FXML
private ComboBox<String> comboPieza;
@FXML
private Label lblCostoPieza;
@FXML
private TextField txtPrecio;
@FXML
private ImageView ImagenNueva;
@FXML
private Connection conexion;
@FXML
private File archivoSeleccionado = null; // se asigna en ActionAggImagen
@FXML
private int idReparacionSeleccionada;

private int idReparacionActual;  // ID de la reparación seleccionada
private Empleado tecnicoLogueado; // Objeto del técnico que inició sesión


    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("RealizarReparacion.fxml", "Realizar Reparacion");
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazEmpleado.fxml", "Panel Empleado");
    }



@FXML
private void ActionAggImagen(ActionEvent event) {
    // Crear el selector de archivos
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Seleccionar imagen de la reparación");

    // Filtros para solo permitir imágenes
    FileChooser.ExtensionFilter filtroImagen = new FileChooser.ExtensionFilter(
            "Archivos de imagen", "*.png", "*.jpg", "*.jpeg", "*.bmp");
    fileChooser.getExtensionFilters().add(filtroImagen);

    // Abrir el explorador de archivos
    File archivoSeleccionado = fileChooser.showOpenDialog(new Stage());

    // Si el usuario selecciona un archivo, mostrarlo en el ImageView
    if (archivoSeleccionado != null) {
        Image imagen = new Image(archivoSeleccionado.toURI().toString());
        ImagenNueva.setImage(imagen);
    }
}


@FXML
private void ActionCompletarReparacion(ActionEvent event) {
    if (txtPrecio.getText().isEmpty()) {
        mostrarError("Campo vacío", "Por favor ingrese el precio de la reparación.");
        return;
    }

    int precioReparacion;
    try {
        precioReparacion = Integer.parseInt(txtPrecio.getText());
    } catch (NumberFormatException e) {
        mostrarError("Dato inválido", "El precio debe ser un número entero válido.");
        return;
    }

    String piezaSeleccionada = comboPieza.getValue();
    if (piezaSeleccionada == null || piezaSeleccionada.isEmpty()) {
        mostrarError("Selección requerida", "Debe seleccionar una pieza utilizada.");
        return;
    }

    try (Connection conexion = ConexionBD.conectar()) {
        conexion.setAutoCommit(false); // 🔒 Iniciamos transacción

        // 1️⃣ Obtener información de la pieza
        String sqlPrecio = "SELECT id_pieza, precio, cantidad FROM pieza WHERE nombre = ?";
        PreparedStatement psPrecio = conexion.prepareStatement(sqlPrecio);
        psPrecio.setString(1, piezaSeleccionada);
        ResultSet rsPrecio = psPrecio.executeQuery();

        int idPieza = 0;
        int cantidadActual = 0;
        int precioPieza = 0;

        if (rsPrecio.next()) {
            idPieza = rsPrecio.getInt("id_pieza");
            precioPieza = rsPrecio.getInt("precio");
            cantidadActual = rsPrecio.getInt("cantidad");
        } else {
            mostrarError("Error", "No se encontró información de la pieza seleccionada.");
            conexion.rollback();
            return;
        }

        if (cantidadActual <= 0) {
            mostrarError("Sin stock", "No hay unidades disponibles de la pieza seleccionada.");
            conexion.rollback();
            return;
        }

        // 2️⃣ Actualizar stock de la pieza
        String sqlUpdatePieza = "UPDATE pieza SET cantidad = cantidad - 1 WHERE id_pieza = ?";
        PreparedStatement psUpdate = conexion.prepareStatement(sqlUpdatePieza);
        psUpdate.setInt(1, idPieza);
        psUpdate.executeUpdate();

        // 3️⃣ Calcular costo total
        int costoTotal = precioReparacion + precioPieza;

        // 4️⃣ Obtener datos del dispositivo y reparación
        String sqlDatos = """
            SELECT d.NombreCliente, r.descripcion_problema, d.FotoDispositivo
            FROM reparaciones r
            INNER JOIN dispositivos d ON r.id_dispositivo = d.id_dispositivo
            WHERE r.id_reparacion = ?
        """;
        PreparedStatement psDatos = conexion.prepareStatement(sqlDatos);
        psDatos.setInt(1, idReparacionSeleccionada);
        ResultSet rsDatos = psDatos.executeQuery();

        String nombreCliente = "";
        String descripcionProblema = "";
        byte[] fotoOriginal = null;

        if (rsDatos.next()) {
            nombreCliente = rsDatos.getString("NombreCliente");
            descripcionProblema = rsDatos.getString("descripcion_problema");
            fotoOriginal = rsDatos.getBytes("FotoDispositivo");
        } else {
            mostrarError("Error", "No se encontró información de la reparación.");
            conexion.rollback();
            return;
        }

        // 5️⃣ Insertar en reparaciones_finalizadas
        String sqlInsertFinal = """
            INSERT INTO reparaciones_finalizadas
            (id_reparacion, nombrecliente, descripcion_problema, pieza_usada, costo_total, tecnicoasignado, fecha_finalizacion, estado, foto_final)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        PreparedStatement psInsertFinal = conexion.prepareStatement(sqlInsertFinal);
        psInsertFinal.setInt(1, idReparacionSeleccionada);
        psInsertFinal.setString(2, nombreCliente);
        psInsertFinal.setString(3, descripcionProblema);
        psInsertFinal.setString(4, piezaSeleccionada);
        psInsertFinal.setInt(5, costoTotal);
        psInsertFinal.setString(6, tecnicoLogueado != null ? tecnicoLogueado.getNombre() : "Desconocido");
        psInsertFinal.setDate(7, java.sql.Date.valueOf(LocalDate.now()));
        psInsertFinal.setString(8, "Completado");

        if (archivoSeleccionado != null) {
            try (InputStream imagenStream = new FileInputStream(archivoSeleccionado)) {
                psInsertFinal.setBinaryStream(9, imagenStream, (int) archivoSeleccionado.length());
            }
        } else if (fotoOriginal != null) {
            psInsertFinal.setBytes(9, fotoOriginal);
        } else {
            psInsertFinal.setNull(9, java.sql.Types.BLOB);
        }

        psInsertFinal.executeUpdate();

// 6️⃣ Insertar o actualizar el contador de uso de la pieza
String sqlInsertPieza = """
    INSERT INTO reparacion_pieza (id_pieza, cantidad_usada)
    VALUES (?, 1)
    ON DUPLICATE KEY UPDATE cantidad_usada = cantidad_usada + 1
""";

PreparedStatement psInsertPieza = conexion.prepareStatement(sqlInsertPieza);
psInsertPieza.setInt(1, idPieza);
psInsertPieza.executeUpdate();


        // 7️⃣ Borrar reparación de tabla principal
        String sqlDelete = "DELETE FROM reparaciones WHERE id_reparacion = ?";
        PreparedStatement psDelete = conexion.prepareStatement(sqlDelete);
        psDelete.setInt(1, idReparacionSeleccionada);
        psDelete.executeUpdate();

        conexion.commit(); // ✅ Confirmamos todo

        mostrarExito("Reparación completada", "La reparación fue registrada correctamente.");
        Main.changeScene("DispositivosAsignados.fxml", "Dispositivos Asignados");

    } catch (Exception e) {
        e.printStackTrace();
        mostrarError("Error al completar reparación", e.getMessage());
    }
}



// Helper para mostrar alertas
private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
    Alert alert = new Alert(tipo);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}


// Método para mostrar alertas
private void mostrarAlerta(String titulo, String mensaje) {
    Alert alerta = new Alert(Alert.AlertType.INFORMATION);
    alerta.setTitle(titulo);
    alerta.setHeaderText(null);
    alerta.setContentText(mensaje);
    alerta.showAndWait();
}


private void mostrarError(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}

private void mostrarExito(String titulo, String mensaje) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(titulo);
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}


   @Override
public void initialize(URL url, ResourceBundle rb) {
    conexion = (Connection) ConexionBD.conectar();

    if (Main.reparacionSeleccionada != null) {
        idReparacionSeleccionada = Main.reparacionSeleccionada.getIdReparacion();
        System.out.println("ID de reparación seleccionado: " + idReparacionSeleccionada);
    } else {
        System.out.println("No se recibió reparación seleccionada.");
    }

    // Cargar tipos fijos en el comboTipo
    comboTipo.getItems().addAll(
        "Pantalla",
        "Batería",
        "Cámara",
        "Altavoz",
        "Micrófono",
        "Conector de carga",
        "Botón de encendido",
        "Botón de volumen",
        "Placa madre",
        "Flex de conexión",
        "Tapa trasera",
        "Sensor de huella",
        "Antena",
        "Chasis / Marco",
        "Cable USB o adaptador"
    );

    comboTipo.setOnAction(event -> cargarPiezasPorTipo());
    comboPieza.setOnAction(event -> mostrarPrecioPieza());
}


    private void cargarPiezasPorTipo() {
        String tipoSeleccionado = comboTipo.getValue();
        if (tipoSeleccionado == null || tipoSeleccionado.isEmpty()) return;

        ObservableList<String> listaPiezas = FXCollections.observableArrayList();

        try {
            String sql = "SELECT nombre FROM pieza WHERE tipo = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, tipoSeleccionado);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                listaPiezas.add(rs.getString("nombre"));
            }

            comboPieza.setItems(listaPiezas);
            comboPieza.setValue(null); // limpiar selección anterior
            lblCostoPieza.setText(""); // limpiar precio

        } catch (SQLException e) {
            mostrarError("Error al cargar piezas", e.getMessage());
        }
    }

    private void mostrarPrecioPieza() {
        String piezaSeleccionada = comboPieza.getValue();
        if (piezaSeleccionada == null || piezaSeleccionada.isEmpty()) return;

        try {
            String sql = "SELECT precio FROM pieza WHERE nombre = ?";
            PreparedStatement ps = conexion.prepareStatement(sql);
            ps.setString(1, piezaSeleccionada);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblCostoPieza.setText("$" + rs.getInt("precio"));
            } else {
                lblCostoPieza.setText("No disponible");
            }

        } catch (SQLException e) {
            mostrarError("Error al obtener precio", e.getMessage());
        }
    }





}  
    
