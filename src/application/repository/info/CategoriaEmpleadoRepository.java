	package application.repository.info;

    import application.comunes.Alerta;
    import application.database.JDBCConnection;
    import application.model.info.CategoriaEmpleado;
    import javafx.collections.FXCollections;
    import javafx.collections.ObservableList;

    import java.sql.Connection;
    import java.sql.PreparedStatement;
    import java.sql.ResultSet;
    import java.sql.SQLException;

	public class CategoriaEmpleadoRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(CategoriaEmpleado categoriaEmpleado){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO CATEGORIA_EMPLEADO (NombreCategoria) values(?)");
	            preparedStatement.setString(1, categoriaEmpleado.getNombre());
	            preparedStatement.executeUpdate();
	            String cuerpoMsj = "Categoria Empleado " + categoriaEmpleado.getNombre()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Categoria Empleado",cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public void update(CategoriaEmpleado categoriaEmpleado){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement= connection.prepareStatement("" +
	                    "UPDATE CATEGORIA_EMPLEADO " +
	                    "SET NombreCategoria=? " +
	                    "WHERE idCategoriaEmpleado=?");
	            preparedStatement.setString(1, categoriaEmpleado.getNombre());
	            preparedStatement.setInt(2, categoriaEmpleado.getIdCategoriaEmpleado());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            String headerMsj="Actualización: Categora Empleado actualizada";
	            String cuerpoMsj = "Categoria Empleado: " + categoriaEmpleado.getNombre() + " modificado correctamente.";
	            Alerta.alertaInfo("Categoria Empleado", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    
	    public void delete(CategoriaEmpleado categoriaEmpleado){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM CATEGORIA_EMPLEADO WHERE idCategoriaEmpleado=?");
	            preparedStatement.setInt(1, categoriaEmpleado.getIdCategoriaEmpleado());
	            preparedStatement.execute();
	            preparedStatement.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<CategoriaEmpleado> view(){

	        ObservableList<CategoriaEmpleado> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM CATEGORIA_EMPLEADO");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                CategoriaEmpleado categoriaEmpleado = new CategoriaEmpleado();
	                categoriaEmpleado.setIdCategoriaEmpleado(resultSet.getInt("idCategoriaEmpleado"));
	                categoriaEmpleado.setNombre(resultSet.getString("NombreCategoria"));
	                list.add(categoriaEmpleado);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    
	    public CategoriaEmpleado search(int idCategoriaEmpleado){
        	CategoriaEmpleado categoriaEmpleado = new CategoriaEmpleado();

	    	try {
	        	connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM CATEGORIA_EMPLEADO where idCategoriaEmpleado=?");
	            preparedStatement.setInt(1, idCategoriaEmpleado);
	            resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
		            categoriaEmpleado.setIdCategoriaEmpleado(idCategoriaEmpleado);
		            categoriaEmpleado.setNombre(resultSet.getString("NombreCategoria"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        
	        return categoriaEmpleado;
	    }
	}