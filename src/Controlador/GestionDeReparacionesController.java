package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class GestionDeReparacionesController implements Initializable {

@FXML
private Button ReparacionesCompletadas, EliminarReparacion, EditarReparaciones, btnInicio, btnVisualizarReparaciones;

@FXML
private void ReparacionesCompletadasAction(ActionEvent event){
  Main.changeScene("ReparacionesCompletadas.fxml", "Reparaciones Completadas");  
}

@FXML
private void EliminarReparacionAction(ActionEvent event){
 Main.changeScene("EliminarReparaciones.fxml", "Eliminar Reparaciones");   
}

@FXML
private void InicioAction(ActionEvent event){
  Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");      
}


@FXML
private void VisualizarReparacionesAtcion(ActionEvent event){
  Main.changeScene("VisualizarReparaciones.fxml", "Reparaciones");      
}



    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
