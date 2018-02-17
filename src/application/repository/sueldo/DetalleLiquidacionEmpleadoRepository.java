package application.repository.sueldo;

import application.database.JDBCConnection;
import application.model.sueldo.DetalleLiquidacionEmpleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleLiquidacionEmpleadoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(DetalleLiquidacionEmpleado detalleLiquidacionEmpleado){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO detalle_liquidacion_EMPLEADO" +
                    "values(?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setDouble(2,detalleLiquidacionEmpleado.getCantidad());
//            preparedStatement.setInt(3,detalleLiquidacionEmpleado.getIdLiquidacionEmpleado());
//            preparedStatement.setInt(4, detalleLiquidacionEmpleado.getIdCodigoConcepto());
            preparedStatement.setDouble(5, detalleLiquidacionEmpleado.getMonto());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void update(DetalleLiquidacionEmpleado detalleLiquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("" +
                    "UPDATE detalle_liquidacion_empleado" +
                    "   SET cantidad=?,  LiquidacionEmpleado_idLiquidacionEmpleado=?, " +
                    "CONCEPTO_SUELDO_idCodigoConcepto=?, monto=?" +
                    "   WHERE idDetalleLiquidacionEmpleado=?");
            preparedStatement.setDouble(1,detalleLiquidacionEmpleado.getCantidad());
//            preparedStatement.setInt(2,detalleLiquidacionEmpleado.getIdLiquidacionEmpleado());
//            preparedStatement.setInt(3,detalleLiquidacionEmpleado.getIdCodigoConcepto());
            preparedStatement.setDouble(4,detalleLiquidacionEmpleado.getMonto());
            preparedStatement.setInt(5, detalleLiquidacionEmpleado.getIdDetalleLiquidacionEmpleado());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(DetalleLiquidacionEmpleado detalleLiquidacionEmpleado){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("DELETE FROM detalle_liquidacion_empleado WHERE idDetalleLiquidacionEmpleado=?");
            preparedStatement.setInt(1,detalleLiquidacionEmpleado.getIdDetalleLiquidacionEmpleado());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<DetalleLiquidacionEmpleado> view(){
        ObservableList<DetalleLiquidacionEmpleado> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM COMPRA_ARTICULO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                DetalleLiquidacionEmpleado detalleLiquidacionEmpleado = new DetalleLiquidacionEmpleado();
                detalleLiquidacionEmpleado.setIdDetalleLiquidacionEmpleado(resultSet.getInt(1));
                detalleLiquidacionEmpleado.setCantidad(resultSet.getDouble(2));
//                detalleLiquidacionEmpleado.setIdLiquidacionEmpleado(resultSet.getInt(3));
//                detalleLiquidacionEmpleado.setIdCodigoConcepto(resultSet.getInt(4));
                detalleLiquidacionEmpleado.setMonto(resultSet.getDouble(5));
                list.add(detalleLiquidacionEmpleado);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
