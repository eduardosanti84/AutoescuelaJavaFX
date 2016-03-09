package dad.autoescuela.controllers;

import java.util.ArrayList;
import java.util.List;

import dad.autoescuela.Main;
import dad.autoescuela.model.Pregunta;
import dad.autoescuela.model.Respuesta;
import dad.autoescuela.model.Resultado;
import dad.autoescuela.model.Usuario;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;


public class MenuAlumnoController {

	private Main main;
	private Usuario usuarioActual;
	private List<Pregunta> preguntasTest;
	private List<Respuesta> respuestasTest;
	private int tamTotalTest;
	private int posicionActual;
	
	@FXML
	private Button aceptarButton;
	@FXML
	private Button continuarTestButton;
	@FXML
	private Button salirButton;
	@FXML
	private Label preguntaLabel;
	@FXML
	private Label numPreguntaLabel;
	@FXML
	private Label totalPreguntasLabel;
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
	@FXML
	private Label nombreUsuarioLabel;
	
	public MenuAlumnoController() {
		
		usuarioActual = ServiceLocator.getConexionServices().getUsuarioActual();
		preguntasTest = new ArrayList<Pregunta>();
		respuestasTest = new ArrayList<Respuesta>();
		tablaResultados = new TableView<>();
		posicionActual = 0;
		tamTotalTest = 10;
	}
	
