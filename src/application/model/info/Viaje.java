package application.model.info;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Viaje {

private final IntegerProperty idProvincia;
private final StringProperty nombre;
	
	public Viaje(Integer id, String nombre){
		this.idProvincia = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
	}
	
	public Viaje(){
		this(0, null);
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

	public final IntegerProperty idProvinciaProperty() {
		return this.idProvincia;
	}
	

	public final int getIdProvincia() {
		return this.idProvinciaProperty().get();
	}
	

	public final void setIdProvincia(final int idProvincia) {
		this.idProvinciaProperty().set(idProvincia);
	}
	
	
}
