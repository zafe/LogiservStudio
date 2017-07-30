	package application.repository.info;

	import application.comunes.Alerta;
	import application.database.JDBCConnection;
	import application.model.info.Localidad;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	public class LocalidadRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(Localidad localidad, int idProvincia){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO LOCALIDAD (NombreLocalidad, PROVINCIA_idProvincia) values(?,?)");
	            preparedStatement.setString(1,localidad.getNombre());
	            preparedStatement.setInt(2,idProvincia);
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            String cuerpoMsj = "Localidad " + localidad.getNombre()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Localidad",cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public void update(Localidad localidad, int idProvincia){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement= connection.prepareStatement("" +
	                    "UPDATE LOCALIDAD " +
	                    "SET NombreLocalidad=?, PROVINCIA_idProvincia=? " +
	                    "WHERE idFinca=?");
	            preparedStatement.setString(1,localidad.getNombre());
	            preparedStatement.setInt(2,idProvincia);
	            preparedStatement.setInt(3,localidad.getIdLocalidad());
	            preparedStatement.close();
	            connection.close();
	            String headerMsj="Actualización: localidad actualizada";
	            String cuerpoMsj = "Localidad: " + localidad.getNombre() + " modificado correctamente.";
	            Alerta.alertaInfo("Localidad", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void delete(Localidad localidad){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM LOCALIDAD WHERE idLocalidad=?");
	            preparedStatement.setInt(1, localidad.getIdLocalidad());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<Localidad> view(){

	        ObservableList<Localidad> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM LOCALIDAD INNER JOIN PROVINCIA WHERE idProvincia = PROVINCIA_idProvincia");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                Localidad localidad = new Localidad();
	                localidad.setIdLocalidad(resultSet.getInt("idLocalidad"));
	                localidad.setNombre(resultSet.getString("NombreLocalidad"));
	                localidad.setProvincia(resultSet.getString("NombreProvincia"));
	                list.add(localidad);
	            }
	            preparedStatement.close();
	            resultSet.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    public void search(Localidad localidad){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM LOCALIDAD where idLocalidad=?");
	            preparedStatement.setInt(1,localidad.getIdLocalidad());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    }
	}