	@FXML
	private void initialize() {
		/////////////////////////////////////////////////////////////////////////////////////////////////// CABECERA /////
		banner.setStyle("-fx-background-image: url('/dad/autoescuela/resources/images/bannerNY.png'); " +
						"-fx-background-size: stretch;"
		);
		nombreUsuarioLabel.setText("Bienvenid@: " + usuarioActual.getNombre());
		//////////////////////////////////////////////////////////////////////////////////// AGRUPACION RADIOBUTTONS /////
		final ToggleGroup grupoRB = new ToggleGroup();
		respuesta1RadioButton.setToggleGroup(grupoRB);
		respuesta2RadioButton.setToggleGroup(grupoRB);
		respuesta3RadioButton.setToggleGroup(grupoRB);
		//respuesta1RadioButton.setSelected(true);
		grupoRB.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
				continuarTestButton.requestFocus();
			}
		});
		
		//////////////////////////////////////////////////////////////////// CONFIGURACION DE LAS CELDAS DE LA TABLA /////
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
		
		ObservableList<Resultado> nulo = FXCollections.observableArrayList();
		main.getStagesAlumno().close();
		main.getPrimaryStage().show();	
		
	}
	
	@FXML
	public void onAceptarButtonAction() {
		rellenarTest();
		mostrarPregunta(preguntasTest.get(posicionActual));
		//posicionActual++;
		
		realizarTestTab.setDisable(false);
		tabPane.getSelectionModel().select(realizarTestTab);
		aceptarButton.setDisable(true);
	}
	
	@FXML
	public void onContinuarTestButtonAction() {

		String numRespuestaUsuario = "";
		
		if(respuesta1RadioButton.isSelected())
			numRespuestaUsuario = "1";
		else if(respuesta2RadioButton.isSelected())
			numRespuestaUsuario = "2";
		else if(respuesta3RadioButton.isSelected())
			numRespuestaUsuario = "3";
		else
			numRespuestaUsuario = "0";
			
		if(!numRespuestaUsuario.equals("0")){

			if(respuestasTest.size() == posicionActual && posicionActual < tamTotalTest){
				//guardamos en una lista la respuesta del usuario
				guardarRespuesta(preguntasTest.get(posicionActual), numRespuestaUsuario);
				
				if(respuestasTest.size() < tamTotalTest){
					continuarTestButton.setText("Continuar");
				}
				else{
					//si es la ultima pregunta solo queda terminar 
					continuarTestButton.setText("Terminar");
				}
				//mostramos resultado sobre la pregunta hecha
				habilitarRadioButton(false);
				mostrarResultado(respuestasTest.get(posicionActual));
			}
			else{
				posicionActual++;
				if(posicionActual < tamTotalTest){
					//mostramos una nueva pregunta
					mostrarPregunta(preguntasTest.get(posicionActual));
					habilitarRadioButton(true);
					continuarTestButton.setText("Comprobar");
				}
				else{
					//Terminamos el test
					corregirTestFinal();
					resetearTest();
					tabPane.getSelectionModel().select(resultadosTab);
				}
			}
		}
	}
	
	private void habilitarRadioButton(Boolean confirmacion) {
		if(confirmacion){
			respuesta1RadioButton.setDisable(false);
			respuesta2RadioButton.setDisable(false);
			respuesta3RadioButton.setDisable(false);
			respuesta1RadioButton.setTextFill(Color.BLACK);
			respuesta2RadioButton.setTextFill(Color.BLACK);
			respuesta3RadioButton.setTextFill(Color.BLACK);
			respuesta1RadioButton.setStyle("");
			respuesta2RadioButton.setStyle("");
			respuesta3RadioButton.setStyle("");
			respuesta1RadioButton.setSelected(false);
			respuesta2RadioButton.setSelected(false);
			respuesta3RadioButton.setSelected(false);
		}
		else{
			respuesta1RadioButton.setDisable(true);
			respuesta2RadioButton.setDisable(true);
			respuesta3RadioButton.setDisable(true);
		}
	}

	private void mostrarResultado(Respuesta respuesta) {
		//colorear la respuesta del usuario, por si acaso se colorea en rojo.
		switch (respuesta.getRespuestaUsuario()) {
		case "1":
			respuesta1RadioButton.setTextFill(Color.BLACK);
			respuesta1RadioButton.setStyle("-fx-background-color: #ff704d; -fx-opacity: 1;");
			break;
		case "2":
			respuesta2RadioButton.setTextFill(Color.BLACK);
			respuesta2RadioButton.setStyle("-fx-background-color: #ff704d; -fx-opacity: 1;");
			break;
		case "3":
			respuesta3RadioButton.setTextFill(Color.BLACK);
			respuesta3RadioButton.setStyle("-fx-background-color: #ff704d; -fx-opacity: 1;");
			break;
		default:
			break;
		}
		
		//colorear correcta, si el usuario acertó quedara en verde.
		switch (respuesta.getRespuestaCorrecta()) {
		case "1":
			respuesta1RadioButton.setTextFill(Color.BLACK);
			respuesta1RadioButton.setStyle("-fx-background-color: #ccff99; -fx-opacity: 1;");
			break;
		case "2":
			respuesta2RadioButton.setTextFill(Color.BLACK);
			respuesta2RadioButton.setStyle("-fx-background-color: #ccff99; -fx-opacity: 1;");
			break;
		case "3":
			respuesta3RadioButton.setTextFill(Color.BLACK);
			respuesta3RadioButton.setStyle("-fx-background-color: #ccff99; -fx-opacity: 1;");
			break;
		default:
			break;
		}
	}


	
	@FXML
	public void onSalirTestButtonAction() {
		
		resetearTest();
		tabPane.getSelectionModel().select(inicioTab);
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												METODOS
	 *********************************************************************************************************************
	 **/
	private void rellenarTest() {
		List<Pregunta> preguntasTotales = ServiceLocator.getPreguntaServices().listarPreguntas();
		int tamPreguntasTotales = preguntasTotales.size();
		
		while(preguntasTest.size() < tamTotalTest){
			
			int numAleatorio = (int) (Math.random() * tamPreguntasTotales);
			Pregunta pregunta = preguntasTotales.get(numAleatorio);
			
			if(!preguntasTest.contains(pregunta)){
				preguntasTest.add(pregunta);
			}
		}
	}
	
	private void resetearTest(){
		
		aceptarButton.setDisable(false);
		realizarTestTab.setDisable(true);
		posicionActual = 0;
		numPreguntaLabel.setText("" + posicionActual);
		totalPreguntasLabel.setText("" + tamTotalTest);
		habilitarRadioButton(true);
		preguntasTest.clear();
		respuestasTest.clear();
		continuarTestButton.setText("Comprobar");
	}
	
	private void mostrarPregunta(Pregunta pregunta){
		if(posicionActual != tamTotalTest){
			if(pregunta.getImagen() != null)
				imagenImageView.setImage(pregunta.getImagen());
			else
				imagenImageView.setImage(Images.NO_IMAGE);
			preguntaLabel.setText(pregunta.getEnunciado());
			respuesta1RadioButton.setText(pregunta.getRespuesta1());
			respuesta2RadioButton.setText(pregunta.getRespuesta2());
			respuesta3RadioButton.setText(pregunta.getRespuesta3());
			
			//TODO REVISAR EL CONTADOR EN INTERFAZ
			numPreguntaLabel.setText("" + (posicionActual + 1));
			totalPreguntasLabel.setText("" + tamTotalTest);
		}
	}
	
	private void guardarRespuesta(Pregunta pregunta, String respuestaUsuario){
		
		Respuesta respuesta = new Respuesta();
		respuesta.setIdPregunta(pregunta.getId());
		respuesta.setRespuestaCorrecta(pregunta.getRespuestaCorrecta());
		respuesta.setRespuestaUsuario(respuestaUsuario);

		respuestasTest.add(respuesta);
	}
	
	private void corregirTestFinal(){

		Resultado resultado = new Resultado();
		resultado.setAlumno_dni(usuarioActual.getDni());
		resultado.setAciertos(0);
		resultado.setFallos(0);
		resultado.setTotal(tamTotalTest);
		
		for(Respuesta respuesta : respuestasTest){

			if(respuesta.getRespuestaUsuario().equals(respuesta.getRespuestaCorrecta()))
				
				resultado.setAciertos(resultado.getAciertos()+1);
			else
				resultado.setFallos(resultado.getFallos()+1);
			System.out.println("id:" + respuesta.getIdPregunta() + " resp usuario:" + respuesta.getRespuestaUsuario() + " Respuesta correcta:" + respuesta.getRespuestaCorrecta());
		}
		ServiceLocator.getResultadoServices().crearResultado(resultado);
	}

	public void setMain(Main main) {
		this.main = main;
	}
}


