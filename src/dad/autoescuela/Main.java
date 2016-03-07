package dad.autoescuela;

import java.io.IOException;

import dad.autoescuela.controllers.MenuAlumnoController;
import dad.autoescuela.controllers.MenuLoginController;
import dad.autoescuela.controllers.MenuProfesorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Stage stage;
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
		stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuAlumno.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuAlumnoController menuAlumnoController = loader.getController();
			menuAlumnoController.setMain(this);
			
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showMenuProfesor(){
		
		stage = new Stage();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("ui/MenuProfesor.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuProfesorController menuProfesorController = loader.getController();
			menuProfesorController.setMain(this);
			
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getPrimaryStage() {
		return this.primaryStage;
	}
	
	public Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}