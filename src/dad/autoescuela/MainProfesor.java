package dad.autoescuela;

import java.io.IOException;

import dad.autoescuela.controllers.MenuProfesorController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainProfesor extends Application{
	@Override
	public void start(Stage primaryStage) {

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ui/MenuProfesor.fxml"));
		
		try {
			Scene scene = new Scene(loader.load());
			
			MenuProfesorController menuProfesorController = loader.getController();
			menuProfesorController.setMain(this);
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
