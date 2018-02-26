package application.model.compra;

import javafx.beans.property.FloatProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class DetalleCompra {

	private IntegerProperty idDetalleCompra;
	private IntegerProperty cantidad;
	private FloatProperty precioUnitario;
	private Articulo articulo;
	private FacturaCompra facturaCompra;

	public DetalleCompra(Integer id, Integer cantidad, Float precio, Articulo articulo, FacturaCompra factura){
		
		this.idDetalleCompra = new SimpleIntegerProperty(id);
		this.cantidad = new SimpleIntegerProperty(cantidad);
		this.precioUnitario = new SimpleFloatProperty(precio);
		this.articulo=articulo;
		this.facturaCompra=factura;
	}

	public DetalleCompra() {
		this(0,0,0f,null,null);
	}

	public int getIdDetalleCompra() {
		return idDetalleCompra.get();
	}

	public IntegerProperty idDetalleCompraProperty() {
		return idDetalleCompra;
	}

	public void setIdDetalleCompra(int idDetalleCompra) {
		this.idDetalleCompra.set(idDetalleCompra);
	}

	public int getCantidad() {
		return cantidad.get();
	}

	public IntegerProperty cantidadProperty() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad.set(cantidad);
	}

	public float getPrecioUnitario() {
		return precioUnitario.get();
	}

	public FloatProperty precioUnitarioProperty() {
		return precioUnitario;
	}

	public void setPrecioUnitario(float precioUnitario) {
		this.precioUnitario.set(precioUnitario);
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public FacturaCompra getFacturaCompra() {
		return facturaCompra;
	}

	public void setFacturaCompra(FacturaCompra facturaCompra) {
		this.facturaCompra = facturaCompra;
	}
}
