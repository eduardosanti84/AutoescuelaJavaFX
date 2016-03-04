package dad.autoescuela.services;

import java.sql.Connection;

public interface IConexionServices {
	
	public boolean conectar();
	public Connection getConexion();
	public void desconectar();
}
