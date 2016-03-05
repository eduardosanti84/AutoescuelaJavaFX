package dad.autoescuela.controllers;

import dad.autoescuela.Main;
import dad.autoescuela.MainAlumno;
import dad.autoescuela.model.Resultado;
import dad.autoescuela.services.ServiceLocator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class MenuAlumnoController {

	@SuppressWarnings("unused")
	private MainAlumno main;

	@FXML
	private Button terminarTestButton;
	@FXML
	private Button siguienteButton;
	@FXML
	private Button salirButton;
	@FXML
	private Label preguntaLabel;
	@FXML
	private RadioButton respuesta1RadioButton;
	@FXML
	private RadioButton respuesta2RadioButton;
	@FXML
	private RadioButton respuesta3RadioButton;
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
	private void initialize() {

		///////////////////////////////////////////////////////////////////////////////////// TODO
		///////////////////////////////////////////////////////////////////////////////////// FORMULARIO
		///////////////////////////////////////////////////////////////////////////////////// USUARIO
		///////////////////////////////////////////////////////////////////////////////////// /////
		terminarTestButton.onActionProperty().set(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				
				System.out.println("toy aqui");
				int aciertos = 0;
				int fallos = 0;
				int total = 0;
				
				Resultado resultado = new Resultado();
				resultado.setAlumno_dni("7856020A");
				//resultado.setAlumno_dni(ServiceLocator.getConexionServices().getUsuario().getDni());
				resultado.setAciertos(aciertos);
				resultado.setFallos(fallos);
				resultado.setTotal(total);
//
				ServiceLocator.getResultadoServices().crearResultado(resultado);
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

	public void setMain(MainAlumno main) {
		this.main = main;
	}
}
