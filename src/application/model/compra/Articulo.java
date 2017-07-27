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
	private final IntegerProperty categoria;
	
	public Articulo(){
		this(null, null, null, null, null);
	}
	
	public Articulo(Integer id, String marca, String modelo, String descripcion, Integer categoria){
		this.idArticulo = new SimpleIntegerProperty(id);
		this.marca = new SimpleStringProperty(marca);
		this.modelo = new SimpleStringProperty(modelo);
		this.descripcion = new SimpleStringProperty(descripcion);
		this.categoria = new SimpleIntegerProperty(categoria);
	}

	public final StringProperty marcaProperty() {
		return this.marca;
	}
	

	public final String getMarca() {
		return this.marcaProperty().get();
	}
	

	public final void setMarca(final String marca) {
		this.marcaProperty().set(marca);
	}
	

	public final StringProperty modeloProperty() {
		return this.modelo;
	}
	

	public final String getModelo() {
		return this.modeloProperty().get();
	}
	

	public final void setModelo(final String modelo) {
		this.modeloProperty().set(modelo);
	}
	

	public final StringProperty descripcionProperty() {
		return this.descripcion;
	}
	

	public final String getDescripcion() {
		return this.descripcionProperty().get();
	}
	

	public final void setDescripcion(final String descripcion) {
		this.descripcionProperty().set(descripcion);
	}
	

	public final IntegerProperty categoriaProperty() {
		return this.categoria;
	}
	

	public final int getCategoria() {
		return this.categoriaProperty().get();
	}
	

	public final void setCategoria(final int categoria) {
		this.categoriaProperty().set(categoria);
	}

	public final IntegerProperty idArticuloProperty() {
		return this.idArticulo;
	}
	

	public final int getIdArticulo() {
		return this.idArticuloProperty().get();
	}
	

	public final void setIdArticulo(final int idArticulo) {
		this.idArticuloProperty().set(idArticulo);
	}
	
	

}
