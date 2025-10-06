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
public class GestionDeReparacionesController implements Initializable {

@FXML
private Button ReparacionesCompletadas, EliminarReparacion, EditarReparaciones, btnInicio;

@FXML
private void ReparacionesCompletadasAction(ActionEvent event){
  Main.changeScene("ReparacionesCompletadas.fxml", "Reparaciones Completadas");  
}

@FXML
private void EliminarReparacionAction(ActionEvent event){
 Main.changeScene("EliminarReparaciones.fxml", "Eliminar Reparaciones");   
}

@FXML
private void EditarReparacionesAction(ActionEvent event){
 Main.changeScene("EditarReparaciones.fxml", "Editar Reparaciones");       
}

@FXML
private void InicioAction(ActionEvent event){
  Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");      
}

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
