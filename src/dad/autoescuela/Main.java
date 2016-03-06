package dad.autoescuela;

import java.io.IOException;

import dad.autoescuela.controllers.MenuLoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	Stage stage;
	
	@Override
	public void start(Stage primaryStage) {
		stage = primaryStage;
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("ui/MenuLogin.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			
			MenuLoginController menuLoginController = loader.getController();
			menuLoginController.setMain(this);
			
			stage.setScene(scene);
			stage.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		launch(args);
	}
}