package application.model.info;

import java.time.LocalDate;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Empleado {
	
	private final StringProperty nombre;
	private final StringProperty apellido;
	
	public Empleado(){
		this(null, null);
	}
	
	/**
	 *
	 * @param nombre
	 * @param apellido
	 */
	
	public Empleado(String name, String surname) {
		this.nombre = new SimpleStringProperty(name);
		this.apellido = new SimpleStringProperty(surname);
	}

	public String getNombre(){
		return nombre.get();
	}
	
	public void setNombre(String nombre){	
		this.nombre.set(nombre);
	}
	
	public StringProperty nombreProperty() {
		return nombre;
	}
	
	public String getApellido(){
		return apellido.get();
	}
	
	public void setApellido(String apellido){
		this.apellido.set(apellido);
	}
	
	public StringProperty apellidoProperty(){
		return apellido;
	}

	

	
	
	
	

}
