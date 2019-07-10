package application.repository.calculo;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.Finca;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FincaRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(Finca finca){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO FINCA values(?,point(?,?),?)");
            preparedStatement.setString(1,null);
            preparedStatement.setDouble(2,finca.getLatitud());
            preparedStatement.setDouble(3,finca.getLongitud());
            preparedStatement.setString(4,finca.getNombre());
            preparedStatement.execute();
            String cuerpoMsj = "Finca " + finca.getNombre()+ " agregada correctamente.\n";
            Alerta.alertaInfo("Fincas",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void update(Finca finca){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("" +
                    "UPDATE FINCA " +
                    "SET  coordenada=point(?,?), Nombre=? " +
                    "WHERE idFinca=?");
            preparedStatement.setDouble(1,finca.getLatitud());
            preparedStatement.setDouble(2,finca.getLongitud());
            preparedStatement.setString(3,finca.getNombre());
            preparedStatement.setInt(4,finca.getIdFinca());
            preparedStatement.execute();
            String headerMsj="Actualización: finca actualizada";
            String cuerpoMsj = "Finca: " + finca.getNombre() + " modificado correctamente.";
            Alerta.alertaInfo("Fincas", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(int id){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM FINCA WHERE idFinca=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Finca> view(){
        ObservableList<Finca> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT idFinca,ST_X(coordenada), ST_Y(coordenada),nombre FROM FINCA");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Finca finca = new Finca();
                finca.setIdFinca(resultSet.getInt(1));
                finca.setLatitud(resultSet.getDouble(2));
                finca.setLongitud(resultSet.getDouble(3));
                finca.setNombre(resultSet.getString(4));
                list.add(finca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public ObservableList<String> listOfFincas(){
                ObservableList<String> list = FXCollections.observableArrayList();
                try {
                        connection= JDBCConnection.getInstanceConnection();
                        preparedStatement=connection.prepareStatement("SELECT nombre FROM FINCA");
                        resultSet = preparedStatement.executeQuery();
                        while (resultSet.next()){
                                list.add(resultSet.getString(1));
                            }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                return list;
            }

    public Finca getFincaById(int idFinca){
        Finca finca = new Finca();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT idFinca,ST_X(coordenada), ST_Y(coordenada),nombre FROM FINCA WHERE idFinca=?");
            preparedStatement.setInt(1,idFinca);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                finca.setIdFinca(resultSet.getInt(1));
                finca.setLongitud(resultSet.getDouble(2));
                finca.setLatitud(resultSet.getDouble(3));
                finca.setNombre(resultSet.getString(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return finca;

    }

}
