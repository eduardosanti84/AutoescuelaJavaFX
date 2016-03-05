package dad.autoescuela.model;

import java.io.File;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Pregunta {

	private final IntegerProperty idPregunta = new SimpleIntegerProperty();
	private final StringProperty enunciado = new SimpleStringProperty();
	private final StringProperty pregunta1 = new SimpleStringProperty();
	private final StringProperty pregunta2 = new SimpleStringProperty();
	private final StringProperty pregunta3 = new SimpleStringProperty();
	private final StringProperty respuesta = new SimpleStringProperty();
	private final ObjectProperty<File> imagen = new SimpleObjectProperty<File>();
	
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
	
	public final StringProperty pregunta1Property() {
		return this.pregunta1;
	}
	
	public final java.lang.String getPregunta1() {
		return this.pregunta1Property().get();
	}
	
	public final void setPregunta1(final java.lang.String pregunta1) {
		this.pregunta1Property().set(pregunta1);
	}
	
	public final StringProperty pregunta2Property() {
		return this.pregunta2;
	}
	
	public final java.lang.String getPregunta2() {
		return this.pregunta2Property().get();
	}
	
	public final void setPregunta2(final java.lang.String pregunta2) {
		this.pregunta2Property().set(pregunta2);
	}
	
	public final StringProperty pregunta3Property() {
		return this.pregunta3;
	}
	
	public final java.lang.String getPregunta3() {
		return this.pregunta3Property().get();
	}
	
	public final void setPregunta3(final java.lang.String pregunta3) {
		this.pregunta3Property().set(pregunta3);
	}
	
	public final StringProperty respuestaProperty() {
		return this.respuesta;
	}
	
	public final java.lang.String getRespuesta() {
		return this.respuestaProperty().get();
	}
	
	public final void setRespuesta(final java.lang.String respuesta) {
		this.respuestaProperty().set(respuesta);
	}
	
	public final ObjectProperty<File> imagenProperty() {
		return this.imagen;
	}
	
	public final java.io.File getImagen() {
		return this.imagenProperty().get();
	}

	public final void setImagen(final java.io.File imagen) {
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
