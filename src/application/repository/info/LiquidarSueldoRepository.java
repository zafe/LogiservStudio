package application.repository.info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import application.database.JDBCConnection;
import application.model.info.Empleado;
import application.model.venta.Viaje;

public class LiquidarSueldoRepository {

	Empleado empleado;
	Viaje viaje;
	Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
	
	public void liquidarSueldo(Empleado empleado, int mes){
		connection= JDBCConnection.getInstanceConnection();
        try {
			preparedStatement=connection.prepareStatement("INSERT INTO LIQUIDACION_EMPLEADO " +
			        "VALUES(?,?,?,?,?,?,?,?,?)");
	        preparedStatement.setString(1,null);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
