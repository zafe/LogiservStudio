package application.repository.compra;

import application.database.JDBCConnection;
import application.model.compra.Articulo;
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
            preparedStatement = connection.prepareStatement("INSERT INTO DETALLE_COMPRA " +
                    "values(?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setInt(2,detalleCompra.getCantidad());
            preparedStatement.setDouble(3,detalleCompra.getPrecioUnitario());
            preparedStatement.setInt(4, detalleCompra.getArticulo().getIdArticulo());
            preparedStatement.setInt(5, detalleCompra.getFacturaCompra().getIdFacturaCompra());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(DetalleCompra detalleCompra){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("" +
                    "UPDATE DETALLE_COMPRA" +
                    "   SET cantidad=?, precioUnitario=?, articulo_idArticulo=?," +
                    "   WHERE idCompraArticulo=?");
            preparedStatement.setInt(1,detalleCompra.getCantidad());
            preparedStatement.setDouble(2,detalleCompra.getPrecioUnitario());
            preparedStatement.setInt(3,detalleCompra.getArticulo().getIdArticulo());
            preparedStatement.setInt(4, detalleCompra.getIdDetalleCompra());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int idFactura){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("DELETE FROM DETALLE_COMPRA  WHERE FacturaCompraArticulo_idFacturaCompraArticulo=?");
            preparedStatement.setInt(1,idFactura);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<DetalleCompra> view(int idFactura){
        ObservableList<DetalleCompra> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT l.idCompraArticulo, " +
                    "l.Cantidad, l.PrecioUnitario, l.Articulo_idArticulo, a.Descripcion " +
                    " from DETALLE_COMPRA as l, ARTICULO as a " +
                    " where FacturaCompraArticulo_idFacturaCompraArticulo=? AND l.Articulo_idArticulo=a.idArticulo;");
            preparedStatement.setInt(1,idFactura);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                DetalleCompra detalleCompra = new DetalleCompra(
                        resultSet.getInt(1), resultSet.getInt(2),
                        resultSet.getFloat(3),
                        new Articulo(resultSet.getInt(4), null,null,
                                resultSet.getString(5), null,0),
                        null
                );
                list.add(detalleCompra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
