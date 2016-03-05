package dad.autoescuela.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import dad.autoescuela.model.Resultado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ResultadoServices implements IResultadoServices {
	
	private ObservableList<Resultado> resultados = FXCollections.observableArrayList();
	private Connection conexion;
	
	public ResultadoServices(){
		conexion = ServiceLocator.getConexionServices().getConexion();
		
		try{ 
			//Este muestra todos los resultados de todos los alumnos. Lo pongo para probar que se muestra bien y
			//porque aun no tienes la clase ConexionService modificada. Mañana si soluciono el error te lo paso todo
			
			String consulta = "SELECT alumno_dni, aciertos, fallos, total FROM resultados";
			PreparedStatement ps = conexion.prepareStatement(consulta);
			
//			Esto muestra solamente los resultados del alumno que se ha logeado. Para ello tenemos que añadir
//			un atributo Usuario en el ConexionService.
			
			
//			String consulta = "SELECT alumno_dni, aciertos, fallos, total FROM resultados WHERE alumno_dni = ?";
//			PreparedStatement ps = conexion.prepareStatement(consulta);
//			
//			ps.setString(1, ServiceLocator.getConexionServices().getUsuario().getDni());
			
			ResultSet rs = ps.executeQuery();

            while(rs.next()){
            	Resultado resultado = new Resultado();
            	
	            resultado.setAlumno_dni(rs.getString("alumno_dni"));
	            resultado.setAciertos(rs.getInt("aciertos"));
	            resultado.setFallos(rs.getInt("fallos"));
	            resultado.setTotal(rs.getInt("total"));
            	
	            resultados.add(resultado);
            }
		}catch(Exception e){  
			e.printStackTrace();      
		}
		
	}
	
	@Override
	public void crearResultado(Resultado resultado) {
		resultados.add(resultado);
		
		String consulta = "INSERT INTO resultados (alumno_dni, aciertos, fallos, total) VALUES (?, ?, ?, ?)";
		try {
			PreparedStatement ps = conexion.prepareStatement(consulta);
			ps.setString(1, resultado.getAlumno_dni());
			ps.setInt(2, resultado.getAciertos());
			ps.setInt(3, resultado.getFallos());
			ps.setInt(4, resultado.getTotal());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public ObservableList<Resultado> listarResultados() {
		return resultados;
	}
}
