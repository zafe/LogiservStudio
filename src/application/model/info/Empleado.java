package application.model.info;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Empleado {
	
	private final IntegerProperty idEmpleado;
	private final StringProperty nombre;
	private final StringProperty apellido;
	private final IntegerProperty hijos;
	private final StringProperty cuit;
	private final StringProperty nacimiento;
	private final StringProperty categoria;
	private final IntegerProperty idDomicilio;
	
	public Empleado(){
		this(0, null, null, 0, null, null, null, 0);
	}
	
	/**
	 *
	 * @param name
	 * @param surname
	 */
	
	public Empleado(Integer id, String name, String surname, Integer hijos,
			String cuit, String nacimiento, String categoria, int idDomicilio) {
		this.idEmpleado = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(name);
		this.apellido = new SimpleStringProperty(surname);
		this.hijos = new SimpleIntegerProperty(hijos);
		this.cuit = new SimpleStringProperty(cuit);
		this.nacimiento = new SimpleStringProperty(nacimiento);
		this.categoria = new SimpleStringProperty(categoria);
		this.idDomicilio = new SimpleIntegerProperty(idDomicilio);
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

	public final IntegerProperty idEmpleadoProperty() {
		return this.idEmpleado;
	}
	

	public final int getIdEmpleado() {
		return this.idEmpleadoProperty().get();
	}
	

	public final void setIdEmpleado(final int idEmpleado) {
		this.idEmpleadoProperty().set(idEmpleado);
	}

	public final IntegerProperty hijosProperty() {
		return this.hijos;
	}
	

	public final int getHijos() {
		return this.hijosProperty().get();
	}
	

	public final void setHijos(final int hijos) {
		this.hijosProperty().set(hijos);
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
	

	public final StringProperty nacimientoProperty() {
		return this.nacimiento;
	}
	

	public final String getNacimiento() {
		return this.nacimientoProperty().get();
	}
	

	public final void setNacimiento(final String nacimiento) {
		this.nacimientoProperty().set(nacimiento);
	}
	

	public final StringProperty categoriaProperty() {
		return this.categoria;
	}
	

	public final String getCategoria() {
		return this.categoriaProperty().get();
	}
	

	public final void setCategoria(final String categoria) {
		this.categoriaProperty().set(categoria);
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
