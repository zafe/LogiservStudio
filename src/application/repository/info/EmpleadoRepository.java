package application.repository.info;

import application.model.info.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.JDBCConnection;

public class EmpleadoRepository {
	
	public static ObservableList<Empleado> buscarEmpleados(){
		
		
		ObservableList<Empleado> empleados = FXCollections.observableArrayList();;
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = JDBCConnection.getInstanceConnection().createStatement();
			resultSet = statement.executeQuery("select * from EMPLEADO");
			
			while(resultSet.next()){
			Empleado empleado = new Empleado();
			empleado.setNombre(resultSet.getString("Nombre"));
			empleado.setApellido(resultSet.getString("Apellido"));
			System.out.println("Empleado " + empleado.getNombre() + " " + empleado.getApellido());
			empleados.add(empleado);
			}
			
			System.out.println(empleados.get(0).getNombre());
			System.out.println(empleados.get(1).getNombre());
			System.out.println(empleados.get(2).getNombre());
			System.out.println(empleados.get(3).getNombre());
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empleados;
		
	}
	
	
	public static Empleado buscarEmpleadoById(Integer id){
		
		Empleado empleado = new Empleado();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = JDBCConnection.getInstanceConnection().createStatement();
			System.out.println("EL ID DEL EMPLEADO ES " + id);
			resultSet = statement.executeQuery("select * from EMPLEADO where idEmpleado="+id);
			
			if(resultSet.next())
			{
			empleado.setNombre(resultSet.getString("Nombre"));
			empleado.setApellido(resultSet.getString("Apellido"));
			}
			
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
		return empleado;
		}

}
