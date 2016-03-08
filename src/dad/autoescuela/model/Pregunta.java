package dad.autoescuela.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

public class Pregunta {

	private final IntegerProperty idPregunta = new SimpleIntegerProperty();
	private final StringProperty enunciado = new SimpleStringProperty();
	private final StringProperty respuesta1 = new SimpleStringProperty();
	private final StringProperty respuesta2 = new SimpleStringProperty();
	private final StringProperty respuesta3 = new SimpleStringProperty();
	private final StringProperty respuestaCorrecta = new SimpleStringProperty();
	private final ObjectProperty<Image> imagen = new SimpleObjectProperty<Image>();
	
	public final IntegerProperty idPreguntaProperty() {
		return this.idPregunta;
	}
	
	public final int getId() {
		return this.idPreguntaProperty().get();
	}
	
	public final void setId(final int id) {
		this.idPreguntaProperty().set(id);
	}
	
	public final StringProperty enunciadoProperty() {
		return this.enunciado;
	}
	
	public final java.lang.String getEnunciado() {
		return this.enunciadoProperty().get();
	}
	
	public final void setEnunciado(final java.lang.String enunciado) {
		this.enunciadoProperty().set(enunciado);
	}
	
	public final StringProperty respuesta1Property() {
		return this.respuesta1;
	}
	

	public final java.lang.String getRespuesta1() {
		return this.respuesta1Property().get();
	}
	

	public final void setRespuesta1(final java.lang.String respuesta1) {
		this.respuesta1Property().set(respuesta1);
	}
	

	public final StringProperty respuesta2Property() {
		return this.respuesta2;
	}
	

	public final java.lang.String getRespuesta2() {
		return this.respuesta2Property().get();
	}
	

	public final void setRespuesta2(final java.lang.String respuesta2) {
		this.respuesta2Property().set(respuesta2);
	}
	

	public final StringProperty respuesta3Property() {
		return this.respuesta3;
	}
	

	public final java.lang.String getRespuesta3() {
		return this.respuesta3Property().get();
	}
	

	public final void setRespuesta3(final java.lang.String respuesta3) {
		this.respuesta3Property().set(respuesta3);
	}
	

	public final StringProperty respuestaCorrectaProperty() {
		return this.respuestaCorrecta;
	}
	

	public final java.lang.String getRespuestaCorrecta() {
		return this.respuestaCorrectaProperty().get();
	}
	

	public final void setRespuestaCorrecta(final java.lang.String respuestaCorrecta) {
		this.respuestaCorrectaProperty().set(respuestaCorrecta);
	}
	
	public final ObjectProperty<Image> imagenProperty() {
		return this.imagen;
	}
	
	public final Image getImagen() {
		return this.imagenProperty().get();
	}

	public final void setImagen(final Image imagen) {
		this.imagenProperty().set(imagen);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((idPregunta.getValue() == null) ? 0 : idPregunta.getValue().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pregunta other = (Pregunta) obj;
		if (idPregunta.get() != other.idPregunta.get()) {
			return false;
		}
		return true;
	}
}
