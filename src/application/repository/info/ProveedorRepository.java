package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.Proveedor;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
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
            preparedStatement= connection.prepareStatement("INSERT INTO proveedor VALUES(?,?,?,LAST_INSERT_ID() )");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,proveedor.getNombre());
            preparedStatement.setString(3,proveedor.getCuit());
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
            preparedStatement=connection.prepareStatement(" UPDATE PROVEEDOR as p " +
                    "    INNER JOIN DOMICILIO as d ON p.DOMICILIO_idDomicilio = idDomicilio " +
                    "    SET p.NOMBRE = ?, p.CUIT=?, d.calle =?, d.numero=?, d.LOCALIDAD_idLocalidad=?" +
                    "    WHERE p.IDPROVEEDOR = ?");
            preparedStatement.setString(1,proveedor.getNombre());
            preparedStatement.setString(2,proveedor.getCuit());
            preparedStatement.setString(3,proveedor.getDomicilio().getCalle());
            preparedStatement.setString(4,proveedor.getDomicilio().getNumero());
            preparedStatement.setInt(5, proveedor.getDomicilio().getLocalidad().getIdLocalidad());
            preparedStatement.setInt(6,proveedor.getId());
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: proveedor actualizado";
            String cuerpoMsj = "Proveedor: " + proveedor.getNombre() + " modificado correctamente.";
            Alerta.alertaInfo("Proveedores", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Proveedor proveedor){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE a1, a2 FROM Proveedor AS a1 INNER JOIN domicilio AS a2\n" +
                    "WHERE a1.DOMICILIO_idDomicilio=a2.idDomicilio AND a1.idProveedor=?");
            preparedStatement.setInt(1,proveedor.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Proveedor> view(){
        ObservableList<Proveedor> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT p.idPROVEEDOR, p.Nombre, p.CUIT, d.idDomicilio,\n" +
                            "  l.idLocalidad, l.NombreLocalidad, d.Calle, d.Numero, c.* \n" +
                    "FROM PROVEEDOR AS p, DOMICILIO AS d, LOCALIDAD AS l, PROVINCIA AS c \n" +
                    "        WHERE p.DOMICILIO_idDomicilio=d.idDomicilio \n" +
                    "AND d.LOCALIDAD_idLocalidad=l.idLocalidad \n" +
                    "AND l.PROVINCIA_idProvincia=c.idProvincia");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Proveedor proveedor = new Proveedor(
                   resultSet.getInt(1),
                   resultSet.getString(2),
                   resultSet.getString(3),
                   new Domicilio(resultSet.getInt(4),
                      new Localidad(resultSet.getInt(5), resultSet.getString(6),
                          new Provincia(resultSet.getInt(9),resultSet.getString(10))),
                      resultSet.getString(7),
                      resultSet.getString(8)));
                list.add(proveedor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
