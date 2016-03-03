package dad.autoescuela.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Resultado {

	private final IntegerProperty id = new SimpleIntegerProperty();
	private final IntegerProperty aciertos = new SimpleIntegerProperty();
	private final IntegerProperty fallos = new SimpleIntegerProperty();
	private final IntegerProperty total = new SimpleIntegerProperty();
	private final StringProperty alumno_dni = new SimpleStringProperty();
	
	public final IntegerProperty idProperty() {
		return this.id;
	}
	
	public final int getId() {
		return this.idProperty().get();
	}
	
	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	
	public final IntegerProperty aciertosProperty() {
		return this.aciertos;
	}
	
	public final int getAciertos() {
		return this.aciertosProperty().get();
	}
	
	public final void setAciertos(final int aciertos) {
		this.aciertosProperty().set(aciertos);
	}
	
	public final IntegerProperty fallosProperty() {
		return this.fallos;
	}
	
	public final int getFallos() {
		return this.fallosProperty().get();
	}
	
	public final void setFallos(final int fallos) {
		this.fallosProperty().set(fallos);
	}
	
	public final IntegerProperty totalProperty() {
		return this.total;
	}
	
	public final int getTotal() {
		return this.totalProperty().get();
	}
	
	public final void setTotal(final int total) {
		this.totalProperty().set(total);
	}
	
	public final StringProperty alumno_dniProperty() {
		return this.alumno_dni;
	}
	
	public final java.lang.String getAlumno_dni() {
		return this.alumno_dniProperty().get();
	}
	
	public final void setAlumno_dni(final java.lang.String alumno_dni) {
		this.alumno_dniProperty().set(alumno_dni);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id.getValue() == null) ? 0 : id.getValue().hashCode());
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
		Resultado other = (Resultado) obj;
		if (id.getValue() == null) {
			if (other.id.getValue() != null)
				return false;
		} else if (!id.getValue().equals(other.id.getValue()))
			return false;
		return true;
	}
	
	
	
	
	
}
