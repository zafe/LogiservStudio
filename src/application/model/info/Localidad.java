package application.model.info;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Localidad {

	private final IntegerProperty idLocalidad;
	private final StringProperty nombre;
	private Provincia provincia;


	public Localidad(Integer id, String nombre, Provincia provincia){
		this.idLocalidad = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.provincia= provincia;
	}
	
	public Localidad(){
		this(0, null, null);
	}

	public int getIdLocalidad() {
		return idLocalidad.get();
	}

	public IntegerProperty idLocalidadProperty() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad.set(idLocalidad);
	}

	public String getNombre() {
		return nombre.get();
	}

	public StringProperty nombreProperty() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre.set(nombre);
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
}
