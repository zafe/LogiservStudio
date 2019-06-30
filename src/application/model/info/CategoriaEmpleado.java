package application.model.info;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.CheckBox;

public class CategoriaEmpleado {

	private final IntegerProperty idCategoriaEmpleado;
	private final StringProperty nombre;
	private CheckBox select;
	
	public CategoriaEmpleado(Integer id, String nombre){
		this.idCategoriaEmpleado = new SimpleIntegerProperty(id);
		this.nombre = new SimpleStringProperty(nombre);
		this.select = new CheckBox();
	}
	
	public CategoriaEmpleado(){
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

	public final IntegerProperty idCategoriaEmpleadoProperty() {
		return this.idCategoriaEmpleado;
	}
	

	public final int getIdCategoriaEmpleado() {
		return this.idCategoriaEmpleadoProperty().get();
	}
	

	public final void setIdCategoriaEmpleado(final int idCategoriaEmpleado) {
		this.idCategoriaEmpleadoProperty().set(idCategoriaEmpleado);
	}

	public CheckBox getSelect() {
		return select;
	}
	public void setCheckBox(CheckBox select){
		this.select =select;
	}

	@Override
	public String toString() {
		return getNombre();
	}
}
