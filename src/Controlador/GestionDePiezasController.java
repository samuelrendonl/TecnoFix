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
public class GestionDePiezasController implements Initializable {


        @FXML
    private Button btnInicio;
     @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }
    
        @FXML
    private Button AñadirPieza;
     @FXML
    private void AñadirPiezaAction(ActionEvent event) {
        Main.changeScene("AñadirPieza.fxml", "Añadir Pieza");
    }
    
    @FXML
    private Button EliminarPieza;
     @FXML
    private void EliminarPiezaAction(ActionEvent event) {
        Main.changeScene("EliminarPieza.fxml", "Eliminar Pieza");
    }
    
    
    @FXML
    private Button EditarPieza;
     @FXML
    private void EditarPiezaAction(ActionEvent event) {
        Main.changeScene("EditarPieza.fxml", "Editar Pieza");
    }
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
