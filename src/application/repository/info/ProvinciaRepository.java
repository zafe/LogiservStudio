	package application.repository.info;

	import application.comunes.Alerta;
	import application.database.JDBCConnection;
	import application.model.info.Localidad;
import application.model.info.Provincia;
import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class ProvinciaRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(Provincia provincia){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO PROVINCIA (PROVINCIA_idProvincia) values(?)");
	            preparedStatement.setString(1,provincia.getNombre());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            String cuerpoMsj = "Provincia " + provincia.getNombre()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Provincias",cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public void update(Provincia provincia){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement= connection.prepareStatement("" +
	                    "UPDATE PROVINCIA " +
	                    "SET NombreProvincia=? " +
	                    "WHERE idProvincia=?");
	            preparedStatement.setString(1,provincia.getNombre());
	            preparedStatement.setInt(2,provincia.getIdProvincia());
	            preparedStatement.close();
	            connection.close();
	            String headerMsj="Actualización: Provincia actualizada";
	            String cuerpoMsj = "Provincia: " + provincia.getNombre() + " modificado correctamente.";
	            Alerta.alertaInfo("Provincia", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void delete(Provincia provincia){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM PROVINCIA WHERE idProvincia=?");
	            preparedStatement.setInt(1, provincia.getIdProvincia());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<Provincia> view(){

	        ObservableList<Provincia> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM PROVINCIA");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                Provincia provincia = new Provincia();
	                provincia.setIdProvincia(resultSet.getInt("idProvincia"));
	                provincia.setNombre(resultSet.getString("NombreProvincia"));
	                list.add(provincia);
	            }
	            preparedStatement.close();
	            resultSet.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    public void search(Provincia provincia){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM PROVINCIA where idProvincia=?");
	            preparedStatement.setInt(1,provincia.getIdProvincia());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    }
	}