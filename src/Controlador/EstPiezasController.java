package Controlador;

import Modelo.ConexionBD;
import Modelo.Pieza;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class EstPiezasController implements Initializable {

    @FXML
    private ComboBox<String> comboTipo; 

    @FXML
    private TableView<Pieza> tablaPiezas;
    @FXML
    private TableColumn<Pieza, Integer> colId;
    @FXML
    private TableColumn<Pieza, String> colNombre;
    @FXML
    private TableColumn<Pieza, String> colTipo;
    @FXML
    private TableColumn<Pieza, Double> colPrecio;
    @FXML
    private TableColumn<Pieza, Integer> colCantidad;
    @FXML
    private TableColumn<Pieza, Integer> colUsadas;

    private ObservableList<Pieza> listaPiezas = FXCollections.observableArrayList();
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

        
        comboTipo.setItems(FXCollections.observableArrayList(
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
        ));

        configurarColumnas();
        cargarPiezas(null);

        // 🔹 Agregar evento: cuando cambie la selección, filtra
        comboTipo.setOnAction(event -> {
            String tipoSeleccionado = comboTipo.getValue();
            if (tipoSeleccionado != null && !tipoSeleccionado.isEmpty()) {
                cargarPiezas(tipoSeleccionado);
            } else {
                cargarPiezas(null);
            }
        });
    }

    private void configurarColumnas() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        colUsadas.setCellValueFactory(new PropertyValueFactory<>("usadas"));
    }

    private void cargarPiezas(String tipoFiltro) {
        listaPiezas.clear();
        Connection conn = ConexionBD.conectar();
        if (conn == null) {
            System.out.println("❌ No se pudo establecer conexión con la base de datos.");
            return;
        }

        String sql = """
            SELECT p.id_pieza, p.nombre, p.tipo, p.precio, p.cantidad,
                   IFNULL(SUM(rp.cantidad_usada), 0) AS usadas
            FROM pieza p
            LEFT JOIN reparacion_pieza rp ON p.id_pieza = rp.id_pieza
        """;

        if (tipoFiltro != null) {
            sql += " WHERE p.tipo = ? ";
        }

        sql += " GROUP BY p.id_pieza, p.nombre, p.tipo, p.precio, p.cantidad ORDER BY p.id_pieza;";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (tipoFiltro != null) {
                stmt.setString(1, tipoFiltro);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pieza pieza = new Pieza(
                        rs.getInt("id_pieza"),
                        rs.getString("nombre"),
                        rs.getString("tipo"),
                        rs.getInt("precio"),
                        rs.getInt("cantidad")
                );
                pieza.setUsadas(rs.getInt("usadas"));
                listaPiezas.add(pieza);
            }

            tablaPiezas.setItems(listaPiezas);

        } catch (SQLException e) {
            System.out.println("⚠️ Error al cargar piezas: " + e.getMessage());
        }
    }
}
