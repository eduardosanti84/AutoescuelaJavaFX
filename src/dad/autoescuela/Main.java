package dad.autoescuela;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		
		FXMLLoader loader = new FXMLLoader();
		//loader.setLocation(Main.class.getResource("ui/Cualquiercosa.fxml"));
		
		try {
			Scene scene = new Scene(loader.load());
			
			//Controladorloquesea controlador = loader.getController();
			//controlador.setMain(this);
			
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
