package application.model.info;

import application.repository.info.LocalidadRepository;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import application.repository.info.LocalidadRepository;

public class Domicilio {
	
	private IntegerProperty idDomicilio;
	private Localidad localidad;
	private StringProperty calle;
	private StringProperty numero;

	
	public Domicilio(Integer id, Localidad localidad, String calle, String numero){
		this.idDomicilio = new SimpleIntegerProperty(id);
		this.localidad=localidad;
		this.calle = new SimpleStringProperty(calle);
		this.numero = new SimpleStringProperty(numero);
	}


	public Domicilio(){
		this(0, null,null, null);
		this.localidad = new Localidad();
	}

	public int getIdDomicilio() {
		return idDomicilio.get();
	}

	public IntegerProperty idDomicilioProperty() {
		return idDomicilio;
	}

	public void setIdDomicilio(int idDomicilio) {
		this.idDomicilio.set(idDomicilio);
	}

	public Localidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(Localidad localidad) {
		this.localidad = localidad;
	}

	public void setLocalidad() {
		LocalidadRepository localidadRepository = new LocalidadRepository();

		this.localidad = localidadRepository.getLocalidadByIdDomicilio(idDomicilio.intValue());
	}

	public String getCalle() {
		return calle.get();
	}

	public StringProperty calleProperty() {
		return calle;
	}

	public void setCalle(String calle) {
		this.calle.set(calle);
	}

	public String getNumero() {
		return numero.get();
	}

	public StringProperty numeroProperty() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero.set(numero);
	}
}
