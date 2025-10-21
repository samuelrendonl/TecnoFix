package Controlador;

import Modelo.ConexionBD;
import Modelo.Pieza;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class AgregarPiezaController implements Initializable {

    public static Pieza piezaAEditar = null;

    @FXML
    private Button btnInicio, btnvolver, btnAggPieza, btnAggImagen;

    @FXML
    private TextField txtNombre, txtPrecio, txtCantidad, txtProveedor, txtMarca;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private ImageView imagePreview;

    private File archivoImagenSeleccionado;
    private byte[] bytesImagen;

    @FXML
    private void volverAction(ActionEvent event) {
        Main.changeScene("GestionDePiezas.fxml", "Gestor De Piezas");
        piezaAEditar = null;
    }

    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
        piezaAEditar = null;
    }

    @FXML
    private void AggImagenAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar Imagen");
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.png", "*.jpeg")
        );

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            archivoImagenSeleccionado = file;
            try (FileInputStream fis = new FileInputStream(file)) {
                bytesImagen = fis.readAllBytes();
                Image img = new Image(new ByteArrayInputStream(bytesImagen));
                imagePreview.setImage(img);
            } catch (IOException e) {
                mostrarAlerta("Error", "No se pudo cargar la imagen: " + e.getMessage());
            }
        }
    }

    @FXML
    private void AggPiezaAction(ActionEvent event) {
        String nombre = txtNombre.getText().trim();
        String tipo = comboTipo.getValue();
        String precioTexto = txtPrecio.getText().trim();
        String cantidadTexto = txtCantidad.getText().trim();
        String proveedor = txtProveedor.getText().trim();
        String marca = txtMarca.getText().trim();

        if (nombre.isEmpty() || tipo == null || precioTexto.isEmpty() || cantidadTexto.isEmpty()
                || proveedor.isEmpty() || marca.isEmpty()) {
            mostrarAlerta("Error", "Todos los campos son obligatorios.");
            return;
        }

        int precio, cantidad;
        try {
            precio = Integer.parseInt(precioTexto);
            cantidad = Integer.parseInt(cantidadTexto);
            if (precio <= 0 || cantidad < 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Error", "El precio y la cantidad deben ser números enteros válidos.");
            return;
        }

        try (Connection conn = ConexionBD.conectar()) {
            if (conn == null) {
                mostrarAlerta("Error", "No se pudo conectar con la base de datos.");
                return;
            }

            if (piezaAEditar == null) {
                // 🔹 Modo AGREGAR
                if (bytesImagen == null) {
                    mostrarAlerta("Error", "Debe seleccionar una imagen para la pieza nueva.");
                    return;
                }

                String sql = "INSERT INTO pieza (nombre, tipo, precio, cantidad, proveedor, marca, imagen) VALUES (?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, nombre);
                ps.setString(2, tipo);
                ps.setInt(3, precio);
                ps.setInt(4, cantidad);
                ps.setString(5, proveedor);
                ps.setString(6, marca);
                ps.setBytes(7, bytesImagen);

                int filas = ps.executeUpdate(); // 🔹 aquí estaba el problema

                if (filas > 0) {
                    mostrarAlerta("Éxito", "Pieza agregada correctamente.");
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se insertó la pieza en la base de datos.");
                }

            } else {
                // 🔹 Modo EDITAR
                String sql;
                PreparedStatement ps;

                if (bytesImagen != null) {
                    sql = "UPDATE pieza SET nombre=?, tipo=?, precio=?, cantidad=?, proveedor=?, marca=?, imagen=? WHERE id_pieza=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setString(2, tipo);
                    ps.setInt(3, precio);
                    ps.setInt(4, cantidad);
                    ps.setString(5, proveedor);
                    ps.setString(6, marca);
                    ps.setBytes(7, bytesImagen);
                    ps.setInt(8, piezaAEditar.getId());
                } else {
                    sql = "UPDATE pieza SET nombre=?, tipo=?, precio=?, cantidad=?, proveedor=?, marca=? WHERE id_pieza=?";
                    ps = conn.prepareStatement(sql);
                    ps.setString(1, nombre);
                    ps.setString(2, tipo);
                    ps.setInt(3, precio);
                    ps.setInt(4, cantidad);
                    ps.setString(5, proveedor);
                    ps.setString(6, marca);
                    ps.setInt(7, piezaAEditar.getId());
                }

                int filas = ps.executeUpdate();

                if (filas > 0) {
                    mostrarAlerta("Éxito", "Pieza actualizada correctamente.");
                    piezaAEditar = null;
                    limpiarCampos();
                } else {
                    mostrarAlerta("Error", "No se pudo actualizar la pieza.");
                }
            }

        } catch (SQLException e) {
            mostrarAlerta("Error SQL", e.getMessage());
            e.printStackTrace();
        }
    }

    private void limpiarCampos() {
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
        comboTipo.getSelectionModel().clearSelection();
        txtProveedor.clear();
        txtMarca.clear();
        imagePreview.setImage(null);
        bytesImagen = null;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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

        if (piezaAEditar != null) {
            txtNombre.setText(piezaAEditar.getNombre());
            txtPrecio.setText(String.valueOf(piezaAEditar.getPrecio()));
            txtCantidad.setText(String.valueOf(piezaAEditar.getCantidad()));
            comboTipo.setValue(piezaAEditar.getTipo());
            txtProveedor.setText(piezaAEditar.getProveedor());
            txtMarca.setText(piezaAEditar.getMarca());

            if (piezaAEditar.getImagen() != null) {
                Image img = new Image(new ByteArrayInputStream(piezaAEditar.getImagen()));
                imagePreview.setImage(img);
            }
        }
    }
}
