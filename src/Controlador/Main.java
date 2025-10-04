/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
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

    private static Stage primaryStage; 
public static String tipoUsuario;
      @Override
    public void start(Stage stage) throws IOException {
   
          primaryStage = stage;
        // Cargamos la primera escena (Login.fxml)
        Parent root = FXMLLoader.load(getClass().getResource("/Vista/Login.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    public static void changeScene(String fxml, String title) {
        try {
            // Cargar el nuevo contenido FXML
            Parent pane = FXMLLoader.load(Main.class.getResource("/Vista/" + fxml));
            primaryStage.getScene().setRoot(pane);
            primaryStage.setTitle(title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
   
    
// Conexion a base de datos 
public class TestConexion {
    public static void main(String[] args) {
        Connection conn = ConexionBD.conectar();
        if (conn != null) {
            System.out.println("Conexión lista para consultas.");
        }
    }
}
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
