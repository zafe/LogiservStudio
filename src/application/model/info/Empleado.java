package application.model.info;


import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


public class Empleado {

	private IntegerProperty idEmpleado;
	private StringProperty nombre;
	private StringProperty apellido;
	private StringProperty cuit;
	private StringProperty nacimiento;
	private Domicilio domicilio;
	private CategoriaEmpleado categoriaEmpleado;
	private StringProperty fechaAlta;
	private StringProperty fechaBaja;

	public Empleado(){
		this(0, null, null,
				null,  null, null,
				null, null, null);
	}

	/**
	 *
	 * @param name
	 * @param surname
	 */

	public Empleado(Integer id, String name, String surname, String cuit,
					String nacimiento, Domicilio domicilio, CategoriaEmpleado categoriaEmpleado,
					String alta, String baja) {
		this.idEmpleado = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(name);
		this.apellido = new SimpleStringProperty(surname);
		this.cuit = new SimpleStringProperty(cuit);
		this.nacimiento = new SimpleStringProperty(nacimiento);
		this.domicilio = domicilio;
		this.categoriaEmpleado = categoriaEmpleado;
		this.fechaAlta = new SimpleStringProperty(alta);
		this.fechaBaja = new SimpleStringProperty(baja);
	}

	public int getIdEmpleado() {
		return idEmpleado.get();
	}

	public IntegerProperty idEmpleadoProperty() {
		return idEmpleado;
	}

	public void setIdEmpleado(int idEmpleado) {
		this.idEmpleado.set(idEmpleado);
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

	public String getApellido() {
		return apellido.get();
	}

	public StringProperty apellidoProperty() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido.set(apellido);
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

	public String getNacimiento() {
		return nacimiento.get();
	}

	public StringProperty nacimientoProperty() {
		return nacimiento;
	}

	public void setNacimiento(String nacimiento) {
		this.nacimiento.set(nacimiento);
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public CategoriaEmpleado getCategoriaEmpleado() {
		return categoriaEmpleado;
	}

	public void setCategoriaEmpleado(CategoriaEmpleado categoriaEmpleado) {
		this.categoriaEmpleado = categoriaEmpleado;
	}

	public String getFechaAlta() {
		return fechaAlta.get();
	}

	public StringProperty fechaAltaProperty() {
		return fechaAlta;
	}

	public void setFechaAlta(String fechaAlta) {
		this.fechaAlta.set(fechaAlta);
	}

	public String getFechaBaja() {
		return fechaBaja.get();
	}

	public StringProperty fechaBajaProperty() {
		return fechaBaja;
	}

	public void setFechaBaja(String fechaBaja) {
		this.fechaBaja.set(fechaBaja);
	}


	@Override
	public String toString() {
		return getIdEmpleado() + "- " + getApellido() + ", "+ getNombre();
	}
}
