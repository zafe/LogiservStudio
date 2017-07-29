package application.repository.info;

import application.database.JDBCConnection;
import application.model.compra.DetalleCompra;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DetalleCompraRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(DetalleCompra detalleCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO COMPRA_ARTICULO" +
                    "values(?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setInt(2,detalleCompra.getCantidad());
            preparedStatement.setDouble(3,detalleCompra.getPrecioUnitario());
            preparedStatement.setInt(4, detalleCompra.getArticuloID());
            preparedStatement.setInt(5, detalleCompra.getFacturaCompraArticuloId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void update(DetalleCompra detalleCompra){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("" +
                    "UPDATE COMPRA_ARTICULO" +
                    "   SET cantidad=?, precioUnitario=?, articulo_idArticulo=?," +
                    " facturaCompraArticulo_idFacturaCompraArticulo=?");
            preparedStatement.setInt(1,detalleCompra.getCantidad());
            preparedStatement.setDouble(2,detalleCompra.getPrecioUnitario());
            preparedStatement.setInt(3,detalleCompra.getArticuloID());
            preparedStatement.setInt(4,detalleCompra.getFacturaCompraArticuloId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(DetalleCompra detalleCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("DELETE FROM COMPRA_ARTICULO WHERE idCompraArticulo=?");
            preparedStatement.setInt(1,detalleCompra.getId());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<DetalleCompra> view(){
        ObservableList<DetalleCompra> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM COMPRA_ARTICULO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                DetalleCompra detalleCompra = new DetalleCompra();
                detalleCompra.setIdDetalleCompra(resultSet.getInt(1));
                detalleCompra.setCantidad(resultSet.getInt(2));
                detalleCompra.setPrecioUnitario((float) resultSet.getDouble(3));
                detalleCompra.setArticuloID(resultSet.getInt(4));
                detalleCompra.setFacturaCompraArticuloId(resultSet.getInt(5));
                list.add(detalleCompra);
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
