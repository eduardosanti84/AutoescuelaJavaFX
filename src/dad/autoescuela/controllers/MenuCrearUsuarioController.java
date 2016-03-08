package dad.autoescuela.controllers;

import dad.autoescuela.Main;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.services.ServiceLocator;
import dad.autoescuela.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;

public class MenuCrearUsuarioController {

	private Main main;

	@FXML
	private ToggleButton profesorButton;
	@FXML
	private Button guardarUsuarioButton;
	@FXML
	private Button guardarCerrarUsuarioButton;
	@FXML
	private TextField nombreTextField;
	@FXML
	private TextField dniTextField;
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private TableView<Usuario> tablaUsuarios;
	@FXML
	private TableColumn<Usuario, String> dniColumn;
	@FXML
	private TableColumn<Usuario, String> nombreColumn;
	@FXML
	private TableColumn<Usuario, Boolean> profesorColumn;

	@FXML
	private Button eliminarUsuarioButton;
	
	@FXML
	private void initialize(){
		////////////////////////////////////////////////////////////////////////////////TODO ESTADOS DEL TOOGLEBUTTON /////
		profesorButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
			if(newValue){
				profesorButton.setText("SI");
			}
			else
				profesorButton.setText("NO");
		});	
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												LISTENER
	 *********************************************************************************************************************
	 **/
	/////////////////////////////////////////////////////////////////////////////////////TODO FORMULARIO USUARIO /////
	@FXML
	public void onGuardarUsuarioButtonAction(){
		if(!nombreTextField.getText().isEmpty() && !dniTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()){

			Usuario usuario = new Usuario();
			usuario.setNombre(nombreTextField.getText());
			usuario.setDni(dniTextField.getText());
			usuario.setPass(passwordTextField.getText());
			usuario.setProfesor(profesorButton.isSelected());
			
			if(ServiceLocator.getUsuarioServices().crearUsuario(usuario)){
				Utils.mensaje(AlertType.INFORMATION, "Correcto", "Confirmacion de Inserción", "Se ha registrado el nuevo usuario!");
				limpiarFormularioUsusario();
			}
			else Utils.mensaje(AlertType.ERROR, "Error", "Error al crear", "Ha ocurrido un error al crear el usuario, comprueba los datos.!");
		}
		else  Utils.mensaje(AlertType.ERROR, "Error", "Comprobación de los datos", "Faltan datos!");
	}
	
	@FXML
	public void onGuardarCerrarUsuarioButtonAction(){
		onGuardarUsuarioButtonAction();
		main.getStagesCrearUsuario().close();
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												METODOS
	 *********************************************************************************************************************
	 **/
	private void limpiarFormularioUsusario(){
		nombreTextField.setText("");
		dniTextField.setText("");
		passwordTextField.setText("");
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
