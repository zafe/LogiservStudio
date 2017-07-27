package application.model.info;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Domicilio {
	
	private IntegerProperty idDomicilio;
	private StringProperty nombre_provincia;
	private StringProperty nombre_localidad;
	private StringProperty calle;
	private StringProperty numero;
	
	public Domicilio(Integer id, String provincia, String localidad, String calle, String numero){
		this.idDomicilio = new SimpleIntegerProperty(id);
		this.nombre_provincia = new SimpleStringProperty(provincia);
		this.nombre_localidad = new SimpleStringProperty(localidad);
		this.calle = new SimpleStringProperty(calle);
		this.numero = new SimpleStringProperty(numero);
	}
	
	public Domicilio(){
		this(null, null, null, null, null);
	}

	public final StringProperty nombre_provinciaProperty() {
		return this.nombre_provincia;
	}
	

	public final String getNombre_provincia() {
		return this.nombre_provinciaProperty().get();
	}
	

	public final void setNombre_provincia(final String nombre_provincia) {
		this.nombre_provinciaProperty().set(nombre_provincia);
	}
	

	public final StringProperty nombre_localidadProperty() {
		return this.nombre_localidad;
	}
	

	public final String getNombre_localidad() {
		return this.nombre_localidadProperty().get();
	}
	

	public final void setNombre_localidad(final String nombre_localidad) {
		this.nombre_localidadProperty().set(nombre_localidad);
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

	public final IntegerProperty idDomicilioProperty() {
		return this.idDomicilio;
	}
	

	public final int getIdDomicilio() {
		return this.idDomicilioProperty().get();
	}
	

	public final void setIdDomicilio(final int idDomicilio) {
		this.idDomicilioProperty().set(idDomicilio);
	}
	
}
