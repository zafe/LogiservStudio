	package application.repository.info;

	import application.comunes.Alerta;
	import application.database.JDBCConnection;
	import application.model.venta.Viaje;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.text.DateFormat;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;

	public class ViajeRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(Viaje viaje, int idOrigenDestino,  int idEmpleado, int idCamion, int idFacturaVenta){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO VIAJE (Fecha, HoraEntrada, Bruto, Tara,"
	            		+ "Origen_Destino_idOrigen_Destino, Empleado_idEmpleado, CAMION_idCamion, FACTURA_VENTA_idFACTURA_VENTA)"
	            		+ " values(?,?,?,?,?,?,?,?)");
	            try {
		            SimpleDateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
					preparedStatement.setDate(1, (java.sql.Date) dateFormat.parse(viaje.getFecha()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
				     DateFormat formatter = new SimpleDateFormat("HH:mm");
			            java.sql.Time horaEntrada;
					horaEntrada = new java.sql.Time(formatter.parse(viaje.getHoraEntrada()).getTime());
		            preparedStatement.setTime(2, horaEntrada);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            preparedStatement.setDouble(3, viaje.getBruto());
	            preparedStatement.setDouble(4, viaje.getTara());
	            preparedStatement.setInt(5, idOrigenDestino);
	            preparedStatement.setInt(6, idEmpleado);
	            preparedStatement.setInt(7, idCamion);
	            preparedStatement.setInt(8, idFacturaVenta);
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	            String cuerpoMsj = "Provincia " + viaje.getIdRemito()+ " agregado correctamente.\n";
	            Alerta.alertaInfo("Viajes",cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }


	    }
	    public void update(Viaje viaje, int idOrigenDestino,  int idEmpleado, int idCamion, int idFacturaVenta){
	        try {
	            connection = JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("UPDATE VIAJE SET Fecha=?, HoraEntrada=?, Bruto=?, Tara=?,"
	            		+ "Origen_Destino_idOrigen_Destino=?, Empleado_idEmpleado=?, CAMION_idCamion=?, FACTURA_VENTA_idFACTURA_VENTA=?"
	            		+ "WHERE idRemito=?");
	            try {
		            SimpleDateFormat dateFormat  = new SimpleDateFormat("dd/MM/yyyy");
					preparedStatement.setDate(1, (java.sql.Date) dateFormat.parse(viaje.getFecha()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
				     DateFormat formatter = new SimpleDateFormat("HH:mm");
			            java.sql.Time horaEntrada;
					horaEntrada = new java.sql.Time(formatter.parse(viaje.getHoraEntrada()).getTime());
		            preparedStatement.setTime(2, horaEntrada);

				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	            preparedStatement.setDouble(3, viaje.getBruto());
	            preparedStatement.setDouble(4, viaje.getTara());
	            preparedStatement.setInt(5, idOrigenDestino);
	            preparedStatement.setInt(6, idEmpleado);
	            preparedStatement.setInt(7, idCamion);
	            preparedStatement.setInt(8, idFacturaVenta);
	            preparedStatement.setInt(9, viaje.getIdRemito());
	            preparedStatement.close();
	            connection.close();
	            String headerMsj="Actualización: Provincia actualizado";
	            String cuerpoMsj = "Provincia: " + viaje.getIdRemito() + " modificado correctamente.";
	            Alerta.alertaInfo("Provincia", headerMsj, cuerpoMsj);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public void delete(Viaje viaje){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement(
	                    "DELETE FROM VIAJE WHERE idRemito=?");
	            preparedStatement.setInt(1, viaje.getIdRemito());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<Viaje> view(){

	        ObservableList<Viaje> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM VIAJE INNER JOIN"
	            		+ "FROM VIAJE v, ORIGEN_DESTINO o, EMPLEADO e, CAMION c, FACTURA_VENTA f"
	            		+ "WHERE v.Origen_Destino_idOrigen_Destino = o.idOrigen_Destino"
	            		+ "AND v.Empleado_idEmpleado = e.idEmpleado"
	            		+ "AND v.Camion_idCamion = c.idCamion"
	            		+ "AND v.FACTURA_VENTA_idFactura_VENTA = f.idFactura_Venta");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                Viaje viaje = new Viaje();
	                viaje.setIdRemito(resultSet.getInt("idRemito"));
	                viaje.setFecha(resultSet.getDate("Fecha").toString());
	                viaje.setBruto(resultSet.getDouble("Bruto"));
	                viaje.setTara(resultSet.getDouble("Tara"));
	                viaje.setFincaOrigen(resultSet.getString("Finca"));
	                viaje.setIngenioDestino(resultSet.getString("Ingenio"));
	                viaje.setCamion(resultSet.getString("Marca")+" "+resultSet.getString("Modelo"));
	                viaje.setFacturaVenta(resultSet.getString("idFacturaVenta"));
	                list.add(viaje);
	            }
	            preparedStatement.close();
	            resultSet.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }
	    
	    public void search(Viaje viaje){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM VIAJE INNER JOIN"
	            		+ "FROM VIAJE v, ORIGEN_DESTINO o, EMPLEADO e, CAMION c, FACTURA_VENTA f"
	            		+ "WHERE v.Origen_Destino_idOrigen_Destino = o.idOrigen_Destino"
	            		+ "AND v.Empleado_idEmpleado = e.idEmpleado"
	            		+ "AND v.Camion_idCamion = c.idCamion"
	            		+ "AND v.FACTURA_VENTA_idFactura_VENTA = f.idFactura_Venta"
	            		+ "AND v.idRemito=?");
	            preparedStatement.setInt(1,viaje.getIdRemito());
	            preparedStatement.executeUpdate();
	            preparedStatement.close();
	            connection.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    }
	}