package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.Finca;

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
}
