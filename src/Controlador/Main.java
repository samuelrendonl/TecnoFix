package Controlador;

import Modelo.ConexionBD;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.sql.Connection;

public class Main extends Application {

    public static String nombreUsuario; 
    public static String tipoUsuario;   
    public static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Login.fxml"));
        Scene scene = new Scene(root);
        
         primaryStage.getIcons().add(new javafx.scene.image.Image(getClass().getResourceAsStream("/Imagenes/logoTecnoFix.png")));
        
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static Object changeScene(String fxml, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/Vista/" + fxml));
            Parent pane = loader.load();

            if (primaryStage.getScene() != null) {
                primaryStage.getScene().setRoot(pane);
            } else {
                Scene scene = new Scene(pane);
                primaryStage.setScene(scene);
            }

            primaryStage.setTitle(title);
            primaryStage.show();

            return loader.getController();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static class TestConexion {
        public static void main(String[] args) {
            Connection conn = ConexionBD.conectar();
            if (conn != null) {
                System.out.println("Conexión lista para consultas.");
            } else {
                System.out.println("Error en la conexión a la base de datos.");
            }
        }
    }
}
