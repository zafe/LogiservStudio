package application.model.info;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoriaEmpleado {

	private final StringProperty nombre;
	
	public CategoriaEmpleado(String nombre){
		this.nombre = new SimpleStringProperty(nombre);
	}
	
	public CategoriaEmpleado(){
		this(null);
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
	
	
	
}