//@FXML
//public void onTerminarTestButtonAction() {
	
//	comprobarRespuesta();
//	
//	Resultado resultado = new Resultado();
//	resultado.setAlumno_dni(usuarioActual.getDni());
//	resultado.setAciertos(aciertos);
//	resultado.setFallos(fallos);
//	resultado.setTotal(tamTotalTest);
//	ServiceLocator.getResultadoServices().crearResultado(resultado);
//	
//	tabPane.getSelectionModel().select(resultadosTab);
//	realizarTestTab.setDisable(true);
//	
//	fallos = 0;
//	aciertos = 0;
//	posicionActual = 0;
//	
//	siguienteButton.setDisable(false);
//	terminarTestButton.setDisable(true);
//	aceptarButton.setDisable(false);
//	preguntasTest.clear();
//}




//if(respuesta1RadioButton.isSelected())
//if(ServiceLocator.getPreguntaServices().comprobarRespuesta(pregunta, "1"))
//	aciertos++;
//else
//	fallos++;
//else
//if(respuesta2RadioButton.isSelected())
//	if(ServiceLocator.getPreguntaServices().comprobarRespuesta(pregunta, "2"))
//		aciertos++;
//	else
//		fallos++;
//else
//	if(respuesta3RadioButton.isSelected())
//		if(ServiceLocator.getPreguntaServices().comprobarRespuesta(pregunta, "3"))
//			aciertos++;
//		else
//			fallos++;
//	else
//		fallos++;





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

