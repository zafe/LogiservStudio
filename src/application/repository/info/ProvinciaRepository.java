	package application.repository.info;

	import application.comunes.Alerta;
	import application.database.JDBCConnection;
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

	    public ObservableList<Provincia> view(){

	        ObservableList<Provincia> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM PROVINCIA");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	            	Provincia provincia = new Provincia(resultSet.getInt(1), resultSet.getString(2));
	                list.add(provincia);
	            }
			} catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return list;
	    }
	    
	    public ObservableList<Provincia> view2(){
	    	ObservableList<Provincia> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM PROVINCIA");
	            resultSet = preparedStatement.executeQuery();
	            
	            while (resultSet.next())
	            	
	            list.add(new Provincia(resultSet.getInt("idProvincia"), resultSet.getString("NombreProvincia")));
	             
			} catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;	
	    	
	    }
	    
	    
	    public Provincia search(String nombreProvincia){
	    	Provincia provincia = new Provincia();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM PROVINCIA WHERE NombreProvincia LIKE ?");
	            preparedStatement.setString(1, nombreProvincia + '%');
	            resultSet=preparedStatement.executeQuery();
	            while(resultSet.next()){
	            	provincia.setIdProvincia(resultSet.getInt(1));
	            	provincia.setNombre(resultSet.getString(2));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return provincia;
		}

		public Provincia getProviniciaByIdLocalidad(int idLocalidad){
			Provincia provincia = new Provincia();
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT p.idProvincia, p.NombreProvincia\n" +
						"FROM PROVINCIA p\n" +
						"INNER JOIN LOCALIDAD l ON p.idProvincia = l.PROVINCIA_idProvincia\n" +
						"WHERE l.idLocalidad = ?;");
				preparedStatement.setInt(1, idLocalidad );
				resultSet=preparedStatement.executeQuery();
				while(resultSet.next()){
					provincia.setIdProvincia(resultSet.getInt(1));
					provincia.setNombre(resultSet.getString(2));
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

			return provincia;

		}
	}