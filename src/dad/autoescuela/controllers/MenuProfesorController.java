package dad.autoescuela.controllers;

import java.io.File;

import dad.autoescuela.Main;
import dad.autoescuela.model.Pregunta;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import dad.autoescuela.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableCell;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Callback;
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
	
	@FXML
	private StackPane banner;
	@FXML
	private Button desconectarButton;
	
	private String radioButtonSelected = "1";
	private File imagenFile = null;
	
	@FXML
	private void initialize(){

		///////////////////////////////////////////////////////////////////////////////////////////////TODO CABECERA /////
		banner.setStyle(
	            "-fx-background-image: url(" +
	                    "'/dad/autoescuela/resources/images/bannerNY.png'" +
	                "); " +
	                "-fx-background-size: stretch;"
	            );
		
		desconectarButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				main.getStage().close();
				main.getPrimaryStage().show();
				
			}
		});
		
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
				
				if(!nombreTextField.getText().isEmpty() && !dniTextField.getText().isEmpty() && !passwordTextField.getText().isEmpty()){

					Usuario usuario = new Usuario();
					usuario.setNombre(nombreTextField.getText());
					usuario.setDni(dniTextField.getText());
					usuario.setPass(passwordTextField.getText());
					usuario.setProfesor(profesorButton.isSelected());
					
					if(ServiceLocator.getUsuarioServices().crearUsuario(usuario)){
						Utils.mensaje(AlertType.INFORMATION, "Correcto", "Confirmacion de Inserción", "Se ha registrado el nuevo usuario!");
						limpiarFormularioAlumno();
					}
					else Utils.mensaje(AlertType.ERROR, "Error", "Error al crear", "Ha ocurrido un error al crear el usuario, comprueba los datos.!");
				}
				else  Utils.mensaje(AlertType.ERROR, "Error", "Comprobación de los datos", "Faltan datos!");

			}
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////TODO VER USUARIOS /////
		dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		profesorColumn.setCellValueFactory(cellData -> cellData.getValue().profesorProperty());

	    // cambiamos la propiedad de la columna e insertamos imagen según lo que reciba.             
		profesorColumn.setCellFactory(new Callback<TableColumn<Usuario, Boolean>,TableCell<Usuario, Boolean>>(){        
		    @Override
		    public TableCell<Usuario, Boolean> call(TableColumn<Usuario, Boolean> param) {                
		        TableCell<Usuario, Boolean> cell = new TableCell<Usuario, Boolean>(){
		            @Override
		            public void updateItem(Boolean item, boolean empty) {                        
		                if(item!=null){ 
		                	
		                    HBox box= new HBox();
		                    box.setAlignment(Pos.CENTER);

		                    VBox vbox = new VBox();
		                    vbox.setAlignment(Pos.CENTER);
		                    
		                    ImageView imageview = new ImageView();
		                    imageview.setFitHeight(20);
		                    imageview.setFitWidth(20);
		                    
		                    if(item)
		                    	imageview.setImage(Images.CORRECT_IMAGE); 
		                    else
		                    	imageview.setImage(Images.INCORRECT_IMAGE);

		                    box.getChildren().addAll(imageview, vbox); 
		                    setGraphic(box);
		                }
		            }
		        };              
		        return cell;
		    }
		});

		tablaUsuarios.setItems(ServiceLocator.getUsuarioServices().listarUsuarios());
		
		eliminarUsuarioButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				if(Utils.confirmacion("Confirmacion", 
						"¿Realmente quiere eliminar este usuario?", 
						"Si elimina este usuario no podrá recuperarlo y se eliminaran todos sus datos, incluido los resultados de sus test."))
				{
					Usuario usuario = tablaUsuarios.selectionModelProperty().get().getSelectedItem();
					
					if(!ServiceLocator.getUsuarioServices().eliminarUsuario(usuario))
						//Utils.mensaje(AlertType.INFORMATION, "Correcto", "Se ha eliminado al usuario", "");
					//else  
						Utils.mensaje(AlertType.ERROR, "Error", "Error al eliminar", "Ha ocurrido un error al eliminar el usuario, contacta con el administrador!");
				}
			}
		});
		
		////////////////////////////////////////////////////////////////////////////////////TODO FORMULARIO PREGUNTA /////
		
		imagenPregunta.setImage(Images.INSERT_IMAGE);
		
		imagenPregunta.onMouseClickedProperty().set(new EventHandler<Event>() {
			public void handle(Event event) {
				
				imagenFile = null;
				FileChooser fileChooser = new FileChooser();
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
				
				if(!enunciadoTextArea.getText().isEmpty() && !pregunta1TextArea.getText().isEmpty() && 
					!pregunta2TextArea.getText().isEmpty() && !pregunta3TextArea.getText().isEmpty()){

					Pregunta pregunta = new Pregunta();
					pregunta.setEnunciado(enunciadoTextArea.getText());
					pregunta.setPregunta1(pregunta1TextArea.getText());
					pregunta.setPregunta2(pregunta2TextArea.getText());
					pregunta.setPregunta3(pregunta3TextArea.getText());
					pregunta.setRespuesta(radioButtonSelected);
					pregunta.setImagen(new Image(imagenFile.toURI().toString()));
					
					if(ServiceLocator.getPreguntaServices().crearPregunta(pregunta)){
						Utils.mensaje(AlertType.INFORMATION, "Correcto", "Se ha creado la pregunta", "La pregunta se ha creado correctamente!");
						limpiarFormularioPregunta();
					}
					else  Utils.mensaje(AlertType.ERROR, "Error", "Error al crear", "Ha ocurrido un error al crear la pregunta, comprueba los datos!");
				}
				else  Utils.mensaje(AlertType.ERROR, "Error", "Comprobación de los datos", "Faltan datos!");
			}
		});
		
		///////////////////////////////////////////////////////////////////////////////////////////TODO VER PREGUNTAS /////
		idPreguntaColumn.setCellValueFactory(cellData -> cellData.getValue().idPreguntaProperty());
		enunciadoColumn.setCellValueFactory(cellData -> cellData.getValue().enunciadoProperty());

		tablaPreguntas.setItems(ServiceLocator.getPreguntaServices().listarPreguntas());
		
		eliminarPreguntaButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				
				if(Utils.confirmacion("Confirmacion", 
						"¿Realmente quiere eliminar esta pregunta?", 
						"Si elimina esta pregunta no podrá recuperarla pero se mantendrán intacto los resultados de los test en la que se incluyó esta pregunta."))
				{
					Pregunta pregunta = tablaPreguntas.selectionModelProperty().get().getSelectedItem();
					
					if(!ServiceLocator.getPreguntaServices().eliminarPregunta(pregunta))
						//Utils.mensaje(AlertType.INFORMATION, "Correcto", "Se ha eliminado la pregunta", "");
					//else  
						Utils.mensaje(AlertType.ERROR, "Error", "Error al eliminar", "Ha ocurrido un error al eliminar la pregunta, contacta con el administrador!"); 
				}
			}
		});
		
		tablaPreguntas.setRowFactory( tv -> {
	    TableRow<Pregunta> row = new TableRow<>();
		    row.setOnMouseClicked(event -> {
		        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
		        	
		        	Pregunta rowData = row.getItem();
		        	//TODO 
		        }
		    });
		    return row ;
		});
	}	
//		tablaPreguntas.getSelectionModel().selectedItemProperty().addListener(
//				(observable, oldValue, newValue) -> showPreguntasDetails(newValue));
//	}
//	private void showPreguntasDetails(Pregunta newValue) {
//		System.out.println(newValue.getPregunta1());
//	}

	private void limpiarFormularioAlumno(){
		nombreTextField.setText("");
		dniTextField.setText("");
		passwordTextField.setText("");
	}
	private void limpiarFormularioPregunta(){
		enunciadoTextArea.setText("");
		pregunta1TextArea.setText("");
		pregunta2TextArea.setText("");
		pregunta3TextArea.setText("");
		imagenPregunta.setImage(Images.INSERT_IMAGE);
		imagenFile = null;
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
