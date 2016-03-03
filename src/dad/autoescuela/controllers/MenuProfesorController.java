package dad.autoescuela.controllers;

import dad.autoescuela.Main;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;

public class MenuProfesorController {

private Main main;
	
	@FXML
	private ToggleButton profesorButton;
	@FXML
	private Button guardarUsuarioButton;
	@FXML
	TextField nombreTextField;
	@FXML
	TextField dniTextField;
	@FXML
	TextField passwordTextField;

	@FXML
	private void initialize(){
		
		profesorButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
			
			if(newValue){
				profesorButton.setText("SI");
			}
			else
				profesorButton.setText("NO");
				
		});

		guardarUsuarioButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {

				System.out.println("guardando..." + nombreTextField.textProperty().getValue());
				System.out.println("guardando..." + dniTextField.textProperty().getValue());
				System.out.println("guardando..." + passwordTextField.textProperty().getValue());
				System.out.println("guardando..." + profesorButton.selectedProperty().getValue());
			}
		});
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}



///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//boton.selectedProperty().addListener(new ChangeListener<Boolean>() {
//@Override
//public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//	System.out.println(newValue);
//	
//	if(newValue){
//		
//	}
//	
//}
//});
