package dad.autoescuela.resources.images;

import javafx.scene.image.Image;

public class Images {
	
	private static final String RAIZ = "dad/autoescuela/resources/images/";
	
	public static final String INSERT_IMAGE = "insertarImagen.png";
	public static final String NO_IMAGE = "sinImagen.jpg";
	
	public static Image cargarIcono(String nombre){
		String ruta = RAIZ + nombre;
		
		return new Image(ruta);
	}
	
	public static String darURL(String nombre){
		String ruta = RAIZ + nombre;
		
		return ruta;
	}
}