package application.model.info;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Localidad {

private final IntegerProperty idLocalidad;
private final StringProperty nombre;
private final StringProperty provincia_nombre;
private Provincia provincia;
	
	public Localidad(Integer id, String nombre, String provincia_nombre){
		this.idLocalidad = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.provincia_nombre = new SimpleStringProperty(provincia_nombre);
	}
	
	public Localidad(){    
		this(0, null, null);
	}

	public final StringProperty nombreProperty() {
		return this.nombre;
	}
	

	public final String getNombre() {
		return this.nombreProperty().get();
	}
	

	public final void setNombre(final String nombre) {
		this.nombreProperty().set(nombre);
	}

	public final StringProperty provinciaProperty() {
		return this.provincia_nombre;
	}
	

	public final String getProvincia() {
		return this.provinciaProperty().get();
	}
	

	public final void setProvincia(final String provincia) {
		this.provinciaProperty().set(provincia);
	}

	public final IntegerProperty idLocalidadProperty() {
		return this.idLocalidad;
	}
	

	public final int getIdLocalidad() {
		return this.idLocalidadProperty().get();
	}
	

	public final void setIdLocalidad(final int idLocalidad) {
		this.idLocalidadProperty().set(idLocalidad);
	}
	
}
