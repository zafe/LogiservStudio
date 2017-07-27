package application.model.compra;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class DetalleCompra {

	private IntegerProperty idDetalleCompra;
	private IntegerProperty cantidad;
	private FloatProperty precioUnitario;
	private StringProperty articulo_marca;
	private StringProperty articulo_modelo;
	
	public DetalleCompra(Integer id, Integer cantidad, Float precio, 
			String articulo_marca, String articulo_modelo){
		
		this.idDetalleCompra = new SimpleIntegerProperty(id);
		this.cantidad = new SimpleIntegerProperty(cantidad);
		this.precioUnitario = new SimpleFloatProperty(precio);
		this.articulo_marca = new SimpleStringProperty(articulo_marca);
		this.articulo_modelo = new SimpleStringProperty(articulo_modelo);
	}
	
	
	public DetalleCompra(){
		this(null, null, null, null, null);
	}


	public final IntegerProperty idProperty() {
		return this.idDetalleCompra;
	}
	


	public final int getId() {
		return this.idProperty().get();
	}
	


	public final void setId(final int id) {
		this.idProperty().set(id);
	}
	


	public final IntegerProperty cantidadProperty() {
		return this.cantidad;
	}
	


	public final int getCantidad() {
		return this.cantidadProperty().get();
	}
	


	public final void setCantidad(final int cantidad) {
		this.cantidadProperty().set(cantidad);
	}
	


	public final FloatProperty precioUnitarioProperty() {
		return this.precioUnitario;
	}
	


	public final float getPrecioUnitario() {
		return this.precioUnitarioProperty().get();
	}
	


	public final void setPrecioUnitario(final float precioUnitario) {
		this.precioUnitarioProperty().set(precioUnitario);
	}
	


	public final StringProperty articulo_marcaProperty() {
		return this.articulo_marca;
	}
	


	public final String getArticulo_marca() {
		return this.articulo_marcaProperty().get();
	}
	


	public final void setArticulo_marca(final String articulo_marca) {
		this.articulo_marcaProperty().set(articulo_marca);
	}
	


	public final StringProperty articulo_modeloProperty() {
		return this.articulo_modelo;
	}
	


	public final String getArticulo_modelo() {
		return this.articulo_modeloProperty().get();
	}
	


	public final void setArticulo_modelo(final String articulo_modelo) {
		this.articulo_modeloProperty().set(articulo_modelo);
	}
	
	
	
}
