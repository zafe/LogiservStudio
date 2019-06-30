package application.repository.calculo;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.Acoplado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AcopladoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(Acoplado acoplado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO ACOPLADO values(?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2, acoplado.getMarca());
            preparedStatement.setString(3, acoplado.getPatente());
            preparedStatement.setString(4, acoplado.getChasisNumero());
            preparedStatement.executeUpdate();
            String cuerpoMsj = "Acoplado \n Marca: " + acoplado.getMarca() + "\nPatente: "+acoplado.getPatente() +"\nagregado correctamente.\n";
            Alerta.alertaInfo("Acoplados",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public void update(Acoplado acoplado){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("UPDATE ACOPLADO " +
                    "SET Marca=?, Patente=?, ChasisNumero=?" +
                    "WHERE idAcoplado=?");
            preparedStatement.setString(1, acoplado.getMarca());
            preparedStatement.setString(2, acoplado.getPatente());
            preparedStatement.setString(3, acoplado.getChasisNumero());
            preparedStatement.setInt(4, acoplado.getId());
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: Acoplado actualizado";
            String cuerpoMsj = "Acoplado: \n\tMarca: " + acoplado.getMarca() + "\n\tPatente: " +
                    acoplado.getPatente()+  "modificado correctamente.";
            Alerta.alertaInfo("Acoplados", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(Acoplado acoplado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM ACOPLADO WHERE idAcoplado=?");
            preparedStatement.setInt(1, acoplado.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Acoplado> view(){
        ObservableList<Acoplado> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM ACOPLADO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Acoplado acoplado = new Acoplado();
                acoplado.setId(resultSet.getInt(1));
                acoplado.setMarca(resultSet.getString(2));
                acoplado.setPatente(resultSet.getString(3));
                acoplado.setChasisNumero(resultSet.getString(4));
                list.add(acoplado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }


    public Acoplado getAcopladoById(int idAcoplado){
        Acoplado acoplado = new Acoplado();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM ACOPLADO where idAcoplado=?");
            preparedStatement.setInt(1,idAcoplado);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                acoplado.setId(resultSet.getInt("idAcoplado"));
                acoplado.setMarca(resultSet.getString("Marca"));
                acoplado.setPatente(resultSet.getString("Patente"));
                acoplado.setChasisNumero(resultSet.getString("ChasisNumero"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acoplado;
    }


}