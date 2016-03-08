package dad.autoescuela.services;

import java.sql.Connection;

import dad.autoescuela.model.Usuario;

public interface IConexionServices {
	
	public boolean conectar();
	public Connection getConexion();
	public void desconectar();
	public Usuario getUsuarioActual();
	public void setUsuarioActual(Usuario usuario);
}
