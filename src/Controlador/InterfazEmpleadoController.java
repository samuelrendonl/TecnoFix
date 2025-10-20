package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;   
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button; 
import javafx.scene.control.ButtonType;


public class InterfazEmpleadoController implements Initializable {

    @FXML
    private Button btnDispositivos; 

    @FXML
    private void ActionDispositivos(ActionEvent event) {
    Main.changeScene("VisualizarDispositivos.fxml", "Dispositivos");
    }
    
        @FXML
    private Button btnDispositivosAsignados; 

    @FXML
    private void ActionDispositivosAsignados(ActionEvent event) {

    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    } 
    
    
            @FXML
    private Button btnLogOut;

@FXML
private void ActionLogOut(ActionEvent event) {
    String nombre = Main.nombreUsuario;
    String rol = Main.tipoUsuario;

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("Cerrar sesión");
    alert.setHeaderText(null);
    if ("admin".equals(Main.tipoUsuario)){
        alert.setContentText("¿Desea cerrar la sesión Administrativa de " + nombre + "?");   
        }else if ("empleado".equals(Main.tipoUsuario)){
         alert.setContentText("¿Desea cerrar la sesión Operativa de" + nombre + "?");   
     }
    
    
        alert.showAndWait().ifPresent(response -> {
        if (response == ButtonType.OK) {
            Main.changeScene("Login.fxml", "Login");
        }
    });
    }
}