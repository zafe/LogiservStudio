	package application.model.compra;

    import javafx.beans.property.*;

public class FacturaCompra {

	private IntegerProperty idFacturaCompra;
	private StringProperty fecha;
	private DoubleProperty total;
	private Proveedor proveedor;
	
	public FacturaCompra(Integer id, String fecha, Double total, Proveedor proveedor){
		this.idFacturaCompra = new SimpleIntegerProperty(id);
		this.fecha = new SimpleStringProperty(fecha);
		this.total = new SimpleDoubleProperty(total);
		this.proveedor = proveedor;
	}
	
	public FacturaCompra(){
		this(0, null, 0.0, null);
	}

	public int getIdFacturaCompra() {
		return idFacturaCompra.get();
	}

	public IntegerProperty idFacturaCompraProperty() {
		return idFacturaCompra;
	}

	public void setIdFacturaCompra(int idFacturaCompra) {
		this.idFacturaCompra.set(idFacturaCompra);
	}

	public String getFecha() {
		return fecha.get();
	}

	public StringProperty fechaProperty() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha.set(fecha);
	}

	public double getTotal() {
		return total.get();
	}

	public DoubleProperty totalProperty() {
		return total;
	}

	public void setTotal(double total) {
		this.total.set(total);
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
}
