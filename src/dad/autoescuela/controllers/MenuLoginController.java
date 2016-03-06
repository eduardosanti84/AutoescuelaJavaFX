package dad.autoescuela.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dad.autoescuela.Main;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.services.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MenuLoginController {
	
	private Main main;
	
	@FXML
	private Button conectarButton;
	@FXML
	private TextField usuarioTextField;
	@FXML
	private PasswordField passwordField;

	@FXML
	private void initialize() {
		
		usuarioTextField.setText("7856020A");
		passwordField.setText("1234789");
		///////////////////////////////////////////////////////////////////////////////////// TODO
		///////////////////////////////////////////////////////////////////////////////////// FORMULARIO
		///////////////////////////////////////////////////////////////////////////////////// USUARIO
		///////////////////////////////////////////////////////////////////////////////////// /////
		conectarButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				Usuario usuario = new Usuario();
				usuario.setDni(usuarioTextField.getText());
				usuario.setPass(passwordField.getText());
				
				List<Usuario> usuarios = new ArrayList<Usuario>();
				usuarios = ServiceLocator.getUsuarioServices().listarUsuarios();
				int i;
				for(i = 0; i < usuarios.size() && !usuarios.get(i).getDni().equalsIgnoreCase(usuario.getDni()); i++);
				if(i == usuarios.size()){
					
				}
				else{
					if(usuario.getPass().equals(usuarios.get(i).getPass())){
						if(usuarios.get(i).isProfesor()){
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(Main.class.getResource("ui/MenuProfesor.fxml"));
							try {
					            Parent root1 = (Parent) loader.load();
					            Stage stage = new Stage();
					            stage.initModality(Modality.APPLICATION_MODAL);
					            stage.initStyle(StageStyle.DECORATED);
					            stage.setTitle("Profesor");
					            stage.setScene(new Scene(root1));  
					            stage.show();
					            
					            main.getStage().close();
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
						}
						else{
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(Main.class.getResource("ui/MenuAlumno.fxml"));
							try {
					            Parent root1 = (Parent) loader.load();
					            Stage stage = new Stage();
					            stage.initModality(Modality.APPLICATION_MODAL);
					            stage.initStyle(StageStyle.DECORATED);
					            stage.setTitle("Alumno");
					            stage.setScene(new Scene(root1));  
					            stage.show();
					            
					            main.getStage().close();
					        } catch (IOException e) {
					            e.printStackTrace();
					        }
						}
					}
					else{ 
					}
				}
			}
		});
		/////////////////////////////////////////////////////////////////////////////////////////// TODO
		/////////////////////////////////////////////////////////////////////////////////////////// VER
		/////////////////////////////////////////////////////////////////////////////////////////// USUARIOS
		/////////////////////////////////////////////////////////////////////////////////////////// /////
	}

	public void setMain(Main main) {
		this.main = main;
	}
}
