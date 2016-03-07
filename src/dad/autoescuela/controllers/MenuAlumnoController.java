package dad.autoescuela.controllers;

import java.util.ArrayList;
import java.util.List;

import dad.autoescuela.Main;
import dad.autoescuela.model.Pregunta;
import dad.autoescuela.model.Resultado;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;


public class MenuAlumnoController {

	private Main main;
	private List<Pregunta> preguntasTest;
	private List<Pregunta> preguntasTotales;
	private Pregunta pregunta;
	private int aciertos;
	private int fallos;
	private int tamTotalTest;
	private int posicionActual;

	@FXML
	private Button terminarTestButton;
	@FXML
	private Button siguienteButton;
	@FXML
	private Button aceptarButton;
	@FXML
	private Button salirButton;
	@FXML
	private Label preguntaLabel;
	@FXML
	private Label numPreguntaLabel;
	@FXML
	private RadioButton respuesta1RadioButton;
	@FXML
	private RadioButton respuesta2RadioButton;
	@FXML
	private RadioButton respuesta3RadioButton;
	@FXML
	private ImageView imagenImageView;
	@FXML
	private TableView<Resultado> tablaResultados;
	@FXML
	private TableColumn<Resultado, String> dniColumn;
	@FXML
	private TableColumn<Resultado, Number> aciertosColumn;
	@FXML
	private TableColumn<Resultado, Number> fallosColumn;
	@FXML
	private TableColumn<Resultado, Number> totalColumn;
	@FXML
	private TabPane tabPane;
	@FXML
	private Tab realizarTestTab;
	@FXML
	private Tab inicioTab;
	@FXML
	private Tab resultadosTab;
	@FXML
	private StackPane banner;
	@FXML
	private Button desconectarButton;
	
	public MenuAlumnoController() {
		
		preguntasTest = new ArrayList<Pregunta>();
		tamTotalTest = 10;
		
		// TODO MONTAR RESETEO
		aciertos = 0;
		fallos = 0;
		posicionActual = 0;
	}
	
	@FXML
	private void initialize() {
		/////////////////////////////////////////////////////////////////////////////////////////////////// CABECERA /////
		banner.setStyle("-fx-background-image: url('/dad/autoescuela/resources/images/bannerNY.png'); " +
						"-fx-background-size: stretch;"
		);
		//////////////////////////////////////////////////////////////////////////////////// AGRUPACION RADIOBUTTONS /////
		final ToggleGroup grupoRB = new ToggleGroup();
		respuesta1RadioButton.setToggleGroup(grupoRB);
		respuesta2RadioButton.setToggleGroup(grupoRB);
		respuesta3RadioButton.setToggleGroup(grupoRB);
		//respuesta1RadioButton.setSelected(true);
		//////////////////////////////////////////////////////////////////// CONFIGURACION DE LAS CELDAS DE LA TABLA /////
		dniColumn.setCellValueFactory(cellData -> cellData.getValue().alumno_dniProperty());
		aciertosColumn.setCellValueFactory(cellData -> cellData.getValue().aciertosProperty());
		fallosColumn.setCellValueFactory(cellData -> cellData.getValue().fallosProperty());
		totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());

