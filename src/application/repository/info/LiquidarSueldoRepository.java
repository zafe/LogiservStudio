package application.repository.info;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

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

			java.util.Date fecha_liquidacion = new java.util.Date();
			preparedStatement.setDate(1, (java.sql.Date ) fecha_liquidacion );//fecha_liquidacion
	        preparedStatement.setDouble(2,0.0);//importe_neto
	        preparedStatement.setDouble(3,0.0);//total_haberes_remunerativos
	        preparedStatement.setDouble(4,0.0);//total_haberes_no_remunerativos
	        preparedStatement.setDouble(5,0.0);//total_retenciones
	        preparedStatement.setDouble(6,0.0);//total_bruto
	        preparedStatement.setInt(7,empleado.getIdEmpleado());//EMPLEADO_idEmpleado
	        preparedStatement.setDate(8,null);//inicio_periodo
	        preparedStatement.setDate(9,null);//fin_periodo
	        preparedStatement.execute();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
