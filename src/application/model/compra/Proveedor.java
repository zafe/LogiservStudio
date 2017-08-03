package application.model.compra;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Proveedor {

	private final IntegerProperty idProveedor;
	private final StringProperty nombre;
	private final StringProperty cuit;
	private final StringProperty calle;
	private final StringProperty numero;
	private final StringProperty localidad;
	private IntegerProperty idLocalidad;

	public int getIdLocalidad() {
		return idLocalidad.get();
	}

	public IntegerProperty idLocalidadProperty() {
		return idLocalidad;
	}

	public void setIdLocalidad(int idLocalidad) {
		this.idLocalidad.set(idLocalidad);
	}

	public Proveedor(){
		this(0, null, null, null, null, null, 0);
	}
	
	public Proveedor(Integer id, String nombre, String cuit, String calle, String numero, String localidad, Integer idLocalidad){
		this.idProveedor = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.cuit = new SimpleStringProperty(cuit);
		this.calle = new SimpleStringProperty(calle);
		this.numero = new SimpleStringProperty(numero);
		this.localidad = new SimpleStringProperty(localidad);
		this.idLocalidad = new SimpleIntegerProperty(idLocalidad);
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
	

	public final StringProperty cuitProperty() {
		return this.cuit;
	}
	

	public final String getCuit() {
		return this.cuitProperty().get();
	}
	

	public final void setCuit(final String cuit) {
		this.cuitProperty().set(cuit);
	}
	

	public final StringProperty calleProperty() {
		return this.calle;
	}
	

	public final String getCalle() {
		return this.calleProperty().get();
	}
	

	public final void setCalle(final String calle) {
		this.calleProperty().set(calle);
	}
	

	public final StringProperty numeroProperty() {
		return this.numero;
	}
	

	public final String getNumero() {
		return this.numeroProperty().get();
	}
	

	public final void setNumero(final String numero) {
		this.numeroProperty().set(numero);
	}
	

	public final StringProperty localidadProperty() {
		return this.localidad;
	}
	

	public final String getLocalidad() {
		return this.localidadProperty().get();
	}
	

	public final void setLocalidad(final String localidad) {
		this.localidadProperty().set(localidad);
	}

	public final IntegerProperty idProveedorProperty() {
		return this.idProveedor;
	}
	

	public final int getIdProveedor() {
		return this.idProveedorProperty().get();
	}
	

	public final void setIdProveedor(final int idProveedor) {
		this.idProveedorProperty().set(idProveedor);
	}
	
	
	
	
	
	
}
