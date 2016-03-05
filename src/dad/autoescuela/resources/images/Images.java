package dad.autoescuela.resources.images;

import javafx.scene.image.Image;

public class Images {
	
	private static final String RAIZ = "dad/autoescuela/resources/images/";
	
	public static final Image INSERT_IMAGE = cargarIcono("insertarImagen.png");
	
	public static Image cargarIcono(String nombre){
		String ruta = RAIZ + nombre;
		
		return new Image(ruta);
	}
}