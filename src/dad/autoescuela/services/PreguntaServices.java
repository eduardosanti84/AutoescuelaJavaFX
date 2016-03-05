package dad.autoescuela.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import dad.autoescuela.model.Pregunta;
import dad.autoescuela.resources.images.Images;

public class PreguntaServices implements IPreguntaServices{

	private Connection conexion;
	private ObservableList<Pregunta> preguntas = FXCollections.observableArrayList();
	
	////////////////////////////////////////////////////////////////////////////////////////////////TODO CONSTRUCTOR //////
	public PreguntaServices(){
		conexion = ServiceLocator.getConexionServices().getConexion();
		
		try{ 
			String consulta = "SELECT * from preguntas";    
			ResultSet rs = conexion.createStatement().executeQuery(consulta); 

            while(rs.next()){
            	Pregunta pregunta = new Pregunta();
            	
            	pregunta.setId(rs.getInt("id"));
            	pregunta.setEnunciado(rs.getString("enunciado"));
            	pregunta.setPregunta1(rs.getString("pregunta1"));
            	pregunta.setPregunta2(rs.getString("pregunta2"));
            	pregunta.setPregunta3(rs.getString("pregunta3"));
            	pregunta.setRespuesta(rs.getString("respuesta"));

            	preguntas.add(pregunta);
            }
		}catch(Exception e){  
			e.printStackTrace();      
		}  
	}
	
	///////////////////////////////////////////////////////////////////////////////////TODO METODOS PARA CONTROLADOR //////
	@Override
	public ObservableList<Pregunta> listarPreguntas() {
		return preguntas;
	}
	
	public Boolean crearPregunta(Pregunta pregunta) {

		if(!preguntas.contains(pregunta)){
			for(int i = 0; i < preguntas.size(); i++){
				if(preguntas.get(i).getId() == pregunta.getId()){
					break;
				}
			}
			
			int i;
			for (i = 0; i < preguntas.size() && preguntas.get(i).getId() != pregunta.getId(); i++);
			
			if (i == preguntas.size()) {
				
				preguntas.add(pregunta);	
				crearPreguntaDB(pregunta);
				
				return true;
			}
		}
		return false;
	}
	
	@Override
	public Boolean eliminarPregunta(Pregunta pregunta) {
		if(preguntas.contains(pregunta)){
			
			preguntas.remove(pregunta);
			eliminarPreguntaDB(pregunta);
			return true;
		}
		return false;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////TODO METODOS DE DB //////
	private void crearPreguntaDB(Pregunta pregunta) {
	
		PreparedStatement preparedStatement = null;
		
		try{ 
			String consulta = "INSERT INTO preguntas(enunciado, pregunta1, pregunta2, pregunta3, respuesta, imagen) VALUES (?, ?, ?, ?, ?, ?)";    
			preparedStatement = conexion.prepareStatement(consulta);
			
			if(pregunta.getImagen() != null){
				FileInputStream fis = new FileInputStream(pregunta.getImagen());
				preparedStatement.setBinaryStream(6, fis,(int)pregunta.getImagen().length());
			}
			else{
				preparedStatement.setBinaryStream(6, null);
			}
			
			preparedStatement.setString(1, pregunta.getEnunciado());
			preparedStatement.setString(2, pregunta.getPregunta1());
			preparedStatement.setString(3, pregunta.getPregunta2());
			preparedStatement.setString(4, pregunta.getPregunta3());
			preparedStatement.setString(5, pregunta.getRespuesta());
			
			
			preparedStatement.executeUpdate();
			
		}catch(Exception e){  
			e.printStackTrace();      
		}
	}

	private void eliminarPreguntaDB(Pregunta pregunta) {
		
		PreparedStatement preparedStatement = null;
		
		try{ 
			String consulta = "DELETE FROM preguntas WHERE id = ?";    
			preparedStatement = conexion.prepareStatement(consulta);
			
			preparedStatement.setInt(1, pregunta.getId());
			preparedStatement.executeUpdate();
			
		}catch(Exception e){  
			e.printStackTrace();      
		} 
	}
	
//	private int tomarUltimaIdDB() {
//		
//		System.out.println(" y aqui");
//		
//		int id = -1;
//		try{ 
//			String consulta = "SELECT LAST_INSERT_ID() as id";    
//			ResultSet rs = conexion.createStatement().executeQuery(consulta); 
//			
//            while(rs.next()){
//            	id = rs.getInt("id");
//            }
//		}catch(Exception e){  
//			e.printStackTrace();      
//		} 
//		
//		return id;
//	}

	//////////////////////////////////////////////////////////////////////////////////TODO METODOS DE DB PARA IMAGEN //////
//	
//	private BufferedImage leerImagenDB() {
//		BufferedImage img = null;
//		try {
//			
//			String consulta = "SELECT imagen FROM preguntas";    
//			ResultSet rs = conexion.createStatement().executeQuery(consulta); 
//
//			while (rs.next())
//			{
//				//Imagen imagen = new Imagen();
//				Blob blob = rs.getBlob("imagen");
//				byte[] datos = blob.getBytes(1, (int)blob.length());
//				
//				try {
//					img = ImageIO.read(new ByteArrayInputStream(datos));
//					
//				} catch (IOException e) {
//					e.printStackTrace();
//				}	
//				//imagen.setImagen(img);
//				//imagen.setNombre(nombre);
//			}
//			rs.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	
//		return img;
//	}
}
