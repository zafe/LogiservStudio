package application.model.compra;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class CategoriaArticulo {

	private final IntegerProperty idCategoriaArticulo;
	private final StringProperty nombre;
	
	public CategoriaArticulo(Integer id, String nombre){
		this.idCategoriaArticulo = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
	}
	
	public CategoriaArticulo(){
		this(null, null);
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

	public final IntegerProperty idCategoriaArticuloProperty() {
		return this.idCategoriaArticulo;
	}
	

	public final int getIdCategoriaArticulo() {
		return this.idCategoriaArticuloProperty().get();
	}
	

	public final void setIdCategoriaArticulo(final int idCategoriaArticulo) {
		this.idCategoriaArticuloProperty().set(idCategoriaArticulo);
	}
	
	
	
	
	
}
