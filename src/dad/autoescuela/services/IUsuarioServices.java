package dad.autoescuela.services;

import dad.autoescuela.model.Usuario;
import javafx.collections.ObservableList;

public interface IUsuarioServices {

	public ObservableList<Usuario> listarUsuarios();
	public Boolean crearUsuario(Usuario usuario);
	public Boolean eliminarUsuario(Usuario usuario);
}
