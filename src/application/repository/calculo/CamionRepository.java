package application.repository.calculo;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.Camion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CamionRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(Camion camion){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO CAMION values(?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,camion.getMarca());
            preparedStatement.setString(3,camion.getModelo());
            preparedStatement.setString(4,camion.getPatente());
            preparedStatement.execute();
            String cuerpoMsj = "Camión: " + camion.getMarca() + " agregado correctamente.\n";
            Alerta.alertaInfo("Camiones",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Camion camion){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("" +
                    "UPDATE CAMION " +
                    "SET Marca=?, Modelo=?, Patente=? " +
                    "WHERE idCamion=?");
            preparedStatement.setString(1,camion.getMarca());
            preparedStatement.setString(2,camion.getModelo());
            preparedStatement.setString(3,camion.getPatente());
            preparedStatement.setInt(4,camion.getId());
            preparedStatement.execute();
            String headerMsj="Actualización: Camión actualizado";
            String cuerpoMsj = "Camión: " + camion.getMarca() + " modificado correctamente.";
            Alerta.alertaInfo("Camiones", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int idCamion){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM CAMION WHERE idCamion=?");
            preparedStatement.setInt(1, idCamion);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Camion> view(){
        ObservableList<Camion> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM CAMION");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Camion camion = new Camion();
                camion.setId(resultSet.getInt(1));
                camion.setMarca(resultSet.getString(2));
                camion.setModelo(resultSet.getString(3));
                camion.setPatente(resultSet.getString(4));
                list.add(camion);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void search(Camion camion){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM CAMION where idCamion=?");
            preparedStatement.setInt(1,camion.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
