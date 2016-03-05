package dad.autoescuela.services;

import dad.autoescuela.model.Resultado;
import javafx.collections.ObservableList;

public interface IResultadoServices {
	
	public void crearResultado(Resultado resultado);
	public ObservableList<Resultado> listarResultados();
}
