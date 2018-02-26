	package application.repository.venta;

	import application.comunes.Alerta;
	import application.comunes.Calculo;
	import application.database.JDBCConnection;
	import application.model.venta.Cliente;
	import application.model.venta.FacturaVenta;
	import application.model.venta.Organizacion;
	import application.model.venta.Viaje;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.List;

	public class FacturaVentaRepository {
		ClienteRepository clienteRepository = new ClienteRepository();
		OrganizacionRepository organizacionRepository = new OrganizacionRepository();
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    
	    public void save(FacturaVenta facturaVenta, int idCliente){
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement = connection.prepareStatement("INSERT INTO FACTURA_VENTA (NombreEmpre, CUIT, FechaEmision, CLIENTE_idCliente ) "
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
	                facturaVenta.setFechaEmision(resultSet.getString("FechaEmision"));
					Cliente cliente = clienteRepository.getClienteById(resultSet.getInt("CLIENTE_idCLIENTE"));
					facturaVenta.setCliente(cliente);
 					Organizacion organizacion = organizacionRepository.getOrganizacionById(resultSet.getInt("ORGANIZACION_idORGANIZACION"));
					facturaVenta.setOrganizacion(organizacion);
	                list.add(facturaVenta);
	            }
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
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	    }

		public void emitirFactura(List<Viaje> viajesALiquidar, int idCliente, int idOrg) {//TODO: Escribir este metodo en la clase que corresponda
			//primero ingresar la factura

			try {
				connection = JDBCConnection.getInstanceConnection();
				preparedStatement = connection.prepareStatement("INSERT INTO FACTURA_VENTA" +
						" (FechaEmision,CLIENTE_idCLIENTE,ORGANIZACION_idORGANIZACION) " +
						"VALUES (curdate(), ?, ?);");
				preparedStatement.setInt(1, idCliente);
				preparedStatement.setInt(2, idOrg);
				preparedStatement.executeUpdate();
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//luego obtener el idFactura recientemente ingresado
			int idFactura = 0;

			try {
				connection = JDBCConnection.getInstanceConnection();
				preparedStatement = connection.prepareStatement("select last_insert_id();");
				resultSet = preparedStatement.executeQuery();
				if (resultSet.next()) idFactura = resultSet.getInt(1);
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//Ahora a crear por una Linea de Viaje por cada Viaje de la colección

			for (Viaje viajeaLiquidar : viajesALiquidar) {
				Calculo calculo  = new Calculo();
				double pesoNeto = calculo.calcularPesoNeto(viajeaLiquidar.getBruto(), viajeaLiquidar.getTara());

				double monto = calculo.calcularMonto(Double.parseDouble(viajeaLiquidar.getDistanciaRecorrida()),pesoNeto,
						viajeaLiquidar.getIngenio().getTarifa(), viajeaLiquidar.getIngenio().getArranque());
				try {
					connection = JDBCConnection.getInstanceConnection();
					preparedStatement = connection.prepareStatement("INSERT INTO LINEA_VIAJE (monto," +
							" FACTURA_VENTA_idFACTURA_VENTA, VIAJE_idRemito) VALUES (?,?,?);");
					preparedStatement.setDouble(1, monto);
					preparedStatement.setInt(2, idFactura);
					preparedStatement.setInt(3, viajeaLiquidar.getIdRemito() );
					preparedStatement.executeUpdate();
					//if (resultSet.next()) idFactura = resultSet.getInt(1);
					preparedStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}



	    }



	}