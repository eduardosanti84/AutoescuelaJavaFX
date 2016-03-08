package dad.autoescuela.controllers;

import java.io.File;

import dad.autoescuela.Main;
import dad.autoescuela.model.Pregunta;
import dad.autoescuela.resources.images.Images;
import dad.autoescuela.services.ServiceLocator;
import dad.autoescuela.utils.Utils;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class MenuCrearPreguntaController {
	
	private Main main;
	private String radioButtonSelected= "1";;
	private File imagenFile = null;
	
	@FXML
	private Button guardarPreguntaButton;
	@FXML
	private Button guardarCerrarPreguntaButton;
	@FXML
	private TextArea enunciadoTextArea;
	@FXML
	private TextArea respuesta1TextArea;
	@FXML
	private TextArea respuesta2TextArea;
	@FXML
	private TextArea respuesta3TextArea;
	@FXML
	private RadioButton rb1;
	@FXML
	private RadioButton rb2;
	@FXML
	private RadioButton rb3;
	@FXML
	private ImageView imagenPregunta;
	
	@FXML
	private void initialize(){

		final ToggleGroup grupoRB = new ToggleGroup();
		rb1.setToggleGroup(grupoRB);
		rb1.setSelected(true);
		rb2.setToggleGroup(grupoRB);
		rb3.setToggleGroup(grupoRB);
		grupoRB.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
			public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle) {
	
	            if(rb1.isSelected()){
	            	radioButtonSelected = "1";
	            	System.out.println("cmabie a 1");
	            }
	            if(rb2.isSelected())
	            	radioButtonSelected = "2";
	            if(rb3.isSelected())
	            	radioButtonSelected = "3";
		    }
		});
		
		imagenPregunta.setImage(Images.INSERT_IMAGE);
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												LISTENER
	 *********************************************************************************************************************
	 **/
	
	////////////////////////////////////////////////////////////////////////////////////TODO FORMULARIO PREGUNTA /////
	@FXML
	public void onImagenPreguntaMouseClicked(){
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
	
	@FXML
	public void onGuardarPreguntaButtonAction(){
		
		System.out.println(enunciadoTextArea.getText());
		System.out.println(respuesta1TextArea.getText());
		System.out.println(respuesta2TextArea.getText());
		System.out.println(respuesta3TextArea.getText());
		System.out.println(radioButtonSelected);
		
		if(!enunciadoTextArea.getText().isEmpty() && !respuesta1TextArea.getText().isEmpty() && 
				!respuesta2TextArea.getText().isEmpty() && !respuesta3TextArea.getText().isEmpty()){
			Pregunta pregunta = new Pregunta();
			pregunta.setEnunciado(enunciadoTextArea.getText());
			pregunta.setRespuesta1(respuesta1TextArea.getText());
			pregunta.setRespuesta2(respuesta2TextArea.getText());
			pregunta.setRespuesta3(respuesta3TextArea.getText());
			pregunta.setRespuestaCorrecta(radioButtonSelected);
			pregunta.setImagen(new Image(imagenFile.toURI().toString()));
			
			if(ServiceLocator.getPreguntaServices().crearPregunta(pregunta)){
				Utils.mensaje(AlertType.INFORMATION, "Correcto", "Se ha creado la pregunta", "La pregunta se ha creado correctamente!");
				limpiarFormularioPregunta();
			}
			else  Utils.mensaje(AlertType.ERROR, "Error", "Error al crear", "Ha ocurrido un error al crear la pregunta, comprueba los datos!");
		}
		else  Utils.mensaje(AlertType.ERROR, "Error", "Comprobación de los datos", "Faltan datos!");
	}
	
	@FXML
	public void onGuardarCerrarPreguntaButtonAction(){
		onGuardarPreguntaButtonAction();
		main.getStagesCrearPregunta().close();
	}
	
	/*
	 *********************************************************************************************************************
	 *** 												METODOS
	 *********************************************************************************************************************
	 **/
	private void limpiarFormularioPregunta(){
		enunciadoTextArea.setText("");
		respuesta1TextArea.setText("");
		respuesta2TextArea.setText("");
		respuesta3TextArea.setText("");
		imagenPregunta.setImage(Images.INSERT_IMAGE);
		imagenFile = null;
	}
	
	public void setMain(Main main) {
		this.main = main;
	}
}
