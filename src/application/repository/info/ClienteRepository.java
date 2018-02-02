package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.info.Domicilio;
import application.model.venta.Cliente;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    
    public void save(Cliente cliente){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("INSERT INTO domicilio " +
                    "values (?, SELECT idLocalidad from localidad WHERE nombre=?, ?, ?); " +
                    "INSERT INTO cliente " +
                    "values (?, ?, ?, LAST_INSERT_ID())");
            preparedStatement.setString(1, null);
//            preparedStatement.setString(2, cliente.getDomicilio().getNombre_localidad());
            preparedStatement.setString(3, cliente.getDomicilio().getCalle());
            preparedStatement.setString(4, cliente.getDomicilio().getNumero());
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, cliente.getNombre());
            preparedStatement.setString(7, cliente.getCuit());
            preparedStatement.executeUpdate();

            String cuerpoMsj = "Cliente  " + cliente.getNombre() + " agregado correctamente.\n";
            Alerta.alertaInfo("Clientes",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Cliente cliente, int idDomicilio){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("" +
                    "UPDATE CLIENTE " +
                    "SET Nombre=?, CUIT=?, DOMICILIO_idDomicilio=?" +
                    "WHERE idEmpresa=?");
            preparedStatement.setString(1,cliente.getNombre());
            preparedStatement.setString(2,cliente.getCuit());
            preparedStatement.setInt(3,idDomicilio);
            preparedStatement.close();
            connection.close();
            String headerMsj="Actualización: cliente actualizado";
            String cuerpoMsj = "Cliente: " + cliente.getNombre() + "modificado correctamente.";
            Alerta.alertaInfo("Clientes", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int idCliente){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM CLIENTE WHERE idCliente=?");
            preparedStatement.setInt(1, idCliente);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Cliente> view(){
        ObservableList<Cliente> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT c.idCliente, c.nombre, c.cuit, d.calle, d.numero, l.NombreLocalidad " +
                    "FROM cliente AS c, domicilio AS d, localidad AS l " +
                    "WHERE c.DOMICILIO_idDomicilio = d.idDomicilio AND d.LOCALIDAD_idLocalidad = l.idLocalidad");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt(1));
                cliente.setNombre(resultSet.getString(2));
                cliente.setCuit(resultSet.getString(3));
                cliente.setDomicilio(new Domicilio());
                cliente.getDomicilio().setCalle(resultSet.getString(4));
                cliente.getDomicilio().setNumero(resultSet.getString(5));
//                cliente.getDomicilio().setNombre_localidad(resultSet.getString(6));
                list.add(cliente);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
