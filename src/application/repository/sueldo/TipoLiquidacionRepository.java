package application.repository.sueldo;

import application.database.JDBCConnection;
import application.model.info.CategoriaEmpleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TipoLiquidacionRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(int idCategoria, int idConceptoSueldo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO tipo_liquidacion values(?,?)");
            preparedStatement.setInt(1,idCategoria);
            preparedStatement.setInt(2,idConceptoSueldo);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<CategoriaEmpleado> getCategoriasByConcepto(int idConceptoSueldo){
        ObservableList<CategoriaEmpleado> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT t.CATEGORIA_EMPLEADO_idCategoriaEmpleado, " +
                    "c.NombreCategoria from tipo_liquidacion AS t, categoria_empleado AS c " +
                    "where CONCEPTO_SUELDO_idCodigoConcepto = ? AND c.idCategoriaEmpleado=t.CATEGORIA_EMPLEADO_idCategoriaEmpleado;");
            preparedStatement.setInt(1, idConceptoSueldo);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CategoriaEmpleado categoriaEmpleado = new CategoriaEmpleado(resultSet.getInt(1), resultSet.getString(2));
                list.add(categoriaEmpleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
    public void delete(int idConceptoSueldo){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("DELETE FROM tipo_liquidacion WHERE CONCEPTO_SUELDO_idCodigoConcepto=?");
            preparedStatement.setInt(1,idConceptoSueldo);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
