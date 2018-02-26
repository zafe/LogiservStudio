	package application.repository.info;

    import application.comunes.Alerta;
    import application.database.JDBCConnection;
    import application.model.info.Domicilio;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

	public class DomicilioRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(Domicilio domicilio){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO DOMICILIO (Calle, Numero, LOCALIDAD_idLocalidad) values(?,?,?)");
	            preparedStatement.setString(1,domicilio.getCalle());
	            preparedStatement.setString(2,domicilio.getNumero());
	            preparedStatement.setInt(3, domicilio.getLocalidad().getIdLocalidad());
	            preparedStatement.execute();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public int  last(){
	    	int last = 0;
			try {
				connection = JDBCConnection.getInstanceConnection();
				preparedStatement= connection.prepareStatement("" +
						"Select max(idDomicilio) as id from domicilio");
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next())
				last = resultSet.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	return last;
		}
	    public void update(Domicilio domicilio){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement= connection.prepareStatement("" +
	                    "UPDATE DOMICILIO " +
	                    "SET Calle=?, Numero=?, LOCALIDAD_idLocalidad=? " +
	                    "WHERE idProvincia=?");
	            preparedStatement.setString(1,domicilio.getCalle());
	            preparedStatement.setString(2,domicilio.getNumero());
	            preparedStatement.setInt(3, domicilio.getLocalidad().getIdLocalidad());
	            preparedStatement.setInt(4, domicilio.getLocalidad().getProvincia().getIdProvincia());
	            String headerMsj="Actualización: Domicilio actualizado";
	            String cuerpoMsj = "Domicilio " + domicilio.getCalle() + " " + domicilio.getNumero()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Domicilio", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void delete(Domicilio domicilio){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM DOMICILIO WHERE idDomicilio=?");
	            preparedStatement.setInt(1, domicilio.getIdDomicilio());
	            preparedStatement.executeUpdate();

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<Domicilio> view(){

	        ObservableList<Domicilio> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT d.Calle, d.Numero, l.NombreLocalidad, p.NombreProvincia "
	            		+ "FROM DOMICILIO d, LOCALIDAD l, PROVINCIA p WHERE d.LOCALIDAD_idLocalidad = l.idLocalidad"
	            		+ " AND l.PROVINCIA_idProvincia = p.idProvincia");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                Domicilio domicilio = new Domicilio();
	                domicilio.setIdDomicilio(resultSet.getInt("idDomicilio"));
	                domicilio.setCalle(resultSet.getString("Calle"));
	                domicilio.setNumero(resultSet.getString("Numero"));
	               // domicilio.setNombre_localidad(resultSet.getString("NombreLocalidad"));
	               // domicilio.setNombre_provincia(resultSet.getString("NombreProvincia"));
	                list.add(domicilio);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }

	    public Domicilio getDomicilioById(int idDomicilio){
	    	Domicilio domicilio = new Domicilio();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT d.idDomicilio, d.Calle, d.Numero\n" +
						"FROM DOMICILIO d\n" +
						"WHERE d.idDomicilio = ?;");
	            preparedStatement.setInt(1,idDomicilio);
	            resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
	           //TODO Revisar esto domicilio.setIdDomicilio(resultSet.getInt("idDomicilio"));
				domicilio.setIdDomicilio(resultSet.getInt("idDomicilio"));
	            domicilio.setCalle(resultSet.getString("Calle"));
	            domicilio.setNumero(resultSet.getString("Numero"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return domicilio;
	    }

	    public Domicilio getDomicilioByEmpleadoId(int idEmpleado){
			Domicilio domicilio = new Domicilio();
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT d.idDomicilio, d.Calle, d.Numero\n" +
						"FROM DOMICILIO d\n" +
						"INNER JOIN EMPLEADO e ON d.idDomicilio = e.DOMICILIO_idDomicilio\n" +
						"WHERE e.idEmpleado = ?");
				preparedStatement.setInt(1,idEmpleado);
				resultSet = preparedStatement.executeQuery();
				if(resultSet.next()){
					//TODO Revisar esto domicilio.setIdDomicilio(resultSet.getInt("idDomicilio"));
					domicilio.setIdDomicilio(resultSet.getInt("idDomicilio"));
					domicilio.setCalle(resultSet.getString("Calle"));
					domicilio.setNumero(resultSet.getString("Numero"));
					domicilio.setLocalidad();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return domicilio;
		}
	}