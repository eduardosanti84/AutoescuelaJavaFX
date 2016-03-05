package dad.autoescuela.services;

import java.io.File;

import dad.autoescuela.model.Pregunta;
import javafx.collections.ObservableList;

public interface IPreguntaServices {

	public ObservableList<Pregunta> listarPreguntas();
	public Boolean crearPregunta(Pregunta pregunta);
	public Boolean eliminarPregunta(Pregunta pregunta);
	public boolean guardarImagenDB(File file);

}
