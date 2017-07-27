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
	private final ObjectProperty<Empleado> empleado;
	
	public Usuario(Integer id, String nombre, String password, Integer empleadoId){
		this.idUsuario = new SimpleIntegerProperty(id);
		this.nombre_usuario = new SimpleStringProperty(nombre);
		this.password = new SimpleStringProperty(password);
		this.empleado = new SimpleObjectProperty<Empleado>(EmpleadoRepository.buscarEmpleadoById(empleadoId));
		
	}

	public Usuario(){
		this(null, null, null, null);
	}
	
	public String getNombre_usuario(){
		return nombre_usuario.get();
	}
	
	public String getPassword(){
		return password.get();
	}
	
	
	public void setNombre_usuario(String nombre){
		this.nombre_usuario.set(nombre);
	}
	
	public void setPassword(String password){
		this.password.set(password);		
	}
	
	public void setEmpleado(Integer id){
		System.out.println("USANDO setEmpleado(id)");
		this.empleado.set(EmpleadoRepository.buscarEmpleadoById(id));
	}
	
	public StringProperty nombre_usuarioProperty() {
		return nombre_usuario;
	}

	public StringProperty passwordProperty() {
		return password;
	}

	public ObjectProperty<Empleado> empleadoProperty() {
		return empleado;
	}

	public final IntegerProperty idUsuarioProperty() {
		return this.idUsuario;
	}
	

	public final int getIdUsuario() {
		return this.idUsuarioProperty().get();
	}
	

	public final void setIdUsuario(final int idUsuario) {
		this.idUsuarioProperty().set(idUsuario);
	}
	
	
	
	
}
