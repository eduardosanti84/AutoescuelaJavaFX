package dad.autoescuela.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;

import dad.autoescuela.Main;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import dad.autoescuela.utils.Utils;

public class MenuLoginController {

	private Main main;
	public static Usuario usuario;
	
	@FXML
	private Button conectarButton;
	@FXML
	private TextField usuarioTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private ImageView logo;

	@FXML
	private void initialize() {
		
		usuarioTextField.setPromptText("Usuario(DNI)");
		passwordField.setPromptText("Contraseña");
		logo.setImage(Images.LOGO_IMAGE);
	}
	
	@FXML
	public void onConectarButtonAction() {
		
		usuario = new Usuario();
		usuario.setDni(usuarioTextField.getText());
		usuario.setPass(passwordField.getText());
		
		List<Usuario> usuarios = new ArrayList<Usuario>();
		usuarios = ServiceLocator.getUsuarioServices().listarUsuarios();
		int i;
		for(i = 0; i < usuarios.size() && !usuarios.get(i).getDni().equalsIgnoreCase(usuario.getDni()); i++);
		if(i != usuarios.size()){

			if(usuario.getPass().equals(usuarios.get(i).getPass())){
				ServiceLocator.getConexionServices().setUsuario(usuario);
				if(usuarios.get(i).isProfesor()){
					main.showMenuProfesor();
					main.getPrimaryStage().close();
				}
				else{
					main.showMenuAlumno();
					main.getPrimaryStage().close();
				}
			}
			else Utils.mensaje(AlertType.WARNING, "Usuario Incorrecto", 
					"Los datos no corresponden a un usuario registrado.", 
					"Revise los datos o contacte con el adminsitrador.");
		}
		else Utils.mensaje(AlertType.WARNING, "Usuario Incorrecto", 
				"Los datos no corresponden a un usuario registrado.", 
				"Revise los datos o contacte con el adminsitrador.");
	}

	public void setMain(Main main) {
		this.main = main;
	}
}
