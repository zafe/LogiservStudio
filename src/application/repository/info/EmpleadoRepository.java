package application.repository.info;

import application.model.info.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.JDBCConnection;

public class EmpleadoRepository {
	
	public static ObservableList<Empleado> buscarEmpleados(){
		
		
		ObservableList<Empleado> empleados = FXCollections.observableArrayList();
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			statement = JDBCConnection.getInstanceConnection().createStatement();
			resultSet = statement.executeQuery("SELECT * FROM EMPLEADO e, CATEGORIA_EMPLEADO c, DOMICILIO d "
					+ "WHERE e.CATEGORIA_EMPLEADO_idCategoriaEmpleado = c.idCategoriaEmpleado "
					+ "AND e.DOMICILIO_idDomicilio = d.idDomicilio;");
			
			while(resultSet.next()){
			Empleado empleado = new Empleado();
			empleado.setIdEmpleado(resultSet.getInt("idEmpleado"));
			empleado.setNombre(resultSet.getString("Nombre"));
			empleado.setApellido(resultSet.getString("Apellido"));
			empleado.setHijos(resultSet.getInt("hijos"));
			empleado.setCuit(resultSet.getString("CUIT"));
			empleado.setNacimiento(resultSet.getString("FechaNacimiento"));
			empleado.setCategoria(resultSet.getString("NombreCategoria"));
			empleado.setDomicile(resultSet.getInt("DOMICILIO_idDomicilio"));
			System.out.println("Empleado " + empleado.getNombre() + " " + empleado.getApellido());
			empleados.add(empleado); }
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return empleados;
		
	}
	public static void deleteEmpleadoById(int id){
		try {
            Connection connection= JDBCConnection.getInstanceConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM EMPLEADO WHERE idEmpleado=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public static void EditEmpleadoById(Empleado empleado, int categoria, int domicilio){
		try {
            Connection connection= JDBCConnection.getInstanceConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE EMPLEADO SET Nombre=?, Apellido=?, CUIT=?, hijos=?, FechaNacimiento=?, CATEGORIA_EMPLEADO_idCategoriaEmpleado=?"
                    + ", DOMICILIO_idDomicilio=?, WHERE idEmpleado=?");
            preparedStatement.setString(1, empleado.getNombre());
            preparedStatement.setString(2, empleado.getApellido());
            preparedStatement.setString(3, empleado.getCuit());
            preparedStatement.setInt(4, empleado.getHijos());
            preparedStatement.setString(5, empleado.getNacimiento());
            preparedStatement.setInt(6, categoria);
            preparedStatement.setInt(7, domicilio);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public static Empleado buscarEmpleadoById(Integer id){
		
		Empleado empleado = new Empleado();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = JDBCConnection.getInstanceConnection().createStatement();
			System.out.println("EL ID DEL EMPLEADO ES " + id);
			resultSet = statement.executeQuery("SELECT * FROM EMPLEADO e, CATEGORIA_EMPLEADO c, DOMICILIO d "
					+ "WHERE e.CATEGORIA_EMPLEADO_idCategoriaEmpleado = c.idCategoriaEmpleado "
					+ "AND e.DOMICILIO_idDomicilio = d.idDomicilio "
					+ "AND e.idEmpleado="+id);			
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