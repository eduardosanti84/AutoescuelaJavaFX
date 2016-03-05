package dad.autoescuela.controllers;

import java.io.File;

import dad.autoescuela.Main;
import dad.autoescuela.model.Pregunta;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
public class MenuProfesorController {

@SuppressWarnings("unused")
private Main main;
	
	@FXML
	private ToggleButton profesorButton;
	@FXML
	private Button guardarUsuarioButton;
	@FXML
	private TextField nombreTextField;
	@FXML
	private TextField dniTextField;
	@FXML
	private TextField passwordTextField;
	
	@FXML
	private TableView<Usuario> tablaUsuarios;
	@FXML
	private TableColumn<Usuario, String> dniColumn;
	@FXML
	private TableColumn<Usuario, String> nombreColumn;
	@FXML
	private TableColumn<Usuario, Boolean> profesorColumn;
	@FXML
	private Button eliminarUsuarioButton;
	
	@FXML
	private TableView<Pregunta> tablaPreguntas;
	@FXML
	private TableColumn<Pregunta, Number> idPreguntaColumn;
	@FXML
	private TableColumn<Pregunta, String> enunciadoColumn;
	@FXML
	private Button eliminarPreguntaButton;
	
	@FXML
	private RadioButton rb1;
	@FXML
	private RadioButton rb2;
	@FXML
	private RadioButton rb3;
	
	@FXML
	private Button guardarPreguntaButton;
	@FXML
	private TextArea enunciadoTextArea;
	@FXML
	private TextArea pregunta1TextArea;
	@FXML
	private TextArea pregunta2TextArea;
	@FXML
	private TextArea pregunta3TextArea;
	@FXML
	private ImageView imagenPregunta;
	
	private String radioButtonSelected = "1";
	private final FileChooser fileChooser = new FileChooser();
	private File imagenFile;
	
	@FXML
	private void initialize(){
		
		/////////////////////////////////////////////////////////////////////////////////////TODO FORMULARIO USUARIO /////
		profesorButton.selectedProperty().addListener((obs, oldValue, newValue) -> {
			
			if(newValue){
				profesorButton.setText("SI");
			}
			else
				profesorButton.setText("NO");
		});
		
		guardarUsuarioButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				Usuario usuario = new Usuario();
				usuario.setNombre(nombreTextField.getText());
				usuario.setDni(dniTextField.getText());
				usuario.setPass(passwordTextField.getText());
				usuario.setProfesor(profesorButton.isSelected());

				if(ServiceLocator.getUsuarioServices().crearUsuario(usuario)){
				}
				else{
				}
			}
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////TODO VER USUARIOS /////
		dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		profesorColumn.setCellValueFactory(cellData -> cellData.getValue().profesorProperty());
//		tablaUsuarios.getSelectionModel().selectedItemProperty().addListener(
//	            (observable, oldValue, newValue) -> showUsuariosDetails(newValue));

		tablaUsuarios.setItems(ServiceLocator.getUsuarioServices().listarUsuarios());
		
		eliminarUsuarioButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Usuario usuario = tablaUsuarios.selectionModelProperty().get().getSelectedItem();
				
				if(ServiceLocator.getUsuarioServices().eliminarUsuario(usuario)){
				}
				else{
				}
			}
		});
		
		////////////////////////////////////////////////////////////////////////////////////TODO FORMULARIO PREGUNTA /////
		
		imagenPregunta.setImage(Images.cargarIcono(Images.INSERT_IMAGE));
		
		imagenPregunta.onMouseClickedProperty().set(new EventHandler<Event>() {
			public void handle(Event event) {
				
				imagenFile = null;

                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All Images", "*.*"));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG", "*.jpg"));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("BMP", "*.bmp"));
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
                
                imagenFile = fileChooser.showOpenDialog(null);
                
                if(imagenFile != null)
                	imagenPregunta.setImage(new Image(imagenFile.toURI().toString()));
			}
		});
		
		final ToggleGroup grupoRB = new ToggleGroup();
		rb1.setToggleGroup(grupoRB);
		rb1.setSelected(true);
		rb2.setToggleGroup(grupoRB);
		rb3.setToggleGroup(grupoRB);
		
		grupoRB.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {

	            if(rb1.isSelected())
	            	radioButtonSelected = "1";
	            if(rb2.isSelected())
	            	radioButtonSelected = "2";
	            if(rb3.isSelected())
	            	radioButtonSelected = "3";
		    }
		});

		guardarPreguntaButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				System.out.println(" el radio button indicado es : " + radioButtonSelected);
				
				Pregunta pregunta = new Pregunta();
				pregunta.setEnunciado(enunciadoTextArea.getText());
				pregunta.setPregunta1(pregunta1TextArea.getText());
				pregunta.setPregunta2(pregunta2TextArea.getText());
				pregunta.setPregunta3(pregunta3TextArea.getText());
				pregunta.setRespuesta(radioButtonSelected);
				
				if(ServiceLocator.getPreguntaServices().crearPregunta(pregunta)){
					
					ServiceLocator.getPreguntaServices().guardarImagenDB(imagenFile);
				}
				else{
				}
			}
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////TODO VER PREGUNTAS /////
		idPreguntaColumn.setCellValueFactory(cellData -> cellData.getValue().idPreguntaProperty());
		enunciadoColumn.setCellValueFactory(cellData -> cellData.getValue().enunciadoProperty());

		tablaPreguntas.setItems(ServiceLocator.getPreguntaServices().listarPreguntas());
		
		eliminarPreguntaButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Pregunta pregunta = tablaPreguntas.selectionModelProperty().get().getSelectedItem();
				
				if(ServiceLocator.getPreguntaServices().eliminarPregunta(pregunta)){
				}
				else{
				}
			}
		});
		
		tablaPreguntas.setRowFactory( tv -> {
		    TableRow<Pregunta> row = new TableRow<>();
			    row.setOnMouseClicked(event -> {
			        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
			        	
			        	Pregunta rowData = row.getItem();
	
			        	//TODO aqui tengo ya los datos de las preguntas
			        }
			    });
		    return row ;
		});
	}

	public void setMain(Main main) {
		this.main = main;
	}
}


///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////////////////
//boton.selectedProperty().addListener(new ChangeListener<Boolean>() {
//@Override
//public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
//	System.out.println(newValue);
//	
//	if(newValue){
//		
//	}
//	
//}
//});
