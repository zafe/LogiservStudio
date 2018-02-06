package application.repository.sueldo;

import application.database.JDBCConnection;
import application.model.sueldo.ReciboSueldo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReciboSueldoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(ReciboSueldo reciboSueldo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("INSERT INTO RECIBO_SUELDO values (?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,reciboSueldo.getFecha());
            preparedStatement.setDouble(3,reciboSueldo.getTotalNeto());
//            preparedStatement.setInt(4,reciboSueldo.getIdLiquidacionEmpleado());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(ReciboSueldo reciboSueldo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("UPDATE RECIBO_SUELDO" +
                    "SET fecha=?, total_neto=?, LiquidacionEmpleado_idLiquidacionEmpleado=?" +
                    "WHERE idRECIBO_SUELDO=?");
            preparedStatement.setString(1,reciboSueldo.getFecha());
            preparedStatement.setDouble(2,reciboSueldo.getTotalNeto());
//            preparedStatement.setInt(3,reciboSueldo.getIdLiquidacionEmpleado());
            preparedStatement.setInt(4, reciboSueldo.getIdRecibo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(ReciboSueldo reciboSueldo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM RECIBO_SUELDO WHERE idRECIBO_SUELDO=?");
            preparedStatement.setInt(1,reciboSueldo.getIdRecibo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<ReciboSueldo> view(){
        ObservableList<ReciboSueldo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM RECIBO_SUELDO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ReciboSueldo reciboSueldo = new ReciboSueldo();
                reciboSueldo.setIdRecibo(resultSet.getInt(1));
                reciboSueldo.setFecha(resultSet.getString(2));
                reciboSueldo.setTotalNeto(resultSet.getDouble(3));
//                reciboSueldo.setIdLiquidacionEmpleado(resultSet.getInt(4));
                list.add(reciboSueldo);
            }
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void search(ReciboSueldo reciboSueldo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM RECIBO_SUELDO WHERE idRECIBO_SUELDO=?");
            preparedStatement.setInt(1,reciboSueldo.getIdRecibo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

