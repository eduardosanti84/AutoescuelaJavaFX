package dad.autoescuela.services;

import java.util.List;

import dad.autoescuela.model.Usuario;

public interface IUsuarioServices {

	public List<Usuario> listarUsuarios();
	public Boolean crearUsuario(Usuario usuario);
	public Boolean eliminarUsuario(Usuario usuario);
	public Boolean login(String usuario, String pass);
	
}
