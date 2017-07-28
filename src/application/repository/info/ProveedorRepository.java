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
            preparedStatement= connection.prepareStatement("INSERT INTO EMPRESA VALUES(?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2, proveedor.getNombre());
            preparedStatement.setString(3,proveedor.getCuit());
//            preparedStatement.setInt(4,proveedor.getIdDomicilio());
//            preparedStatement.setString(5,proveedor.getTipoEmpresa());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            //TODO CREO QUE LA DB CONNECTION NO DEBERIA CERRARSE PORQUE SI SE CIERRA NO PUEDE SER USADA MAS
            connection.close();
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
                    "UPDATE EMPRESA " +
                    "SET nombre=?, cuit=?, Domicilio_idDomicilio=?" +
                    "WHERE idEmpresa=?");
            preparedStatement.setString(1,proveedor.getNombre());
            preparedStatement.setString(2,proveedor.getCuit());
//            preparedStatement.setString(3,proveedor.getIdDomicilio());
            preparedStatement.setInt(4,proveedor.getIdProveedor());
            preparedStatement.close();
            connection.close();
            String headerMsj="Actualización: proveedor actualizado";
            String cuerpoMsj = "Proveedor: " + proveedor.getNombre() + "modificado correctamente.";
            Alerta.alertaInfo("Proveedores", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Proveedor proveedor){
        try {
            connection=JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM EMPRESA WHERE idEmpresa=?");
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
            preparedStatement = connection.prepareStatement("SELECT * FROM EMPRESA");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Proveedor proveedor = new Proveedor();
                proveedor.setIdProveedor(resultSet.getInt(1));
                proveedor.setNombre(resultSet.getString(2));
                proveedor.setCuit(resultSet.getString(3));
//                proveedor.setIdDomicilio(resultSet.getString(4));
//                proveedor.setTipoEmpresa(resultSet.getString(5));
                list.add(proveedor);

            }
            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void search(Proveedor proveedor){
        try {
            connection=JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM EMPRESA where idEmpresa=?");
            preparedStatement.setInt(1,proveedor.getIdProveedor());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
