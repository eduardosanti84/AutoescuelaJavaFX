package dad.autoescuela.controllers;

import java.util.ArrayList;
import java.util.List;

import dad.autoescuela.MainAlumno;
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

	@SuppressWarnings("unused")
	private MainAlumno main;

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
	private StackPane banner;
	@FXML
	private Button desconectarButton;
	
	private List<Integer> preguntasTest;
	
	private Pregunta pregunta;
	private int aciertos;
	private int fallos;
	private int total;
	private int numAleatorio;
	private int posicion;
	
	public MenuAlumnoController() {
		preguntasTest = new ArrayList<Integer>();
		
		aciertos = 0;
		fallos = 0;
		total = 0;
	}
	
	@FXML
	private void initialize() {
		///////////////////////////////////////////////////////////////////////////////////// TODO
		///////////////////////////////////////////////////////////////////////////////////// FORMULARIO
		///////////////////////////////////////////////////////////////////////////////////// USUARIO
		///////////////////////////////////////////////////////////////////////////////////// /////
		///////////////////////////////////////////////////////////////////////////////////////////////TODO CABECERA /////
		banner.setStyle(
			"-fx-background-image: url(" +
			"'/dad/autoescuela/resources/images/bannerNY.png'" +
			"); " +
			"-fx-background-size: stretch;"
		);
		
		desconectarButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
			
			}
		});

		
		final ToggleGroup grupoRB = new ToggleGroup();
		respuesta1RadioButton.setToggleGroup(grupoRB);
		//respuesta1RadioButton.setSelected(true);
		respuesta2RadioButton.setToggleGroup(grupoRB);
		respuesta3RadioButton.setToggleGroup(grupoRB);

		aceptarButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				numAleatorio = (int) (Math.random() * 9);
				while(preguntasTest.contains(numAleatorio))
					numAleatorio = (int) (Math.random() * 9);
				preguntasTest.add(numAleatorio);
				posicion = 0;
				while(preguntasTest.get(posicion) != numAleatorio)
					posicion++;
				insertarPregunta(ServiceLocator.getPreguntaServices().listarPreguntas().get(preguntasTest.get(posicion)));
				realizarTestTab.setDisable(false);
				aceptarButton.setDisable(true);
			}
		});
		
		siguienteButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
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
				numAleatorio = (int) (Math.random() * 9);
				while(preguntasTest.size() < 9 & preguntasTest.contains(numAleatorio)){
					numAleatorio = (int) (Math.random() * 9);
				}
				
				if(total < 9){
					preguntasTest.add(numAleatorio);
					posicion = 0;
					while(preguntasTest.get(posicion) != numAleatorio)
						posicion++;
					insertarPregunta(ServiceLocator.getPreguntaServices().listarPreguntas().get(preguntasTest.get(posicion)));
				}
				if(total == 9){
					siguienteButton.setDisable(true);
					terminarTestButton.setDisable(false);
				}
			}
		});
		
		terminarTestButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				Resultado resultado = new Resultado();
				resultado.setAlumno_dni(ServiceLocator.getConexionServices().getUsuario().getDni());
				resultado.setAciertos(aciertos);
				resultado.setFallos(fallos);
				resultado.setTotal(total);

				ServiceLocator.getResultadoServices().crearResultado(resultado);
				
				total = 0;
				fallos = 0;
				aciertos = 0;
				siguienteButton.setDisable(false);
				terminarTestButton.setDisable(true);
			}
		});
		/////////////////////////////////////////////////////////////////////////////////////////// TODO
		/////////////////////////////////////////////////////////////////////////////////////////// VER
		/////////////////////////////////////////////////////////////////////////////////////////// USUARIOS
		/////////////////////////////////////////////////////////////////////////////////////////// /////
		
		dniColumn.setCellValueFactory(cellData -> cellData.getValue().alumno_dniProperty());
		aciertosColumn.setCellValueFactory(cellData -> cellData.getValue().aciertosProperty());
		fallosColumn.setCellValueFactory(cellData -> cellData.getValue().fallosProperty());
		totalColumn.setCellValueFactory(cellData -> cellData.getValue().totalProperty());

		tablaResultados.setItems(ServiceLocator.getResultadoServices().listarResultados());
	}
	
	private void insertarPregunta(Pregunta pregunta){
		this.pregunta = pregunta;
		if(total != 30){
			if(pregunta.getImagen() != null)
				imagenImageView.setImage(new Image(pregunta.getImagen().toURI().toString()));
			else
				imagenImageView.setImage(Images.NO_IMAGE);
			preguntaLabel.setText(pregunta.getEnunciado());
			respuesta1RadioButton.setText(pregunta.getPregunta1());
			respuesta2RadioButton.setText(pregunta.getPregunta2());
			respuesta3RadioButton.setText(pregunta.getPregunta3());
			numPreguntaLabel.setText("Total: " + (++total) + " - 30");
		}
	}

	public void setMain(MainAlumno main) {
		this.main = main;
	}
}
