/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class InterfazAdministradorController implements Initializable {

    @FXML
    private Button btnTrabajadores;

    @FXML
    private void ActionTrabajadores(ActionEvent event) {
    Main.changeScene("GestionDeTrabajadores.fxml", "Gestor De Trabajadores");   
    }

    @FXML
    private Button btnReparaciones;

    @FXML
    private void ActionReparaciones(ActionEvent event) {
        
    }

    @FXML
    private Button btnPiezas;

    @FXML
    private void ActionPiezas(ActionEvent event) {
     Main.changeScene("GestionDePiezas.fxml", "Gestor De Piezas"); 
    }

    @FXML
    private Button btnAggDispositivo;

    @FXML
    private void ActionAggDispositivo(ActionEvent event) {
    Main.changeScene("AgregarDispositivo.fxml", "Agregar Dispositivo");    
    }

@FXML
private Button btnDispositivos;

@FXML
private void ActionDispositivos(ActionEvent event) {
Main.changeScene("Dispositivos.fxml", "Dispositivos");
    
}

    @FXML
    private Button btnEstadisticas;

    @FXML
    private void ActionEstadisticas(ActionEvent event) {
     Main.changeScene("Estadisticas.fxml", "Estadisticas");  
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
