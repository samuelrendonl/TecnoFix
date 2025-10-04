/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;   
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button; 

/**
 * FXML Controller class
 *
 * @author Mi PC
 */
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
    
}