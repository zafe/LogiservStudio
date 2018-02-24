package application.repository.compra;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.FacturaCompra;
import application.model.compra.Proveedor;
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
                    "INSERT INTO COMPRA_ARTICULO VALUES(?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,facturaCompra.getFecha());
            preparedStatement.setDouble(3, facturaCompra.getTotal());
            preparedStatement.setInt(4, facturaCompra.getProveedor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(FacturaCompra facturaCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("UPDATE COMPRA_ARTICULO " +
                    "SET fecha=?, PROVEEDOR_idPROVEEDOR=? " +
                    " WHERE idFacturaCompraArticulo=?");
            preparedStatement.setString(1, facturaCompra.getFecha());
            preparedStatement.setInt(2, facturaCompra.getProveedor().getId());
            preparedStatement.setInt(3, facturaCompra.getIdFacturaCompra());
            preparedStatement.executeUpdate();
            Alerta.alertaInfo("Factura Compra", "Factura Actualizada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int idFactura){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM COMPRA_ARTICULO WHERE idFacturaCompraArticulo=? ");
            preparedStatement.setInt(1,idFactura);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<FacturaCompra> view(){
        ObservableList<FacturaCompra> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement(
                    "SELECT f.idFacturaCompraArticulo, f.Fecha, f.total, p.idPROVEEDOR, p.Nombre, p.cuit " +
                    " from compra_articulo as f, proveedor as p " +
                    " where f.proveedor_idproveedor=p.idPROVEEDOR");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                FacturaCompra facturaCompra = new FacturaCompra(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        new Proveedor(resultSet.getInt(4),
                                resultSet.getString(5),
                                resultSet.getString(6),null));
                list.add(facturaCompra);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getLastID() {
        int lastIdFactura=0;
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT MAX(idFacturaCompraArticulo) FROM compra_articulo;");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next())
            lastIdFactura= resultSet.getInt(1);

            return lastIdFactura;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastIdFactura;
    }
//TODO: hacer un metodo para obtener facturas en un rango de fechas? o bien ¿utilizar un procedimiento?
}

