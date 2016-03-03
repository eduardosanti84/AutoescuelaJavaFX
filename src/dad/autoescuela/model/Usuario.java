package dad.autoescuela.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {

	private final StringProperty nombre = new SimpleStringProperty();
	private final StringProperty dni = new SimpleStringProperty();
	private final StringProperty pass = new SimpleStringProperty();
	private final BooleanProperty profesor = new SimpleBooleanProperty();
	
	public final StringProperty nombreProperty() {
		return this.nombre;
	}

	public final java.lang.String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final java.lang.String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty dniProperty() {
		return this.dni;
	}
	

	public final java.lang.String getDni() {
		return this.dniProperty().get();
	}
	

	public final void setDni(final java.lang.String dni) {
		this.dniProperty().set(dni);
	}
	

	public final StringProperty passProperty() {
		return this.pass;
	}
	

	public final java.lang.String getPass() {
		return this.passProperty().get();
	}
	

	public final void setPass(final java.lang.String pass) {
		this.passProperty().set(pass);
	}
	

	public final BooleanProperty profesorProperty() {
		return this.profesor;
	}
	

	public final boolean isProfesor() {
		return this.profesorProperty().get();
	}
	

	public final void setProfesor(final boolean profesor) {
		this.profesorProperty().set(profesor);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dni.get() == null) ? 0 : dni.get().hashCode());
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
		Usuario other = (Usuario) obj;
		if (dni.get() == null) {
			if (other.dni.get() != null)
				return false;
		} else if (!dni.get().equals(other.dni.get()))
			return false;
		return true;
	}
}

