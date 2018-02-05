package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.Ingenio;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class IngenioRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(Ingenio ingenio){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO Ingenio values(?,point(?,?),?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setDouble(2,ingenio.getLatitud());
            preparedStatement.setDouble(3,ingenio.getLongitud());
            preparedStatement.setString(4,ingenio.getNombre());
            preparedStatement.setDouble(5,ingenio.getArranque());
            preparedStatement.setDouble(6,ingenio.getTarifa());
            preparedStatement.execute();
            String cuerpoMsj = "Ingenio " + ingenio.getNombre()+ " agregado correctamente.\n";
            Alerta.alertaInfo("Ingenios",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Ingenio ingenio){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("" +
                    "UPDATE ingenio " +
                    "SET coordenada=point(?,?),Nombre=?, arranque=?, tarifa=?" +
                    "WHERE idIngenio=?");
            preparedStatement.setDouble(1, ingenio.getLatitud());
            preparedStatement.setDouble(2, ingenio.getLongitud());
            preparedStatement.setString(3,ingenio.getNombre());
            preparedStatement.setDouble(4,ingenio.getArranque());
            preparedStatement.setDouble(5, ingenio.getTarifa());
            preparedStatement.setInt(6, ingenio.getIdIngenio());
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: finca actualizada";
            String cuerpoMsj = "Finca: " + ingenio.getNombre() + " modificado correctamente.";
            Alerta.alertaInfo("Fincas", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int id){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM INGENIO WHERE idIngenio=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Ingenio> view(){
        ObservableList<Ingenio> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("select idIngenio, x(coordenada), y(coordenada), nombre, arranque, tarifa from ingenio");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Ingenio ingenio = new Ingenio();
                ingenio.setIdIngenio(resultSet.getInt(1));
                ingenio.setLatitud(resultSet.getDouble(2));
                ingenio.setLongitud(resultSet.getDouble(3));
                ingenio.setNombre(resultSet.getString(4));
                ingenio.setArranque(resultSet.getDouble(5));
                ingenio.setTarifa(resultSet.getDouble(6));
                list.add(ingenio);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> listOfIngenios(){
        ObservableList<String> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT nombre FROM ingenio");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                list.add(resultSet.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
