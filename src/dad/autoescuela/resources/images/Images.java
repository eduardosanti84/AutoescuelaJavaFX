package dad.autoescuela.resources.images;

import javafx.scene.image.Image;

public class Images {
	
	private static final String RAIZ = "dad/autoescuela/resources/images/";
	
	public static final Image INSERT_IMAGE = cargarIcono("insertarImagen.png");
	public static final Image NO_IMAGE = cargarIcono("sinImagen.jpg");
	public static final Image CORRECT_IMAGE = cargarIcono("correcto.png");
	public static final Image INCORRECT_IMAGE = cargarIcono("incorrecto.png");
	public static final Image LOGO_IMAGE = cargarIcono("logoNY.png");
	
	public static Image cargarIcono(String nombre){
		String ruta = RAIZ + nombre;
		
		return new Image(ruta);
	}
}