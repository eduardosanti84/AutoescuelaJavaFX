package dad.autoescuela.controllers;

import dad.autoescuela.Main;
import dad.autoescuela.model.Pregunta;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import dad.autoescuela.utils.Utils;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

public class MenuProfesorController {	
	
	private Main main;
	private Usuario usuarioActual;
	
	@FXML
	private TableView<Usuario> tablaUsuarios;
	@FXML
	private TableColumn<Usuario, String> dniColumn;
	@FXML
	private TableColumn<Usuario, String> nombreColumn;
	@FXML
	private TableColumn<Usuario, Boolean> profesorColumn;
	@FXML
	private Button abrirCrearUsuarioButton;
	@FXML
	private Button eliminarUsuarioButton;
	
	@FXML
	private TableView<Pregunta> tablaPreguntas;
	@FXML
	private TableColumn<Pregunta, Number> idPreguntaColumn;
	@FXML
	private TableColumn<Pregunta, String> enunciadoColumn;
	@FXML
	private Button abrirCrearPreguntaButton;
	@FXML
	private Button eliminarPreguntaButton;

	@FXML
	private Label nombreUsuarioLabel;
	@FXML
	private StackPane banner;
	@FXML
	private Button desconectarButton;

	public MenuProfesorController() {
		usuarioActual = ServiceLocator.getConexionServices().getUsuarioActual();
	}
	
	@FXML
	private void initialize(){
		///////////////////////////////////////////////////////////////////////////////////////////////TODO CABECERA /////
		banner.setStyle(
	            "-fx-background-image: url(" +
	                    "'/dad/autoescuela/resources/images/bannerNY.png'" +
	                "); " +
	                "-fx-background-size: stretch;"
	            );
		nombreUsuarioLabel.setText("Bienvenid@: " + usuarioActual.getNombre());
		////////////////////////////////////////////////////////////////////////////////////AGRUPACION RADIOBUTTONS /////
		
		////////////////////////////////////////////////////////////////////CONFIGURACION DE LAS CELDAS DE LA TABLA /////
		dniColumn.setCellValueFactory(cellData -> cellData.getValue().dniProperty());
		nombreColumn.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
		profesorColumn.setCellValueFactory(cellData -> cellData.getValue().profesorProperty());
		tablaUsuarios.setItems(ServiceLocator.getUsuarioServices().listarUsuarios());
		
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
		///////////////////////////////////////////////////////////////////////////////////////////////TODO PREGUNTAS /////
		idPreguntaColumn.setCellValueFactory(cellData -> cellData.getValue().idPreguntaProperty());
		enunciadoColumn.setCellValueFactory(cellData -> cellData.getValue().enunciadoProperty());
		tablaPreguntas.setItems(ServiceLocator.getPreguntaServices().listarPreguntas());
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												LISTENER
	 *********************************************************************************************************************
	 **/
	/////////////////////////////////////////////////////////////////////////////////////////////////TODO BANNER /////
	@FXML
	public void onDesconectarButtonAction() {
		main.getStagesProfesor().close();
		main.getPrimaryStage().show();	
	}
	
	///////////////////////////////////////////////////////////////////////////////////////////////TODO USUARIOS /////
	@FXML
	public void onCrearUsuarioButtonAction(){
		main.showMenuCrearUsuario();
	}

	@FXML
	public void onEliminarUsuarioButtonAction(){	

		if(Utils.confirmacion("Confirmacion", 
				"¿Realmente quiere eliminar este usuario?", 
				"Si elimina este usuario no podrá recuperarlo y se eliminaran todos sus datos, incluido los resultados de sus test."))
		{
			Usuario usuario = tablaUsuarios.selectionModelProperty().get().getSelectedItem();
			
			if(!ServiceLocator.getUsuarioServices().eliminarUsuario(usuario)) 
				Utils.mensaje(AlertType.ERROR, "Error", "Error al eliminar", "Ha ocurrido un error al eliminar el usuario, contacta con el administrador!");
		}
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////////TODO PREGUNTAS /////
	@FXML
	public void onCrearPreguntaButtonAction(){
		main.showMenuCrearPregunta();
	}
	
	@FXML
	public void onEliminarPreguntaButtonAction(){
		if(Utils.confirmacion("Confirmacion", 
				"¿Realmente quiere eliminar esta pregunta?", 
				"Si elimina esta pregunta no podrá recuperarla pero se mantendrán intacto los resultados de los test en la que se incluyó esta pregunta."))
		{
			Pregunta pregunta = tablaPreguntas.selectionModelProperty().get().getSelectedItem();
			
			if(!ServiceLocator.getPreguntaServices().eliminarPregunta(pregunta))
				Utils.mensaje(AlertType.ERROR, "Error", "Error al eliminar", "Ha ocurrido un error al eliminar la pregunta, contacta con el administrador!"); 
		}
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


//tablaPreguntas.setRowFactory( tv -> {
//TableRow<Pregunta> row = new TableRow<>();
//    row.setOnMouseClicked(event -> {
//        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//        	
//        	Pregunta rowData = row.getItem();
//        	//
//        }
//    });
//    return row ;
//});
//}

//tablaPreguntas.getSelectionModel().selectedItemProperty().addListener(
//(observable, oldValue, newValue) -> showPreguntasDetails(newValue));
//}
//private void showPreguntasDetails(Pregunta newValue) {
//System.out.println(newValue.getPregunta1());
//}
