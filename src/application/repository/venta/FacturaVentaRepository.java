	package application.repository.venta;

	import application.comunes.Alerta;
	import application.database.JDBCConnection;
	import application.model.venta.FacturaVenta;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;

	public class FacturaVentaRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(FacturaVenta facturaVenta, int idCliente){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO F (NombreEmpre, CUIT, FechaEmision, CLIENTE_idCliente ) "
	            		+ "values(?,?,?,?)");
	            preparedStatement.setString(1,facturaVenta.getNombreEmpresa());
	            preparedStatement.setString(2, facturaVenta.getCuit());
	            SimpleDateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
	            try {
					preparedStatement.setDate(3, (java.sql.Date) dateFormat.parse(facturaVenta.getFechaEmision()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            preparedStatement.setInt(4, idCliente);
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            String cuerpoMsj = "Factura Venta " + facturaVenta.getIdFacturaVenta()+ " agregada correctamente.\n";
	            Alerta.alertaInfo("Facturas de Venta",cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public void update(FacturaVenta facturaVenta, int idCliente){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement= connection.prepareStatement("" +
	                    "UPDATE CLIENTE " +
	                    "SET NombreEmpresa=?, CUIT=?, FechaEmision=?, CLIENTE_idCliente=? " + 
	                    "WHERE idFACTURA_VENTA=?");
	            preparedStatement.setString(1,facturaVenta.getNombreEmpresa());
	            preparedStatement.setString(2,facturaVenta.getCuit());
	            SimpleDateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
	            try {
					preparedStatement.setDate(3, (java.sql.Date) dateFormat.parse(facturaVenta.getFechaEmision()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            preparedStatement.setInt(4, idCliente);
	            preparedStatement.setInt(5, facturaVenta.getIdFacturaVenta());
	            preparedStatement.close();
	            connection.close();
	            String headerMsj="Actualización: Factura venta actualizada";
	            String cuerpoMsj = "Factura Venta: " + facturaVenta.getIdFacturaVenta() + " modificada correctamente.";
	            Alerta.alertaInfo("Factura Venta", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void delete(FacturaVenta facturaVenta){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM FACTURA_VENTA WHERE idFACTURA_VENTA=?");
	            preparedStatement.setInt(1, facturaVenta.getIdFacturaVenta());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<FacturaVenta> view(){

	        ObservableList<FacturaVenta> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM FACTURA_VENTA INNER JOIN CLIENTE "
	            		+ "WHERE idCliente = CLIENTE_idCliente");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                FacturaVenta facturaVenta = new FacturaVenta();
	                facturaVenta.setIdFacturaVenta(resultSet.getInt("idFACTURA_VENTA"));
	                facturaVenta.setNombreEmpresa(resultSet.getString("NombreEmpresa"));
	                facturaVenta.setCuit(resultSet.getString("CUIT"));
	                facturaVenta.setCuitCliente(resultSet.getString("Nombre"));
	                facturaVenta.setFechaEmision(resultSet.getDate("FechaEmision").toString());//TODO PUEDE HABER ERROR AQUI
	                list.add(facturaVenta);
	            }
	            preparedStatement.close();
	            resultSet.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    public void search(FacturaVenta facturaVenta){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM FACTURA_VENTA where idFACTURA_VENTA=?");
	            preparedStatement.setInt(1,facturaVenta.getIdFacturaVenta());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    }
	}