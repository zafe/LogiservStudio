package application.repository.sueldo;

import application.database.JDBCConnection;
import application.model.sueldo.LiquidacionEmpleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LiquidacionEmpleadoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(LiquidacionEmpleado liquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("INSERT INTO LIQUIDACION_EMPLEADO " +
                    "VALUES(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,liquidacionEmpleado.getFechaLiquidacion());
            preparedStatement.setDouble(3,liquidacionEmpleado.getImporteNeto());
            preparedStatement.setDouble(4,liquidacionEmpleado.getTotalHaberesRemunerativos());
            preparedStatement.setDouble(5,liquidacionEmpleado.getTotalHaberesNoRemunerativos());
            preparedStatement.setDouble(6,liquidacionEmpleado.getTotalRetenciones());
            preparedStatement.setDouble(7,liquidacionEmpleado.getTotalBruto());
//            preparedStatement.setInt(8, liquidacionEmpleado.getIdPeriodoLiquidacion());
//            preparedStatement.setInt(9,liquidacionEmpleado.getIdEmpleado());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(LiquidacionEmpleado liquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("UPDATE LIQUIDACION_EMPLEADO" +
                    "SET  fecha_liquidacion=?,  importe_neto=?, total_haberes_remunerativos=?," +
                    " total_haberes_no_remunerativos=?, total_retenciones=?, total_bruto=?," +
                    " PERIODO_LIQ_idPERIODO_LIQ=?, EMPLEADO_idEmpleado=?" +
                    "WHERE idLiquidacionEmpleado=?");
            preparedStatement.setString(1,liquidacionEmpleado.getFechaLiquidacion());
            preparedStatement.setDouble(2,liquidacionEmpleado.getImporteNeto());
            preparedStatement.setDouble(3,liquidacionEmpleado.getTotalHaberesRemunerativos());
            preparedStatement.setDouble(4,liquidacionEmpleado.getTotalHaberesNoRemunerativos());
            preparedStatement.setDouble(5,liquidacionEmpleado.getTotalRetenciones());
            preparedStatement.setDouble(6,liquidacionEmpleado.getTotalBruto());
//            preparedStatement.setInt(7,liquidacionEmpleado.setIdPeriodoLiquidacion());
//            preparedStatement.setInt(8,liquidacionEmpleado.setIdEmpleado());
            preparedStatement.setInt(9,liquidacionEmpleado.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(LiquidacionEmpleado liquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM LIQUIDACION_EMPLEADO WHERE  idLiquidacionEmpleado=?");
            preparedStatement.setInt(1,liquidacionEmpleado.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<LiquidacionEmpleado> view(){
        ObservableList<LiquidacionEmpleado> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM LIQUIDACION_EMPLEADO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                LiquidacionEmpleado liquidacionEmpleado = new LiquidacionEmpleado();
                liquidacionEmpleado.setId(resultSet.getInt(1));
                liquidacionEmpleado.setFechaLiquidacion(resultSet.getString(2));
                liquidacionEmpleado.setImporteNeto(resultSet.getDouble(3));
                liquidacionEmpleado.setTotalHaberesRemunerativos(resultSet.getDouble(4));
                liquidacionEmpleado.setTotalHaberesNoRemunerativos(resultSet.getDouble(5));
                liquidacionEmpleado.setTotalRetenciones(resultSet.getDouble(6));
                liquidacionEmpleado.setTotalBruto(resultSet.getDouble(7));
//                liquidacionEmpleado.setIdPeriodoLiquidacion(resultSet.getInt(8));
//                liquidacionEmpleado.setIdEmpleado(resultSet.getInt(9));
                list.add(liquidacionEmpleado);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
