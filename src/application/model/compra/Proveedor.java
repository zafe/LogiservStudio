package application.model.compra;

import application.model.info.Domicilio;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Proveedor {

	private final IntegerProperty id;
	private final StringProperty nombre;
	private final StringProperty cuit;
	private Domicilio domicilio;

	public Proveedor(){
		this(0,  null, null, null);
	}
	
	public Proveedor(Integer id, String nombre, String cuit, Domicilio domicilio){
		this.id = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.cuit = new SimpleStringProperty(cuit);
		this.domicilio= domicilio;
	}

	public int getId() {
		return id.get();
	}

	public IntegerProperty idProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
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

	public String getCuit() {
		return cuit.get();
	}

	public StringProperty cuitProperty() {
		return cuit;
	}

	public void setCuit(String cuit) {
		this.cuit.set(cuit);
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio){
		this.domicilio = domicilio;
	}

	@Override
	public String toString() {
		return getNombre();
	}
}
