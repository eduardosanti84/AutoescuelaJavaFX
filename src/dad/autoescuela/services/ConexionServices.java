package dad.autoescuela.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import dad.autoescuela.model.Usuario;

public class ConexionServices implements IConexionServices {

	private static Connection conexion; 
	private static final String URL = "jdbc:mysql://localhost:3306/autoescuelajavafx";  
	private static final String USER = "root";
	private static final String PASS = "";
	
	private static Usuario usuarioActual;
	
	public ConexionServices() {
		conectar();
	}
	
	@Override
	public boolean conectar() {
		try{ 
			Class.forName("com.mysql.jdbc.Driver").newInstance();  
			conexion = DriverManager.getConnection(URL, USER, PASS);  
			return true;
			
		}catch(ClassNotFoundException | IllegalAccessException | InstantiationException | SQLException e){  
			System.err.println("Error: "+e.getMessage()); 
			return false;
		}
	}

	@Override
	public Connection getConexion() {
		try {
			if (conexion == null || conexion.isClosed()) {
				conectar();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conexion; 
	}
	
	@Override
	public void desconectar() {
		try {
			conexion.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	@Override
	public void setUsuarioActual(Usuario usuarioActual) {
		this.usuarioActual = usuarioActual;
	}
}
