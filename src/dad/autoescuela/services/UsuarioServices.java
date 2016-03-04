package dad.autoescuela.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import dad.autoescuela.model.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UsuarioServices implements IUsuarioServices{
	
	private Connection conexion;
	private ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
	
	public UsuarioServices() {
		conexion = ServiceLocator.getConexionServices().getConexion();
		
		try{ 
			String consulta = "SELECT nombre, dni, profesor from usuarios";    
			ResultSet rs = conexion.createStatement().executeQuery(consulta); 

            while(rs.next()){
            	Usuario usuario = new Usuario();
            	
            	usuario.setNombre(rs.getString("nombre"));
            	usuario.setDni(rs.getString("dni"));
            	usuario.setProfesor(rs.getBoolean("profesor"));

            	usuarios.add(usuario);
            }
		}catch(Exception e){  
			e.printStackTrace();      
		}  
	}
	
	@Override
	public ObservableList<Usuario> listarUsuarios() {
		return usuarios;
	}

	@Override
	public Boolean crearUsuario(Usuario usuario) {

		if(!usuarios.contains(usuario)){
			for(int i = 0; i < usuarios.size(); i++){
				if(usuarios.get(i).getDni().equals(usuario.getDni())){
					break;
				}
			}
			
			int i;
			for (i = 0; i < usuarios.size() && !usuarios.get(i).getDni().equals(usuario.getDni()); i++);
			
			if (i == usuarios.size()) {
				usuarios.add(usuario);	
				
				addUsuario(usuario);
				
				return true;
			}
		}
		return false;
	}
	@Override
	public Boolean eliminarUsuario(Usuario usuario) {
		return null;
	}

	//////////////////////METODOS DB
	private void addUsuario(Usuario usuario) {
		
		PreparedStatement preparedStatement = null;
		
		try{ 
			String consulta = "INSERT INTO usuarios(nombre, dni, pass, profesor) VALUES (?, ?, ?, ?)";    
			preparedStatement = conexion.prepareStatement(consulta);
			
			preparedStatement.setString(1, usuario.getNombre());
			preparedStatement.setString(2, usuario.getDni());
			preparedStatement.setString(3, usuario.getPass());
			preparedStatement.setBoolean(4, usuario.isProfesor());
			
			preparedStatement.executeUpdate();
			
		}catch(Exception e){  
			e.printStackTrace();      
		} 
	}
}
