package Controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;



public class ReparacionesCompletadasController implements Initializable {


    
    @FXML
    private Button btnInicio;
    
    
    @FXML
    private void InicioAction(ActionEvent event) {
        Main.changeScene("InterfazAdministrador.fxml", "Panel Administrador");
    }
            @FXML
    private void volverAction(ActionEvent event){
      Main.changeScene("GestionDeReparaciones.fxml", "Gestor de Reparaciones");  
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
