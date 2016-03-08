package dad.autoescuela;

import java.io.IOException;

import dad.autoescuela.controllers.MenuAlumnoController;
import dad.autoescuela.controllers.MenuCrearPreguntaController;
import dad.autoescuela.controllers.MenuCrearUsuarioController;
import dad.autoescuela.controllers.MenuLoginController;
import dad.autoescuela.controllers.MenuProfesorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Stage stageProfesor;
	Stage stageAlumno;
	Stage stageCrearUsuario;
	Stage stageCrearPregunta;
	
	//Stage stage;
	Stage primaryStage;
	
	@Override
	public void start(Stage primaryStage) {
		
		primaryStage = new Stage();
		this.primaryStage = primaryStage;
		showMenuLogin();
	}

	private void showMenuLogin() {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuLogin.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuLoginController menuLoginController = loader.getController();
			menuLoginController.setMain(this);

			this.primaryStage.setScene(scene);
			this.primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMenuAlumno(){
		stageAlumno = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuAlumno.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuAlumnoController menuAlumnoController = loader.getController();
			menuAlumnoController.setMain(this);
			
			stageAlumno.setScene(scene);
			stageAlumno.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMenuProfesor(){
		
		stageProfesor = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuProfesor.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuProfesorController menuProfesorController = loader.getController();
			menuProfesorController.setMain(this);
			
			stageProfesor.setScene(scene);
			stageProfesor.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMenuCrearUsuario(){
		
		stageCrearUsuario = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuCrearUsuario.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuCrearUsuarioController menuCrearUsuarioController = loader.getController();
			menuCrearUsuarioController.setMain(this);
			
			stageCrearUsuario.setScene(scene);
			stageCrearUsuario.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
	
	public void showMenuCrearPregunta(){
		
		stageCrearPregunta = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuCrearPregunta.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuCrearPreguntaController menuCrearPreguntaController = loader.getController();
			menuCrearPreguntaController.setMain(this);
			
			stageCrearPregunta.setScene(scene);
			stageCrearPregunta.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
//	public Stage getStages() {
//		return stage;
//	}
	
	public Stage getStagesAlumno() {
		return stageAlumno;
	}
	public Stage getStagesProfesor() {
		return stageProfesor;
	}
	public Stage getStagesCrearUsuario() {
		return stageCrearUsuario;
	}
	public Stage getStagesCrearPregunta() {
		return stageCrearPregunta;
	}

	public static void main(String[] args) {
		launch(args);
	}
}