package application.repository.venta;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.model.venta.Cliente;
import application.repository.info.DomicilioRepository;
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

    DomicilioRepository domicilioRepository = new DomicilioRepository();

    public void save(Cliente cliente){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("INSERT INTO CLIENTE VALUES(?,?,?,LAST_INSERT_ID() )");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,cliente.getNombre());
            preparedStatement.setString(3,cliente.getCuit());
            preparedStatement.executeUpdate();

            String cuerpoMsj = "Cliente  " + cliente.getNombre() + " agregado correctamente.\n";
            Alerta.alertaInfo("Clientes",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Cliente cliente){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("UPDATE CLIENTE as p " +
                    "    INNER JOIN DOMICILIO as d ON p.DOMICILIO_idDomicilio = idDomicilio " +
                            "    SET p.NOMBRE = ?, p.CUIT=?, d.calle =?, d.numero=?, d.LOCALIDAD_idLocalidad=?" +
                            "    WHERE p.idCLIENTE = ?");
            preparedStatement.setString(1,cliente.getNombre());
            preparedStatement.setString(2,cliente.getCuit());
            preparedStatement.setString(3,cliente.getDomicilio().getCalle());
            preparedStatement.setString(4,cliente.getDomicilio().getNumero());
            preparedStatement.setInt(5, cliente.getDomicilio().getLocalidad().getIdLocalidad());
            preparedStatement.setInt(6,cliente.getIdCliente());
            preparedStatement.executeUpdate();
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
            preparedStatement=connection.prepareStatement("DELETE a1, a2 FROM CLIENTE AS a1 INNER JOIN domicilio AS a2\n" +
                            "WHERE a1.DOMICILIO_idDomicilio=a2.idDomicilio AND a1.idCliente=?");
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
                    "SELECT p.idCLIENTE, p.Nombre, p.CUIT, d.idDomicilio, "+
                            "                           l.idLocalidad, l.NombreLocalidad, d.Calle, d.Numero, c.* \n" +
                            "                  FROM CLIENTE AS p, DOMICILIO AS d, LOCALIDAD AS l, PROVINCIA AS c \n" +
                            "          WHERE p.DOMICILIO_idDomicilio=d.idDomicilio \n" +
                            "     AND d.LOCALIDAD_idLocalidad=l.idLocalidad " +
                            "  AND l.PROVINCIA_idProvincia=c.idProvincia");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cliente cliente = new Cliente(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        new Domicilio(resultSet.getInt(4),
                                new Localidad(resultSet.getInt(5), resultSet.getString(6),
                                        new Provincia(resultSet.getInt(9),resultSet.getString(10))),
                                resultSet.getString(7),
                                resultSet.getString(8)));
                list.add(cliente);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Cliente getClienteById(int idCliente){
        Cliente cliente = new Cliente();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CLIENTE WHERE idCLIENTE=?;");
            preparedStatement.setInt(1, idCliente);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                        cliente.setIdCliente(resultSet.getInt("idCLIENTE"));
                        cliente.setNombre(resultSet.getString("Nombre"));
                        cliente.setCuit(resultSet.getString("CUIT"));
                        Domicilio domicilio = domicilioRepository.getDomicilioById(resultSet.getInt("DOMICILIO_idDomicilio"));
                        cliente.setDomicilio(domicilio);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }
}
