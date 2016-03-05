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
			String consulta = "INSERT INTO preguntas(enunciado, pregunta1, pregunta2, pregunta3, respuesta) VALUES (?, ?, ?, ?, ?)";    
			preparedStatement = conexion.prepareStatement(consulta);
			
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
		
		System.out.println(" y aqui");
		
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

	//////////////////////////////////////////////////////////////////////////////////TODO METODOS DE DB PARA IMAGEN //////
	public boolean guardarImagenDB(File imagenFile){
		
		if(imagenFile == null){
			
			//imagenFile = new File("file:" + Images.darURL(Images.NO_IMAGE));  //TODO dar imagen en ruta relativa
		}
		
		int id_pregunta = tomarUltimaIdDB();
		
		conexion = ServiceLocator.getConexionServices().getConexion();
		
		String insert = "insert into Imagenes(nombre, imagen, id_pregunta) values (?, ?, ?)";
		FileInputStream fis = null;
		PreparedStatement ps = null;
		try {
			conexion.setAutoCommit(false);
			
			fis = new FileInputStream(imagenFile);
			ps = conexion.prepareStatement(insert);
			ps.setString(1, crearNombreImagen());
			ps.setBinaryStream(2,fis,(int)imagenFile.length());
			ps.setInt(3, id_pregunta);
			ps.executeUpdate();
			conexion.commit();
			
			return true;
			
		} catch (Exception e) {
			e.printStackTrace(); 
		}finally{
			
			try {
				ps.close();
				fis.close();
			} catch (Exception e) {
				e.printStackTrace(); 
			}
		}        
		return false;
	}
	
	private String crearNombreImagen(){
		
		String nombre;
		
		Date actual = new Date(System.currentTimeMillis());
	    DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
	    nombre = df.format(actual);

		return nombre;
	}
	
	private BufferedImage leerImagenDB() {
		//ArrayList<Imagen> lista = new ArrayList();
		BufferedImage img = null;
		try {
			
			String consulta = "SELECT imagen,nombre FROM Imagenes";    
			ResultSet rs = conexion.createStatement().executeQuery(consulta); 

			while (rs.next())
			{
				//Imagen imagen = new Imagen();
				Blob blob = rs.getBlob("imagen");
				String nombre = rs.getObject("nombre").toString();
				byte[] datos = blob.getBytes(1, (int)blob.length());
				
				try {
					img = ImageIO.read(new ByteArrayInputStream(datos));
					
				} catch (IOException e) {
					e.printStackTrace();
				}	
				//imagen.setImagen(img);
				//imagen.setNombre(nombre);
			}
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return img;
	}
}