		tablaResultados.setItems(ServiceLocator.getResultadoServices().listarResultados());
	}
	/*
	 *********************************************************************************************************************
	 *** 												LISTENER
	 *********************************************************************************************************************
	 **/
	@FXML
	public void onDesconectarButtonAction() {
		main.getStage().close();
		main.getPrimaryStage().show();	
	}
	
	@FXML
	public void onAceptarButtonAction() {
		rellenarTest();
		mostrarPregunta(preguntasTest.get(0));
		posicionActual++;

		realizarTestTab.setDisable(false);
		tabPane.getSelectionModel().select(realizarTestTab);
		aceptarButton.setDisable(true);
	}

	@FXML
	public void onSiguienteTestButtonAction() {
	
		comprobarRespuesta();
		
		if(posicionActual < tamTotalTest-1){

			mostrarPregunta(preguntasTest.get(posicionActual));
			posicionActual++;
		}
		else{
			siguienteButton.setDisable(true);
			terminarTestButton.setDisable(false);
		}
	}
	
	@FXML
	public void onAnteriorTestButtonAction() {
	
		//comprobarRespuesta();
		//TODO TAL VEZ HAGA FALTA UN OBJETO RESPUESTA
		if(posicionActual < tamTotalTest-1){

			mostrarPregunta(preguntasTest.get(posicionActual));
			posicionActual++;
		}
		else{
			siguienteButton.setDisable(true);
			terminarTestButton.setDisable(false);
		}
	}
	
	@FXML
	public void onTerminarTestButtonAction() {
		
		comprobarRespuesta();
		
		Resultado resultado = new Resultado();
		resultado.setAlumno_dni(ServiceLocator.getConexionServices().getUsuario().getDni());
		resultado.setAciertos(aciertos);
		resultado.setFallos(fallos);
		resultado.setTotal(tamTotalTest);
		ServiceLocator.getResultadoServices().crearResultado(resultado);
		
		tabPane.getSelectionModel().select(resultadosTab);
		realizarTestTab.setDisable(true);
		
		fallos = 0;
		aciertos = 0;
		posicionActual = 0;
		
		siguienteButton.setDisable(false);
		terminarTestButton.setDisable(true);
		aceptarButton.setDisable(false);
		preguntasTest.clear();
	}
	
	@FXML
	public void onSalirTestButtonAction() {
		
		tabPane.getSelectionModel().select(inicioTab);
		realizarTestTab.setDisable(true);
		aceptarButton.setDisable(false);
		aciertos = 0;
		fallos = 0;
		posicionActual = 0;
		numPreguntaLabel.setText(posicionActual + " - " + tamTotalTest);
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												METODOS
	 *********************************************************************************************************************
	 **/
	private void rellenarTest() {
		preguntasTotales = ServiceLocator.getPreguntaServices().listarPreguntas();
		int tamPreguntasTotales = preguntasTotales.size();
		
		while(preguntasTest.size() < tamTotalTest){
			
			int numAleatorio = (int) (Math.random() * tamPreguntasTotales);
			Pregunta pregunta = preguntasTotales.get(numAleatorio);
			
			if(!preguntasTest.contains(pregunta)){
				preguntasTest.add(pregunta);
			}
		}
	}
	
	private void mostrarPregunta(Pregunta pregunta){
		this.pregunta = pregunta;
		if(posicionActual != tamTotalTest){
			if(pregunta.getImagen() != null)
				imagenImageView.setImage(pregunta.getImagen());
			else
				imagenImageView.setImage(Images.NO_IMAGE);
			preguntaLabel.setText(pregunta.getEnunciado());
			respuesta1RadioButton.setText(pregunta.getPregunta1());
			respuesta2RadioButton.setText(pregunta.getPregunta2());
			respuesta3RadioButton.setText(pregunta.getPregunta3());
			
			//TODO REVISAR EL CONTADOR EN INTERFAZ
			//numPreguntaLabel.setText("Total: " + (++total) + " - " + tamTotalTest);
		}
	}
	
	private void comprobarRespuesta(){
		
		//TODO REVISAR
		if(respuesta1RadioButton.isSelected())
			if(ServiceLocator.getPreguntaServices().comprobarRespuesta(pregunta, "1"))
				aciertos++;
			else
				fallos++;
		else
			if(respuesta2RadioButton.isSelected())
				if(ServiceLocator.getPreguntaServices().comprobarRespuesta(pregunta, "2"))
					aciertos++;
				else
					fallos++;
			else
				if(respuesta3RadioButton.isSelected())
					if(ServiceLocator.getPreguntaServices().comprobarRespuesta(pregunta, "3"))
						aciertos++;
					else
						fallos++;
				else
					fallos++;
	}

	public void setMain(Main main) {
		this.main = main;
	}
}
















//numAleatorio = (int) (Math.random() * totalPreguntas);
//while(preguntasTest.size() < totalPreguntas & preguntasTest.contains(numAleatorio)){
//	numAleatorio = (int) (Math.random() * totalPreguntas);
//}
//
//if(total < totalPreguntas){
//	preguntasTest.add(numAleatorio);
//	posicion = 0;
//	while(preguntasTest.get(posicion) != numAleatorio)
//		posicion++;
//	insertarPregunta(ServiceLocator.getPreguntaServices().listarPreguntas().get(preguntasTest.get(posicion)));
//}
//if(total == totalPreguntas){
//	siguienteButton.setDisable(true);
//	terminarTestButton.setDisable(false);
//}

//numAleatorio = (int) (Math.random() * totalPreguntas);
//while(preguntasTest.contains(numAleatorio))
//	numAleatorio = (int) (Math.random() * totalPreguntas);
//preguntasTest.add(numAleatorio);
//posicion = 0;
//while(preguntasTest.get(posicion) != numAleatorio)
//	posicion++;
//insertarPregunta(ServiceLocator.getPreguntaServices().listarPreguntas().get(preguntasTest.get(posicion)));

