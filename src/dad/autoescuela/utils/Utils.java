package dad.autoescuela.utils;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class Utils {

	public static void mensaje(AlertType alertType, String titulo, String cabecera, String contenido){
		
		Alert alert = new Alert(alertType);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecera);
		alert.setContentText(contenido);
		alert.show();
	}
	
	public static Boolean confirmacion(String titulo, String cabecera, String contenido) {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecera);
		alert.setContentText(contenido);
		Optional<ButtonType> result = alert.showAndWait();
		if ((result.isPresent()) && (result.get() == ButtonType.OK))
		    return true;
		else
			return false;
	}
}
