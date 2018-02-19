package application.repository.info;

import application.comunes.Alerta;
import application.model.info.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.JDBCConnection;

public class EmpleadoRepository {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;

	public ObservableList<Empleado> buscarEmpleados(){
		ObservableList<Empleado> empleados = FXCollections.observableArrayList();
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement("SELECT e.idEmpleado, e.CUIT, e.Nombre, e.Apellido, " +
					"e.FechaNacimiento, c.idCategoriaEmpleado, c.NombreCategoria,\n" +
					"\t\td.idDomicilio, d.Calle, d.Numero, l.idLocalidad, l.NombreLocalidad, p.*\n" +
					"\tFROM EMPLEADO e, CATEGORIA_EMPLEADO c, DOMICILIO d, LOCALIDAD AS l, PROVINCIA AS p \n" +
					"\t\tWHERE e.CATEGORIA_EMPLEADO_idCategoriaEmpleado = c.idCategoriaEmpleado \n" +
					"\t\t\tAND e.DOMICILIO_idDomicilio = d.idDomicilio\n" +
					"\t\t\tAND d.LOCALIDAD_idLocalidad=l.idLocalidad \n" +
					" AND e.FechaBaja IS null " +  //aqui traemos a los empleados cuya fecha de baja no fue ingresada
					"            AND l.PROVINCIA_idProvincia=p.idProvincia");
			resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()){
				Empleado empleado = new Empleado();
				empleado.setIdEmpleado(resultSet.getInt(1));
				empleado.setCuit(resultSet.getString(2));
				empleado.setNombre(resultSet.getString(3));
				empleado.setApellido(resultSet.getString(4));
				empleado.setNacimiento(resultSet.getString(5));
				empleado.setCategoriaEmpleado(new CategoriaEmpleado(resultSet.getInt(6), resultSet.getString(7)));
				empleado.setDomicilio(new Domicilio(resultSet.getInt(8),
						new Localidad(resultSet.getInt(11), resultSet.getString(12),
								new Provincia(resultSet.getInt(13), resultSet.getString(14))),
						resultSet.getString(9), resultSet.getString(10)));
				empleados.add(empleado);
			}
			} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}

	/**
	 * this method make a logic logicDelete
	 * @param empleado
	 */
	public void logicDelete(Empleado empleado){
		try {
            Connection connection = JDBCConnection.getInstanceConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE EMPLEADO SET FechaBaja=? where idEmpleado=?");
			preparedStatement.setString(1, empleado.getFechaBaja());
			preparedStatement.setInt(2, empleado.getIdEmpleado());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
	}
	
	public void edit(Empleado empleado){
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement=connection.prepareStatement(" UPDATE EMPLEADO as e " +
					"    INNER JOIN DOMICILIO as d ON e.DOMICILIO_idDomicilio = d.idDomicilio " +
					"    SET e.CUIT = ?, e.CATEGORIA_EMPLEADO_idCategoriaEmpleado=?, " +
					"			e.Nombre=?, e.Apellido =?, e.FechaNacimiento=?, d.LOCALIDAD_idLocalidad=?" +
					"    WHERE e.idEmpleado = ?");
			preparedStatement.setString(1, empleado.getCuit());
			preparedStatement.setInt(2, empleado.getCategoriaEmpleado().getIdCategoriaEmpleado());
			preparedStatement.setString(3, empleado.getNombre());
			preparedStatement.setString(4, empleado.getApellido());
			preparedStatement.setString(5, empleado.getNacimiento());
			preparedStatement.setInt(6, empleado.getDomicilio().getLocalidad().getIdLocalidad());
			preparedStatement.setInt(7, empleado.getIdEmpleado());
			preparedStatement.executeUpdate();
			String headerMsj="Actualización: Empleado actualizado";
			String cuerpoMsj = "Empleado: " + empleado.getNombre() + " modificado correctamente.";
			Alerta.alertaInfo("Empleados", headerMsj, cuerpoMsj);
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
						"where CATEGORIA_EMPLEADO_idCategoriaEmpleado = ?" +
						" AND FechaBaja IS null");
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
	/**
	 * @Isa: El empleado siempre se guarda con una fecha de baja null
	 */
		public void save(Empleado empleado){
				try {
					connection = JDBCConnection.getInstanceConnection();
					preparedStatement= connection.prepareStatement("INSERT INTO EMPLEADO VALUES(?,?,?,?,?,?,LAST_INSERT_ID(),?,?)");
					preparedStatement.setString(1,null);
					preparedStatement.setString(2, empleado.getCuit());
					preparedStatement.setInt(3, empleado.getCategoriaEmpleado().getIdCategoriaEmpleado());
					preparedStatement.setString(4, empleado.getNombre());
					preparedStatement.setString(5, empleado.getApellido());
					preparedStatement.setString(6, empleado.getNacimiento());
					preparedStatement.setString(7, empleado.getFechaAlta());
					preparedStatement.setString(8, null); //fecha de baja
					preparedStatement.executeUpdate();
					String cuerpoMsj = "Empleado  " + empleado.getNombre() + " agregado correctamente.\n";
					Alerta.alertaInfo("Empleados",cuerpoMsj);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	public ObservableList<Empleado> buscarEmpleadosDadosDeBaja(){
		ObservableList<Empleado> empleados = FXCollections.observableArrayList();
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement("SELECT e.idEmpleado, e.CUIT, e.Nombre, e.Apellido, " +
					"e.FechaNacimiento, c.idCategoriaEmpleado, c.NombreCategoria,\n" +
					"\t\td.idDomicilio, d.Calle, d.Numero, l.idLocalidad, l.NombreLocalidad, p.*, e.FechaBaja\n" +
					"\tFROM EMPLEADO e, CATEGORIA_EMPLEADO c, DOMICILIO d, LOCALIDAD AS l, PROVINCIA AS p \n" +
					"\t\tWHERE e.CATEGORIA_EMPLEADO_idCategoriaEmpleado = c.idCategoriaEmpleado \n" +
					"\t\t\tAND e.DOMICILIO_idDomicilio = d.idDomicilio\n" +
					"\t\t\tAND d.LOCALIDAD_idLocalidad=l.idLocalidad \n" +
					" AND e.FechaBaja IS NOT null " +  //aqui traemos a los empleados cuya fecha de baja fue ingresada
					"            AND l.PROVINCIA_idProvincia=p.idProvincia");
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()){
				Empleado empleado = new Empleado();
				empleado.setIdEmpleado(resultSet.getInt(1));
				empleado.setCuit(resultSet.getString(2));
				empleado.setNombre(resultSet.getString(3));
				empleado.setApellido(resultSet.getString(4));
				empleado.setNacimiento(resultSet.getString(5));
				empleado.setCategoriaEmpleado(new CategoriaEmpleado(resultSet.getInt(6), resultSet.getString(7)));
				empleado.setDomicilio(new Domicilio(resultSet.getInt(8),
						new Localidad(resultSet.getInt(11), resultSet.getString(12),
								new Provincia(resultSet.getInt(13), resultSet.getString(14))),
						resultSet.getString(9), resultSet.getString(10)));
				empleado.setFechaBaja(resultSet.getString(15));
				empleados.add(empleado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empleados;
	}
		public Empleado getEmpleadoById(int idEmpleado){
			Empleado empleado = new Empleado();
			try {
				Connection connection= JDBCConnection.getInstanceConnection();
				PreparedStatement preparedStatement =connection.prepareStatement("SELECT idEmpleado, CUIT, Nombre," +
						" Apellido, FechaNacimiento FROM EMPLEADO WHERE idEmpleado=?;");
				preparedStatement.setInt(1,idEmpleado);
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					empleado.setIdEmpleado(resultSet.getInt(1));
					empleado.setCuit(resultSet.getString(2));
					empleado.setNombre(resultSet.getString(3));
					empleado.setApellido(resultSet.getString(4));
					empleado.setNacimiento(resultSet.getString(5));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return empleado;
		}
}
