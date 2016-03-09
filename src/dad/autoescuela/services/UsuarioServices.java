package dad.autoescuela.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dad.autoescuela.model.Usuario;

public class UsuarioServices implements IUsuarioServices{
	
	///////////////////////////////////////////////////////////////////////////////////TODO METODOS PARA CONTROLADOR //////
	@Override
	public List<Usuario> listarUsuarios() {
		Connection conexion = ServiceLocator.getConexionServices().getConexion();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try{ 
			String consulta = "SELECT * from usuarios";    
			ResultSet rs = conexion.createStatement().executeQuery(consulta); 
			
            while(rs.next()){
            	
            	Usuario usuario = new Usuario();
            	usuario.setNombre(rs.getString("nombre"));
            	usuario.setDni(rs.getString("dni"));
            	usuario.setProfesor(rs.getBoolean("profesor"));
            	usuario.setPass(rs.getString("pass"));

            	usuarios.add(usuario);
            }
		}catch(Exception e){  
			e.printStackTrace();      
		}
		return usuarios;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////TODO METODOS DE DB //////
	public Boolean crearUsuario(Usuario usuario) {
		Connection conexion = ServiceLocator.getConexionServices().getConexion();

		PreparedStatement preparedStatement = null;
		
		try{ 
			String consulta = "INSERT INTO usuarios(nombre, dni, pass, profesor) VALUES (?, ?, ?, ?)";    
			preparedStatement = conexion.prepareStatement(consulta);
			
			preparedStatement.setString(1, usuario.getNombre());
			preparedStatement.setString(2, usuario.getDni());
			preparedStatement.setString(3, usuario.getPass());
			preparedStatement.setBoolean(4, usuario.isProfesor());
			
			preparedStatement.executeUpdate();
			return true;
		}catch(Exception e){  
			e.printStackTrace();  
			return false;
		} 
	}
	
	public Boolean eliminarUsuario(Usuario usuario) {
		Connection conexion = ServiceLocator.getConexionServices().getConexion();

		PreparedStatement preparedStatement = null;
		
		try{ 
			String consulta = "DELETE FROM usuarios WHERE dni LIKE ?";    
			preparedStatement = conexion.prepareStatement(consulta);
			
			preparedStatement.setString(1, usuario.getDni());
			preparedStatement.executeUpdate();
			return true;
		}catch(SQLException e) {  
			e.printStackTrace();
			return false;
		} 
	}
	
	public Boolean login(String usuario, String pass) {
		return true;
	}
	
}
