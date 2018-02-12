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
			empleado.setDomicilio(resultSet.getInt("DOMICILIO_idDomicilio"));
			System.out.println("Empleado " + empleado.getNombre() + " " + empleado.getApellido() + " idDomicilio :" + empleado.getDomicilio().getIdDomicilio());
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
	}
	
	public static void edit(Empleado empleado){
		try {
            Connection connection= JDBCConnection.getInstanceConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE DOMICILIO SET Calle = ? , Numero = ?, LOCALIDAD_idLocalidad = ? WHERE idDomicilio = ?;");
            //UPDATE del Domicilio
            preparedStatement.setString(1, empleado.getDomicilio().getCalle());
            preparedStatement.setString(2, empleado.getDomicilio().getNumero());
            preparedStatement.setInt(3, empleado.getDomicilio().getLocalidad().getIdLocalidad());
            preparedStatement.setInt(4, empleado.getDomicilio().getIdDomicilio());
            System.out.println("DOMICILIO UPDATE SQL: " + preparedStatement.toString());
            preparedStatement.executeUpdate();
            //UPDATE del Empleado
			preparedStatement = connection.prepareStatement(
					"UPDATE EMPLEADO SET CUIT = ?, hijos = ?, Nombre = ?, Apellido = ?, FechaNacimiento = ?, " +
					"CATEGORIA_EMPLEADO_idCategoriaEmpleado = ? WHERE idEmpleado = ?;");
            preparedStatement.setString(1, empleado.getCuit());
            preparedStatement.setInt(2, empleado.getHijos());
            preparedStatement.setString(3, empleado.getNombre());
			preparedStatement.setString(4, empleado.getApellido());
			preparedStatement.setString(5, empleado.getNacimiento());
			preparedStatement.setInt(6, empleado.getCategoriaEmpleado().getIdCategoriaEmpleado());
			preparedStatement.setInt(7, empleado.getIdEmpleado());
			preparedStatement.executeUpdate();
            preparedStatement.close();
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

		public ObservableList<Empleado> getEmpleadosByCategoriaEmpleado(int idCategoria){
			ObservableList<Empleado> list = FXCollections.observableArrayList();
			try {
				Connection connection= JDBCConnection.getInstanceConnection();
				PreparedStatement preparedStatement=connection.prepareStatement("select idEmpleado, " +
						"Apellido, Nombre " +
						"from Empleado " +
						"where CATEGORIA_EMPLEADO_idCategoriaEmpleado = ?");
				preparedStatement.setInt(1,idCategoria);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					Empleado empleado = new Empleado();
					empleado.setIdEmpleado(resultSet.getInt(1));
					empleado.setApellido(resultSet.getString(2));
					empleado.setNombre(resultSet.getString(3));
					list.add(empleado);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		}

		public void save(Empleado empleado){
			try {
				Connection connection= JDBCConnection.getInstanceConnection();
				//Inserta primero el Domicilio, luego retiene el idDomicilio para usarlo en la inserción del Empleado
				PreparedStatement preparedStatement=connection.prepareStatement("INSERT INTO DOMICILIO " +
						"(Calle, Numero, LOCALIDAD_idLocalidad) " +
						"VALUES (?,?,?); " , Statement.RETURN_GENERATED_KEYS);
				//Inserta el Domicilio
				preparedStatement.setString(1,empleado.getDomicilio().getCalle());
				preparedStatement.setString(2,empleado.getDomicilio().getNumero());
				preparedStatement.setInt(3, empleado.getDomicilio().getLocalidad().getIdLocalidad());
				preparedStatement.executeUpdate();
				ResultSet rs = preparedStatement.getGeneratedKeys();
				int last_inserted_id = 0;
				if(rs.next())
					last_inserted_id = rs.getInt(1);


				//Conseguir el idDomicilio recientemente ingresado
				preparedStatement = connection.prepareStatement("INSERT INTO EMPLEADO " +
						"(CUIT, hijos, Nombre, Apellido, FechaNacimiento, CATEGORIA_EMPLEADO_idCategoriaEmpleado, " +
						"DOMICILIO_idDomicilio)" +
						" VALUES (?,?,?,?,?,?,?)");
				//Inserta el Empleado
				preparedStatement.setString(1, empleado.getCuit());
				preparedStatement.setInt(2, empleado.getHijos());
				preparedStatement.setString(3, empleado.getNombre());
				preparedStatement.setString(4, empleado.getApellido());
				preparedStatement.setString(5, empleado.getNacimiento());
				preparedStatement.setInt(6, empleado.getCategoriaEmpleado().getIdCategoriaEmpleado());
				preparedStatement.setInt(7, last_inserted_id);
				System.out.println("I SQL: " + preparedStatement.toString());
				preparedStatement.executeUpdate();

				System.out.printf("Empleado %s %s agregado correctamente", empleado.getNombre(), empleado.getApellido());

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
}