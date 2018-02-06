package application.repository.info;

import application.database.JDBCConnection;
import application.model.sueldo.ConceptoSueldo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConceptoSueldoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(ConceptoSueldo conceptoSueldo){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO CONCEPTO_SUELDO" +
                    " values(?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,conceptoSueldo.getDescripcion());
            preparedStatement.setFloat(3,conceptoSueldo.getCantidad());
            preparedStatement.setString(4, conceptoSueldo.getTipoConcepto());
            preparedStatement.setString(5, conceptoSueldo.getTipoCantidad());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(ConceptoSueldo conceptoSueldo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("" +
                    "UPDATE CONCEPTO_SUELDO" +
                    "   SET descripcion=?, cantidad=?, tipo_concepto=?," +
                    " tipo_cantidad=?" +
                    "WHERE idCodigoConcepto=?");
            preparedStatement.setString(1,conceptoSueldo.getDescripcion());
            preparedStatement.setDouble(2,conceptoSueldo.getCantidad());
            preparedStatement.setString(3,conceptoSueldo.getTipoConcepto());
            preparedStatement.setString(4,conceptoSueldo.getTipoCantidad());
            preparedStatement.setInt(5, conceptoSueldo.getIdConceptoSueldo());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(ConceptoSueldo conceptoSueldo){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("DELETE FROM CONCEPTO_SUELDO WHERE idCodigoConcepto=?");
            preparedStatement.setInt(1,conceptoSueldo.getIdConceptoSueldo());
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<ConceptoSueldo> view(){
        ObservableList<ConceptoSueldo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM concepto_Sueldo");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ConceptoSueldo conceptoSueldo = new ConceptoSueldo();
                conceptoSueldo.setIdConceptoSueldo(resultSet.getInt(1));
                conceptoSueldo.setDescripcion(resultSet.getString(2));
                conceptoSueldo.setCantidad(resultSet.getFloat(3));
                conceptoSueldo.setTipoConcepto(resultSet.getString(4));
                conceptoSueldo.setTipoCantidad(resultSet.getString(5));
                list.add(conceptoSueldo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<ConceptoSueldo> getConceptosByEmpleadoId(int idEmpleado){
        ObservableList<ConceptoSueldo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT\n" +
                    "CONCEPTO_SUELDO.idCodigoConcepto,\n" +
                    "CONCEPTO_SUELDO.descripcion,\n" +
                    "CONCEPTO_SUELDO.cantidad,\n" +
                    "CONCEPTO_SUELDO.tipo_concepto,\n" +
                    "        CONCEPTO_SUELDO.tipo_cantidad\n" +
                    "FROM \n" +
                    "CONCEPTO_SUELDO\n" +
                    "INNER JOIN \n" +
                    "TIPO_LIQUIDACION ON TIPO_LIQUIDACION.CONCEPTO_SUELDO_idCodigoConcepto = CONCEPTO_SUELDO.idCodigoConcepto\n" +
                    "INNER JOIN\n" +
                    "        CATEGORIA_EMPLEADO ON CATEGORIA_EMPLEADO.idCategoriaEmpleado = TIPO_LIQUIDACION.CATEGORIA_EMPLEADO_idCategoriaEmpleado\n" +
                    "INNER JOIN\n" +
                    "EMPLEADO ON EMPLEADO.CATEGORIA_EMPLEADO_idCategoriaEmpleado = CATEGORIA_EMPLEADO.idCategoriaEmpleado\n" +
                    "WHERE\n" +
                    "EMPLEADO.idEmpleado = ?;");
            preparedStatement.setInt(1, idEmpleado);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                ConceptoSueldo conceptoSueldo = new ConceptoSueldo();
                conceptoSueldo.setIdConceptoSueldo(resultSet.getInt(1));
                conceptoSueldo.setDescripcion(resultSet.getString(2));
                conceptoSueldo.setCantidad(resultSet.getFloat(3));
                conceptoSueldo.setTipoConcepto(resultSet.getString(4));
                conceptoSueldo.setTipoCantidad(resultSet.getString(5));
                list.add(conceptoSueldo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
