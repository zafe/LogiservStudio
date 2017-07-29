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
            preparedStatement = connection.prepareStatement("INSERT INTO Ingenio values(?,?,?,?)");
            preparedStatement.setString(1,null);
//            preparedStatement.setInt(2,finca.getIdEmpresa());
            preparedStatement.setString(3,"POINT('"+ingenio.getLatitud()+" "+ingenio.getLongitud()+"')");
            preparedStatement.setString(4,ingenio.getNombre());

            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
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
                    "SET Empresa_idDueño=?, Coordenada=?, Nombre=?, " +
                    "WHERE idFinca=?");
//            preparedStatement.setString(1,finca.getIdEmpresa());
            preparedStatement.setString(2,"POINT('"+ingenio.getLatitud()+" "+ingenio.getLongitud()+"')");
            preparedStatement.setString(3,ingenio.getNombre());
            preparedStatement.setInt(4,ingenio.getIdIngenio());
            preparedStatement.close();
            connection.close();
            String headerMsj="Actualización: finca actualizada";
            String cuerpoMsj = "Finca: " + ingenio.getNombre() + " modificado correctamente.";
            Alerta.alertaInfo("Fincas", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Ingenio ingenio){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM INGENIO WHERE idIngenio=?");
            preparedStatement.setInt(1, ingenio.getIdIngenio());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Ingenio> view(){
        ObservableList<Ingenio> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM FINCA");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Ingenio ingenio = new Ingenio();
                ingenio.setIdIngenio(resultSet.getInt(1));
//                ingenio.setIdEmpresa(resultSet.getInt(2));
                ingenio.setLatitud(resultSet.getDouble(3));
                ingenio.setLongitud(resultSet.getDouble(3));
                ingenio.setNombre(resultSet.getString(4));
                list.add(ingenio);
            }
            preparedStatement.close();
            resultSet.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    public void search(Ingenio ingenio){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM INGENIO where idIngenio=?");
            preparedStatement.setInt(1,ingenio.getIdIngenio());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
