package dad.autoescuela.services;

import dad.autoescuela.model.Pregunta;
import javafx.collections.ObservableList;

public interface IPreguntaServices {

	public ObservableList<Pregunta> listarPreguntas();
	public Boolean crearPregunta(Pregunta pregunta);
	public Boolean eliminarPregunta(Pregunta pregunta);
	public boolean comprobarRespuesta(Pregunta pregunta, String respuesta);
}
