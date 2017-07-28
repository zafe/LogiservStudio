package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.FacturaCompra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FacturaCompraRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(FacturaCompra facturaCompra){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO FACTURA_COMPRA_ARTICULO VALUES(?,?,?,?)");//TODO: ver nombre tabla en BD
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,facturaCompra.getFecha());//TODO: revisar esto
//            preparedStatement.setInt(3,facturaCompra.getProveedorId());
            preparedStatement.setDouble(4, facturaCompra.getTotal());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(FacturaCompra facturaCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("UPDATE FACTURA_COMPRA_ARTICULO" +
                    "SET fecha=?, empresa_idEmpresa=?, total=?" +
                    "WHERE idFacturaCompraArticulo=?");
            preparedStatement.setString(1, facturaCompra.getFecha());
//            preparedStatement.setInt(2, facturaCompra.getProveedorId());
            preparedStatement.setDouble(3,facturaCompra.getTotal());
            preparedStatement.setInt(4, facturaCompra.getIdFacturaCompra());
            preparedStatement.close();
            connection.close();
            Alerta.alertaInfo("Factura Compra", "Factura Actualizada correctamente");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(FacturaCompra facturaCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM FACTURA_COMPRA_ARTICULO where idFacturaCompraArticulo=?");
            preparedStatement.setInt(1,facturaCompra.getIdFacturaCompra());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<FacturaCompra> view(){
        ObservableList<FacturaCompra> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM FACTURA_COMPRA_ARTICULO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FacturaCompra facturaCompra = new FacturaCompra();
                facturaCompra.setIdFacturaCompra(resultSet.getInt(1));
                facturaCompra.setFecha(resultSet.getString(2));
//                facturaCompra.setProveedorId(resultSet.getInt(3));
                facturaCompra.setTotal(resultSet.getDouble(4));
                list.add(facturaCompra);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
//TODO: hacer un metodo para obtener facturas en un rango de fechas? o bien ¿utilizar un procedimiento?
}

