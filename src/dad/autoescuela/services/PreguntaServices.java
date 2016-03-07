package dad.autoescuela.services;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import dad.autoescuela.model.Pregunta;

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

            	Blob blob = rs.getBlob("imagen");
            	if (blob != null) {
            		pregunta.setImagen(new Image(blob.getBinaryStream()));
            	}
            	
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
//			for(int i = 0; i < preguntas.size(); i++){
//				if(preguntas.get(i).getId() == pregunta.getId()){
//					break;
//				}
//			}
//			
//			int i;
//			for (i = 0; i < preguntas.size() && preguntas.get(i).getId() != pregunta.getId(); i++);
//			
//			if (i == preguntas.size()) {
				
					
				crearPreguntaDB(pregunta);
				//al insertar ya tenemos la id, la necesitamos para rellenar en la lista observable y luego ne el tableview
				pregunta.setId(tomarUltimaIdDB());
				preguntas.add(pregunta);
				
				return true;
//			}
		}
		else
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
	
	@Override
	public boolean comprobarRespuesta(Pregunta pregunta, String respuesta) {
		if (pregunta.getRespuesta().equals(respuesta)) {
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
				BufferedImage bImage = SwingFXUtils.fromFXImage(pregunta.getImagen(), null);
				ByteArrayOutputStream bContent = new ByteArrayOutputStream();
				ImageIO.write(bImage, "png", bContent);
				byte [] contenido = bContent.toByteArray(); 
				preparedStatement.setBytes(6, contenido);
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
	
	private int tomarUltimaIdDB() {
		
		int id = -1;
		try{ 
			String consulta = "SELECT LAST_INSERT_ID() as id";    
			ResultSet rs = conexion.createStatement().executeQuery(consulta); 
			
            while(rs.next()){
            	id = rs.getInt("id");
            }
		}catch(Exception e){  
			e.printStackTrace();      
		} 
		
		return id;
	}
}
