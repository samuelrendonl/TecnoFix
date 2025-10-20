/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package Controlador;

import javafx.event.ActionEvent;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

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
     Main.changeScene("GestionDeReparaciones.fxml", "Gestor De Piezas");   
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


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
}
