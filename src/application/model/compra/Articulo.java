package application.model.compra;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Articulo {

	private final IntegerProperty idArticulo;
	private final StringProperty marca;
	private final StringProperty modelo;
	private final StringProperty descripcion;
	private final CategoriaArticulo categoria;
	private final IntegerProperty stock;
	
	public Articulo(){
		this(0, null, null, null, null,0);
	}
	
	public Articulo(Integer id, String marca, String modelo, String descripcion, CategoriaArticulo categoria, Integer stock){
		this.idArticulo = new SimpleIntegerProperty(id);
		this.marca = new SimpleStringProperty(marca);
		this.modelo = new SimpleStringProperty(modelo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.categoria = categoria;
		this.stock= new SimpleIntegerProperty(stock);
	}

	public int getIdArticulo() {
		return idArticulo.get();
	}

	public IntegerProperty idArticuloProperty() {
		return idArticulo;
	}

	public void setIdArticulo(int idArticulo) {
		this.idArticulo.set(idArticulo);
	}

	public String getMarca() {
		return marca.get();
	}

	public StringProperty marcaProperty() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca.set(marca);
	}

	public String getModelo() {
		return modelo.get();
	}

	public StringProperty modeloProperty() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo.set(modelo);
	}

	public String getDescripcion() {
		return descripcion.get();
	}

	public StringProperty descripcionProperty() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion.set(descripcion);
	}

	public CategoriaArticulo getCategoria() {
		return categoria;
	}

	public int getStock() {
		return stock.get();
	}

	public IntegerProperty stockProperty() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock.set(stock);
	}
}
