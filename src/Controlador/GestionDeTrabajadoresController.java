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
public class GestionDeTrabajadoresController implements Initializable {

    /**
     * Initializes the controller class.
     */
    
    @FXML
    private Button btnAñadirEmpleado;
    
    @FXML
    private Button btnEliminarEmpleado;
    
    @FXML
    private Button btnEditarEmpleado;
    
    @FXML
    private Button btnInicio;
    
    
    @FXML
    private void ActionInicio(ActionEvent event){
      Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");  
    }
        @FXML
    private void AñadirEmpleado(ActionEvent event){
        Main.changeScene("AgregarTrabajador.fxml", "Añadir Empleado");
    }
    @FXML
    private void EliminarEmpleado(ActionEvent event){
        Main.changeScene("EliminarEmpleado.fxml", "Eliminar Empleado");
    }
    
        @FXML
    private void EditarEmpleado(ActionEvent event){
       Main.changeScene("EditarEmpleado.fxml", "Editar Empleado"); 
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
