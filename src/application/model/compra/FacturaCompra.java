	package application.model.compra;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class FacturaCompra {

	private IntegerProperty idFacturaCompra;
	private StringProperty fecha;
	private DoubleProperty total;
	private StringProperty nombre_proveedor;
	
	public FacturaCompra(Integer id, String fecha, Double total, String nombre){
		this.idFacturaCompra = new SimpleIntegerProperty(id);
		this.fecha = new SimpleStringProperty(fecha);
		this.total = new SimpleDoubleProperty(total);
		this.nombre_proveedor = new SimpleStringProperty(nombre);
	}
	
	public FacturaCompra(){
		this(null, null, null, null);
	}

	public final IntegerProperty idFacturaCompraProperty() {
		return this.idFacturaCompra;
	}
	

	public final int getIdFacturaCompra() {
		return this.idFacturaCompraProperty().get();
	}
	

	public final void setIdFacturaCompra(final int idFacturaCompra) {
		this.idFacturaCompraProperty().set(idFacturaCompra);
	}
	

	public final StringProperty fechaProperty() {
		return this.fecha;
	}
	

	public final String getFecha() {
		return this.fechaProperty().get();
	}
	

	public final void setFecha(final String fecha) {
		this.fechaProperty().set(fecha);
	}
	

	public final DoubleProperty totalProperty() {
		return this.total;
	}
	

	public final double getTotal() {
		return this.totalProperty().get();
	}
	

	public final void setTotal(final double total) {
		this.totalProperty().set(total);
	}
	

	public final StringProperty nombre_proveedorProperty() {
		return this.nombre_proveedor;
	}
	

	public final String getNombre_proveedor() {
		return this.nombre_proveedorProperty().get();
	}
	

	public final void setNombre_proveedor(final String nombre_proveedor) {
		this.nombre_proveedorProperty().set(nombre_proveedor);
	}
	
	
	
}
