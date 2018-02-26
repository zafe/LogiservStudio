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
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public ObservableList<Localidad> view(int idProvincia){

	        ObservableList<Localidad> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM localidad inner join provincia WHERE PROVINCIA_idProvincia =? AND provincia_idprovincia=idprovincia;");
	            preparedStatement.setInt(1,idProvincia);
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	            	Localidad localidad = new Localidad(resultSet.getInt(1), resultSet.getString(2),
							new Provincia(resultSet.getInt(3), resultSet.getString(5)));
	            	list.add(localidad);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }

	    public ObservableList<Localidad> view2(int idProvincia){

	        ObservableList<Localidad> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM LOCALIDAD WHERE PROVINCIA_idProvincia = ?");
	            preparedStatement.setInt(1,idProvincia);
	            resultSet = preparedStatement.executeQuery();

	            while (resultSet.next()){
	                Localidad localidad = new Localidad();
	                localidad.setIdLocalidad(resultSet.getInt("idLocalidad"));
	                localidad.setNombre(resultSet.getString("NombreLocalidad"));
	                list.add(localidad);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    
	    public Localidad search(String nombreLocalidad){
			Localidad localidad = new Localidad();
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT * FROM LOCALIDAD where NombreLocalidad LIKE ?");
				preparedStatement.setString(1,nombreLocalidad+ '%');
				resultSet=preparedStatement.executeQuery();

				while(resultSet.next()){
					localidad.setIdLocalidad(resultSet.getInt(1));
					localidad.setNombre(resultSet.getString(2));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return localidad;

		}

		public Localidad getLocalidadByIdDomicilio(int idDomicilio){
			Localidad localidad = new Localidad();
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT l.idLocalidad, l.NombreLocalidad\n" +
						"FROM LOCALIDAD l\n" +
						"INNER JOIN DOMICILIO d ON l.idLocalidad = d.LOCALIDAD_idLocalidad\n" +
						"WHERE d.idDomicilio = ?;");
				preparedStatement.setInt(1,idDomicilio);
				resultSet=preparedStatement.executeQuery();

				while(resultSet.next()){
					localidad.setIdLocalidad(resultSet.getInt(1));
					localidad.setNombre(resultSet.getString(2));
					localidad.setProvincia();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return localidad;

		}

		public Localidad getLocalidadById(int idLocalidad){
			Localidad localidad = new Localidad();
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT l.idLocalidad, l.NombreLocalidad\n" +
						"FROM LOCALIDAD l\n" +
						"WHERE l.idLocalidad = ?;");
				preparedStatement.setInt(1,idLocalidad);
				resultSet=preparedStatement.executeQuery();

				while(resultSet.next()){
					localidad.setIdLocalidad(resultSet.getInt(1));
					localidad.setNombre(resultSet.getString(2));
					localidad.setProvincia();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return localidad;


		}
	}