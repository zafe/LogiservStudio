package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.FacturaCompra;
import application.model.compra.Proveedor;
import com.sun.org.apache.regexp.internal.RE;
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
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(FacturaCompra facturaCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("UPDATE COMPRA_ARTICULO" +
                    "SET fecha=?, empresa_idEmpresa=?, total=?" +
                    "WHERE idFacturaCompraArticulo=?");
            preparedStatement.setString(1, facturaCompra.getFecha());
//            preparedStatement.setInt(2, facturaCompra.getProveedorId());
            preparedStatement.setDouble(3,facturaCompra.getTotal());
            preparedStatement.setInt(4, facturaCompra.getIdFacturaCompra());
            Alerta.alertaInfo("Factura Compra", "Factura Actualizada correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(FacturaCompra facturaCompra){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM COMPRA_ARTICULO where idFacturaCompraArticulo=?");
            preparedStatement.setInt(1,facturaCompra.getIdFacturaCompra());
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

