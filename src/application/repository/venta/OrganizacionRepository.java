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
    public void update(Organizacion organizacion){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("UPDATE Organizacion as p " +
                    "    INNER JOIN DOMICILIO as d ON p.DOMICILIO_idDomicilio = idDomicilio " +
                    "    SET p.NOMBRE = ?, p.CUIT=?, p.APODERADO_idEmpleado=?, d.calle =?, d.numero=?, d.LOCALIDAD_idLocalidad=?" +
                    "    WHERE p.idORGANIZACION = ?");
            preparedStatement.setString(1,organizacion.getNombreOrg());
            preparedStatement.setString(2,organizacion.getCuitOrg());
//            preparedStatement.setString(3, organizacion.getRazonSocial());
            preparedStatement.setInt(3, organizacion.getApoderadoOrg().getIdEmpleado());
            preparedStatement.setString(4,organizacion.getDomicilioOrg().getCalle());
            preparedStatement.setString(5, organizacion.getDomicilioOrg().getNumero());
            preparedStatement.setInt(6, organizacion.getDomicilioOrg().getLocalidad().getIdLocalidad());
//            preparedStatement.setString(4, organizacion.getTelefono());
            preparedStatement.setInt(7, organizacion.getIdOrganizacion());
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: datos de la organizacion actualizados";
            String cuerpoMsj = "Organizacion: " + organizacion.getNombreOrg() + " modificado correctamente.";
            Alerta.alertaInfo("Organización", headerMsj, cuerpoMsj);
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
    public ObservableList<Organizacion> view(){
        ObservableList<Organizacion> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT idORGANIZACION, Nombre, CUIT, APODERADO_idEmpleado, DOMICILIO_idDomicilio " +
                            "FROM ORGANIZACION;");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){;
                Organizacion organizacion = new Organizacion();
                organizacion.setIdOrganizacion(resultSet.getInt("idORGANIZACION"));
                organizacion.setNombreOrg(resultSet.getString("Nombre"));
                Empleado apoderado = empleadoRepository.buscarEmpleadoById(resultSet.getInt("APODERADO_idEmpleado"));
                organizacion.setApoderadoOrg(apoderado);
                Domicilio domicilio = domicilioRepository.getDomicilioById(resultSet.getInt("DOMICILIO_idDomicilio"));
                organizacion.setDomicilioOrg(domicilio);
                list.add(organizacion);
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

//                Empleado apoderado = empleadoRepository.getEmpleadoById(resultSet.getInt("APODERADO_idEmpleado"));
//                organizacion.setApoderadoOrg(apoderado);

                Domicilio domicilio = domicilioRepository.getDomicilioById(resultSet.getInt("DOMICILIO_idDomicilio"));
                organizacion.setDomicilioOrg(domicilio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organizacion;
    }
    public Organizacion get(){
        Organizacion organizacion = new Organizacion();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT p.idOrganizacion, p.Nombre, p.CUIT, p.APODERADO_idEmpleado, e.nombre, e.apellido,\n" +
                            " d.idDomicilio, l.idLocalidad, l.NombreLocalidad, d.Calle, d.Numero, c.* \n" +
                            "    FROM ORGANIZACION AS p,EMPLEADO AS e, DOMICILIO AS d, LOCALIDAD AS l, PROVINCIA AS c \n" +
                            "        WHERE p.DOMICILIO_idDomicilio=d.idDomicilio\n" +
                            "\t\t\tAND e.idEmpleado = p.APODERADO_idEmpleado \n" +
                            "           AND d.LOCALIDAD_idLocalidad=l.idLocalidad\n" +
                            "           AND l.PROVINCIA_idProvincia=c.idProvincia");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                organizacion = new Organizacion(    //id, nombre, cuit, apoderado, domicilio
                        resultSet.getInt(1), //id
                        resultSet.getString(2),//nombre
                        resultSet.getString(3),//cuit
                        new Empleado(resultSet.getInt(4),
                                resultSet.getString(5),
                                resultSet.getString(6),
                                null,null,null,
                                null,null,null),

                        new Domicilio(resultSet.getInt(7),
                                new Localidad(resultSet.getInt(8), resultSet.getString(9),
                                        new Provincia(resultSet.getInt(12),resultSet.getString(13))),
                                resultSet.getString(10),
                                resultSet.getString(11)));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organizacion;
    }
}
