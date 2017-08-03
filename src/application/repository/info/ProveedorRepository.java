package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.Proveedor;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProveedorRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(Proveedor proveedor){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("INSERT INTO proveedor VALUES(?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,proveedor.getNombre());
            preparedStatement.setString(3,proveedor.getCuit());
            preparedStatement.setString(4,"LAST_INSERT_ID()");
            preparedStatement.executeUpdate();
            String cuerpoMsj = "Proveedor  " + proveedor.getNombre() + " agregado correctamente.\n";
            Alerta.alertaInfo("Proveedores",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Proveedor proveedor){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("" +
                    "UPDATE proveedor " +
                    "SET nombre=?, cuit=?, Domicilio_idDomicilio=?" +
                    "WHERE idPROVEEDOR=?");
            preparedStatement.setString(1,proveedor.getNombre());
            preparedStatement.setString(2,proveedor.getCuit());
//            preparedStatement.setString(3,proveedor.getIdDomicilio());
            preparedStatement.setInt(4,proveedor.getIdProveedor());

            String headerMsj="Actualización: proveedor actualizado";
            String cuerpoMsj = "Proveedor: " + proveedor.getNombre() + "modificado correctamente.";
            Alerta.alertaInfo("Proveedores", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Proveedor proveedor){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM proveedor WHERE idPROVEEDOR=?");
            preparedStatement.setInt(1,proveedor.getIdProveedor());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Proveedor> view(){
        ObservableList<Proveedor> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT p.idPROVEEDOR, p.Nombre, p.CUIT, d.Calle, d.Numero, l.NombreLocalidad " +
                            "FROM PROVEEDOR p, DOMICILIO d, LOCALIDAD l " +
                            "WHERE p.DOMICILIO_idDomicilio = d.idDomicilio " +
                            "AND d.LOCALIDAD_idLocalidad = l.idLocalidad ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(resultSet.getInt(1));
                proveedor.setNombre(resultSet.getString(2));
                proveedor.setCuit(resultSet.getString(3));
                proveedor.setCalle(resultSet.getString(4));
                proveedor.setNumero(resultSet.getString(5));
                proveedor.setLocalidad(resultSet.getString(6));
                list.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void search(Proveedor proveedor){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM proveedor where idPROVEEDOR=?");
            preparedStatement.setInt(1,proveedor.getIdProveedor());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
