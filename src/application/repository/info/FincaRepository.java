package application.repository.info;

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
            preparedStatement = connection.prepareStatement("INSERT INTO FINCA values(?,?,?,?)");
            preparedStatement.setString(1,null);
//            preparedStatement.setInt(2,finca.getIdEmpresa());
            preparedStatement.setString(3,"POINT('"+finca.getLatitud()+" "+finca.getLongitud()+"')");
            preparedStatement.setString(4,finca.getNombre());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
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
                    "SET Empresa_idDueño=?, Coordenada=?, Nombre=?, " +
                    "WHERE idFinca=?");
//            preparedStatement.setString(1,finca.getIdEmpresa());
            preparedStatement.setString(2,"POINT('"+finca.getLatitud()+" "+finca.getLongitud()+"')");
            preparedStatement.setString(3,finca.getNombre());
            preparedStatement.setInt(4,finca.getIdFinca());
            preparedStatement.close();
            connection.close();
            String headerMsj="Actualización: finca actualizada";
            String cuerpoMsj = "Finca: " + finca.getNombre() + " modificado correctamente.";
            Alerta.alertaInfo("Fincas", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(Finca finca){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM FINCA WHERE idFinca=?");
            preparedStatement.setInt(1, finca.getIdFinca());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Finca> view(){

        ObservableList<Finca> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM FINCA");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Finca finca = new Finca();
                finca.setIdFinca(resultSet.getInt(1));
//                finca.setIdEmpresa(resultSet.getInt(2));
                finca.setLatitud(resultSet.getDouble(3));
                finca.setLongitud(resultSet.getDouble(3));
                finca.setNombre(resultSet.getString(4));
                list.add(finca);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public void search(Finca finca){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM FINCA where idFinca=?");
            preparedStatement.setInt(1,finca.getIdFinca());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
