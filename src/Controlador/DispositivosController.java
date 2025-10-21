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
public class DispositivosController implements Initializable {

    @FXML
    private Button btnInicio;
     @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }
    
    
    @FXML
    private Button VisualizarDispositivos;
     @FXML
    private void VisualizarDispositivosAction(ActionEvent event) {
        Main.changeScene("VisualizarDispositivos.fxml", "Visualizar Dispositivos");
    }    
    
    @FXML
    private Button EliminarDispositivo;
     @FXML
    private void EliminarDispositivoAction(ActionEvent event) {
        Main.changeScene("EliminarDispositivos.fxml", "Eliminar Dispositivo");
    }  
    
    
    @FXML
    private Button EditarDispositivo;
     @FXML
    private void EditarDispositivoAction(ActionEvent event) {
        Main.changeScene("EditarDispositivo.fxml",  "Editar Dispositivo");
    }  
  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
