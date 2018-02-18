package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.info.Familiar;
import com.sun.org.apache.bcel.internal.generic.FADD;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FamiliarRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(Familiar familiar){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("" +
                    "INSERT INTO FAMILIAR VALUES(?,?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,familiar.getParentesco());
            preparedStatement.setString(3, familiar.getNombre());
            preparedStatement.setString(4, familiar.getApellido());
            preparedStatement.setString(5, familiar.getFechaNacimiento());
            preparedStatement.setInt(6, familiar.getEmpleado().getIdEmpleado());
            preparedStatement.executeUpdate();
            String cuerpoMsj = "Familiar agregado correctamente.\n";
            Alerta.alertaInfo("Grupo Familiar",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void update(Familiar familiar){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("UPDATE FAMILIAR " +
                    "SET parentesco=?, Nombre=?, Apellido=?, FechaNacimiento=? " +
                    "WHERE idFAMILIAR=?");
            preparedStatement.setString(1, familiar.getParentesco());
            preparedStatement.setString(2,familiar.getNombre());
            preparedStatement.setString(3, familiar.getApellido());
            preparedStatement.setString(4, familiar.getFechaNacimiento());
            preparedStatement.setInt(5, familiar.getIdFAMILIAR());
            Alerta.alertaInfo("Grupo Familiar", "Familiar actualizado correctamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<Familiar> view(int idEmpleado) {
        ObservableList<Familiar> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement(
                    "SELECT * from FAMILIAR where EMPLEADO_idEmpleado=?");
            preparedStatement.setInt(1,idEmpleado);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Familiar familiar = new Familiar();
                familiar.setIdFAMILIAR(resultSet.getInt(1));
                familiar.setParentesco(resultSet.getString(2));
                familiar.setNombre(resultSet.getString(3));
                familiar.setApellido(resultSet.getString(4));
                familiar.setFechaNacimiento(resultSet.getString(5));
                list.add(familiar);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void delete(int idFamiliar) {
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("" +
                    "DELETE FROM FAMILIAR " +
                    "WHERE idFAMILIAR=?");
            preparedStatement.setInt(1,idFamiliar);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
