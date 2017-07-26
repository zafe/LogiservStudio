package application.model.compra;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoriaArticulo {

	private final StringProperty nombre;
	
	CategoriaArticulo(){
		this(null);
	}
	
	CategoriaArticulo(String nombre){
		this.nombre = new SimpleStringProperty(nombre);
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
