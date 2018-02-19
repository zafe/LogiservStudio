package application.repository.venta;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.info.Domicilio;
import application.model.info.Empleado;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.model.venta.Cliente;
import application.model.venta.Organizacion;
import application.repository.info.DomicilioRepository;
import application.repository.info.EmpleadoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrganizacionRepository {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    DomicilioRepository domicilioRepository = new DomicilioRepository();
    EmpleadoRepository empleadoRepository = new EmpleadoRepository();

    public void save(Cliente cliente){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("INSERT INTO Cliente VALUES(?,?,?,LAST_INSERT_ID() )");
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

    public Organizacion getOrganizacionById(int idOrganizacion){
        Organizacion organizacion = new Organizacion();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT idORGANIZACION, Nombre, CUIT, APODERADO_idEmpleado, DOMICILIO_idDomicilio" +
                            " FROM ORGANIZACION WHERE idORGANIZACION=?;");
            preparedStatement.setInt(1, idOrganizacion);
            System.out.println("SQL QUERY: " + preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                organizacion.setIdOrganizacion(resultSet.getInt("idORGANIZACION"));
                organizacion.setNombreOrg(resultSet.getString("Nombre"));
                organizacion.setCuitOrg(resultSet.getString("CUIT"));

                Empleado apoderado = empleadoRepository.getEmpleadoById(resultSet.getInt("APODERADO_idEmpleado"));
                organizacion.setApoderadoOrg(apoderado);

                Domicilio domicilio = domicilioRepository.getDomicilioById(resultSet.getInt("DOMICILIO_idDomicilio"));
                organizacion.setDomicilioOrg(domicilio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organizacion;
    }
}
