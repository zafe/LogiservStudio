package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
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
    
    public void save(Cliente cliente, int idDomicilio){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("INSERT INTO CLIENTE (Nombre, CUIT, DOMICILIO_idDomicilio) "
            		+ "VALUES (?,?,?)");
            preparedStatement.setString(1, cliente.getNombre());
            preparedStatement.setString(2,cliente.getCuit());
            preparedStatement.setInt(3, idDomicilio);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
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
    public void delete(Cliente cliente){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM CLIENTE WHERE idCliente=?");
            preparedStatement.setInt(1,cliente.getIdCliente());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Cliente> view(){
        ObservableList<Cliente> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM CLIENTE INNER JOIN DOMICILIO"
            		+ " WHERE idDomicilio = DOMICILIO_idDomicilio");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cliente cliente = new Cliente();
                cliente.setIdCliente(resultSet.getInt("idCliente"));
                cliente.setNombre(resultSet.getString("Nombre"));
                cliente.setCuit(resultSet.getString("CUIT"));
                cliente.setDomicilio(resultSet.getString("Calle") + " " + resultSet.getString("Numero"));
                list.add(cliente);

            }
            preparedStatement.close();
            resultSet.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void search(Cliente cliente){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM CLIENTE where idCliente=?");
            preparedStatement.setInt(1,cliente.getIdCliente());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
