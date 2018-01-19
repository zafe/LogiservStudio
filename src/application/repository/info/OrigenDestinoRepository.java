package application.repository.info;

import application.database.JDBCConnection;
import application.model.calculo.OrigenDestino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrigenDestinoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public ObservableList<OrigenDestino> view(){
        ObservableList<OrigenDestino> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM ACOPLADO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrigenDestino origenDestino = new OrigenDestino();
                origenDestino.setIdOrigenDestino(resultSet.getInt(1));
                origenDestino.setDistanciaKM(resultSet.getFloat(2));
//                origenDestino.setIdFinca(resultSet.getInt(3));
//                origenDestino.setIdIngenio(resultSet.getInt(4));
                list.add(origenDestino);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
