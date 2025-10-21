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
public class EstatdisticasController implements Initializable {

   @FXML
   private Button btnInicio, btnESTReparaciones, btnESTEmpleadosAction, btnESTPiezas;
   
   
   @FXML
   private void ESTPiezasAction(ActionEvent event){
    Main.changeScene("EstPiezas.fxml", "Estadisticas de Piezas");
  
   }
   
   @FXML
   private void ESTEmpleadosAction(ActionEvent event){
    Main.changeScene("EstEmpleados.fxml", "Estadisticas de Empleados");
   }
   
  @FXML
  private void ESTReparacionesAction(ActionEvent event){
   Main.changeScene("EstReparaciones.fxml", "Estadisticas de Reparaciones");

  }
        @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }

    
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
