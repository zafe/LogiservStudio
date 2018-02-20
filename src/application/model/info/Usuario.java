package application.model.info;

import application.repository.info.EmpleadoRepository;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Usuario {
	
	private final IntegerProperty idUsuario;
	private final StringProperty nombre_usuario;
	private final StringProperty password;
	private Empleado empleado;
	private IntegerProperty root;



	public Usuario(Integer id, String nombre, String password, Empleado empleado, Integer root){
		this.idUsuario = new SimpleIntegerProperty(id);
		this.nombre_usuario = new SimpleStringProperty(nombre);
		this.password = new SimpleStringProperty(password);
		this.empleado = empleado;
		this.root = new SimpleIntegerProperty(root);
		
	}

	public Usuario(){
		this(0, null, null,  null,0);
	}

	public int getIdUsuario() {
		return idUsuario.get();
	}

	public IntegerProperty idUsuarioProperty() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario.set(idUsuario);
	}

	public String getNombre_usuario() {
		return nombre_usuario.get();
	}

	public StringProperty nombre_usuarioProperty() {
		return nombre_usuario;
	}

	public void setNombre_usuario(String nombre_usuario) {
		this.nombre_usuario.set(nombre_usuario);
	}

	public String getPassword() {
		return password.get();
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public void setPassword(String password) {
		this.password.set(password);
	}

	public Empleado getEmpleado() {
		return empleado;
	}

	public void setEmpleado(Empleado empleado) {
		this.empleado = empleado;
	}
	public int getRoot() {
		return root.get();
	}

	public IntegerProperty rootProperty() {
		return root;
	}

	public void setRoot(int root) {
		this.root.set(root);
	}
}
