	package application.repository.venta;

	import application.comunes.Alerta;
	import application.comunes.Calculo;
	import application.database.JDBCConnection;
	import application.model.calculo.Camion;
	import application.model.calculo.Finca;
	import application.model.calculo.Ingenio;
	import application.model.info.Empleado;
	import application.model.venta.Viaje;
	import application.repository.calculo.CamionRepository;
	import application.repository.calculo.FincaRepository;
	import application.repository.calculo.IngenioRepository;
	import application.repository.info.EmpleadoRepository;
	import javafx.collections.FXCollections;
	import javafx.collections.ObservableList;

	import java.math.BigDecimal;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.text.DateFormat;
	import java.text.ParseException;
	import java.text.SimpleDateFormat;
	import java.util.List;

	public class ViajeRepository {
	    Connection connection;
	    PreparedStatement preparedStatement;
	    ResultSet resultSet;
	    FincaRepository fincaRepository = new FincaRepository();
	    IngenioRepository ingenioRepository = new IngenioRepository();
	    EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	    CamionRepository camionRepository = new CamionRepository();
	    
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

		public void save2(Viaje viaje, int idOrigenDestino){
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement = connection.prepareStatement("INSERT INTO VIAJE (Fecha, HoraEntrada, Bruto, Tara,"
						+ "Origen_Destino_idOrigen_Destino, Empleado_idEmpleado, CAMION_idCamion)"
						+ " values(?,?,?,?,?,?,?);");
				preparedStatement.setString(1, viaje.getFecha());
				preparedStatement.setString(2, viaje.getHoraEntrada());
				preparedStatement.setDouble(3, viaje.getBruto());
				preparedStatement.setDouble(4, viaje.getTara());
				preparedStatement.setInt(5, idOrigenDestino);
				preparedStatement.setInt(6, viaje.getConductor().getIdEmpleado());
				preparedStatement.setInt(7, viaje.getCamion().getId());
				preparedStatement.executeUpdate();
				preparedStatement.close();
				String cuerpoMsj = "Viaje " + viaje.getIdRemito()+ " agregado correctamente.\n";
				Alerta.alertaInfo("Viajes",cuerpoMsj);
			} catch (SQLException e) {
				e.printStackTrace();
			}


		}

		public void update2(Viaje viaje, int idOrigenDestino){
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement = connection.prepareStatement("UPDATE VIAJE SET Fecha=?, HoraEntrada=?, Bruto=?," +
						" Tara=?, Origen_Destino_idOrigen_Destino=?, Empleado_idEmpleado=?, CAMION_idCamion=?" +
						" WHERE idRemito=?;");
				preparedStatement.setString(1, viaje.getFecha());
				preparedStatement.setString(2, viaje.getHoraEntrada());
				preparedStatement.setDouble(3, viaje.getBruto());
				preparedStatement.setDouble(4, viaje.getTara());
				preparedStatement.setInt(5, idOrigenDestino);
				preparedStatement.setInt(6, viaje.getConductor().getIdEmpleado());
				preparedStatement.setInt(7, viaje.getCamion().getId());
				preparedStatement.setInt(8,viaje.getIdRemito());
				System.out.println("UPDATE VIAJE QUERY: " + preparedStatement);
				preparedStatement.executeUpdate();
				preparedStatement.close();
				String cuerpoMsj = "Viaje " + viaje.getIdRemito()+ " agregado correctamente.\n";
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
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
	    public ObservableList<Viaje> view(){

	        ObservableList<Viaje> list = FXCollections.observableArrayList();
	        try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT v.idRemito, v.Fecha, v.HoraEntrada, v.Bruto," +
						" v.Tara, v.Empleado_idEmpleado, v.CAMION_idCamion, od.DistanciaKM, od.FINCA_idFinca, " +
						"od.INGENIO_idIngenio FROM VIAJE v " +
						"INNER JOIN ORIGEN_DESTINO od ON Origen_Destino_idOrigen_Destino= od.idOrigen_Destino;");
	            resultSet = preparedStatement.executeQuery();
	            while (resultSet.next()){
	                Viaje viaje = new Viaje();
	                viaje.setIdRemito(resultSet.getInt("idRemito"));
	                viaje.setFecha(resultSet.getDate("Fecha").toString());
	                viaje.setHoraEntrada(resultSet.getTime("HoraEntrada").toString());
	                viaje.setBruto(resultSet.getDouble("Bruto"));
	                viaje.setTara(resultSet.getDouble("Tara"));
	                viaje.setDistanciaRecorrida(resultSet.getString("DistanciaKM"));
					Finca finca = fincaRepository.getFincaById(resultSet.getInt("FINCA_idFinca"));
					viaje.setFinca(finca);
					Ingenio ingenio = ingenioRepository.getIngenioById(resultSet.getInt("INGENIO_idIngenio"));
					viaje.setIngenio(ingenio);
					Camion camion = camionRepository.getCamionById(resultSet.getInt("CAMION_idCamion"));
					Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
					viaje.setConductor(conductor);
					viaje.setCamion(camion);
	                list.add(viaje);
	                System.out.printf("Viaje agregado%n" +
							"idRemito: %s %n" +
							"Fecha   : %s %n" +
							"Bruto   : %s %n" +
							"Tara    : %s %n",
							viaje.getIdRemito(),
							viaje.getFecha(),
							viaje.getBruto(),
							viaje.getTara());
	            }
	            preparedStatement.close();
	            resultSet.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return list;
	    }

		public ObservableList<Viaje> getViajesSinLiquidar(){

			ObservableList<Viaje> list = FXCollections.observableArrayList();
			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT v.idRemito, v.Fecha, v.HoraEntrada, v.Bruto," +
						" v.Tara, v.Empleado_idEmpleado, v.CAMION_idCamion, od.DistanciaKM, od.FINCA_idFinca, " +
						"od.INGENIO_idIngenio FROM VIAJE v INNER JOIN ORIGEN_DESTINO od ON " +
						"Origen_Destino_idOrigen_Destino= od.idOrigen_Destino " +
						"LEFT JOIN LINEA_VIAJE lv ON v.idRemito = lv.VIAJE_idRemito WHERE lv.idLINEA_VIAJE IS NULL;");
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					Viaje viaje = new Viaje();
					viaje.setIdRemito(resultSet.getInt("idRemito"));
					viaje.setFecha(resultSet.getDate("Fecha").toString());
					viaje.setHoraEntrada(resultSet.getTime("HoraEntrada").toString());
					viaje.setBruto(resultSet.getDouble("Bruto"));
					viaje.setTara(resultSet.getDouble("Tara"));
					viaje.setDistanciaRecorrida(resultSet.getString("DistanciaKM"));
					Finca finca = fincaRepository.getFincaById(resultSet.getInt("FINCA_idFinca"));
					viaje.setFinca(finca);
					Ingenio ingenio = ingenioRepository.getIngenioById(resultSet.getInt("INGENIO_idIngenio"));
					viaje.setIngenio(ingenio);
					Camion camion = camionRepository.getCamionById(resultSet.getInt("CAMION_idCamion"));
					Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
					viaje.setConductor(conductor);
					viaje.setCamion(camion);
					list.add(viaje);
					System.out.printf("Viaje agregado%n" +
									"idRemito: %s %n" +
									"Fecha   : %s %n" +
									"Bruto   : %s %n" +
									"Tara    : %s %n",
							viaje.getIdRemito(),
							viaje.getFecha(),
							viaje.getBruto(),
							viaje.getTara());
				}
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		}
	    
	    public Viaje search(int idViaje){
	    	Viaje viaje = new Viaje();
	    	try {
	            connection= JDBCConnection.getInstanceConnection();
	            preparedStatement=connection.prepareStatement("SELECT * FROM VIAJE INNER JOIN"
	            		+ "FROM VIAJE v, ORIGEN_DESTINO o, EMPLEADO e, CAMION c, FACTURA_VENTA f"
	            		+ "WHERE v.Origen_Destino_idOrigen_Destino = o.idOrigen_Destino"
	            		+ "AND v.Empleado_idEmpleado = e.idEmpleado"
	            		+ "AND v.Camion_idCamion = c.idCamion"
	            		+ "AND v.FACTURA_VENTA_idFactura_VENTA = f.idFactura_Venta"
	            		+ "AND v.idRemito=?");
	            preparedStatement.setInt(1,idViaje);
	            resultSet = preparedStatement.executeQuery();
	            viaje.setIdRemito(resultSet.getInt("idRemito"));
	            viaje.setBruto(resultSet.getDouble("Bruto"));
	            viaje.setCamionNombre(resultSet.getString("Marca") +  " " + resultSet.getString("Modelo") );
	            viaje.setFecha(resultSet.getString("Fecha"));
	            viaje.setDistanciaRecorrida(resultSet.getString("DistanciaKM"));
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }	
	    	return viaje;
	    }

	    public ObservableList<Viaje> getViajesByIdFactura(int idFactura){
	    	ObservableList<Viaje> list = FXCollections.observableArrayList();

			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT v.idRemito, v.Fecha, v.HoraEntrada, v.Bruto," +
						" v.Tara, v.Empleado_idEmpleado, v.CAMION_idCamion, od.DistanciaKM, od.FINCA_idFinca," +
						" od.INGENIO_idIngenio FROM VIAJE v INNER JOIN ORIGEN_DESTINO od " +
						"ON Origen_Destino_idOrigen_Destino= od.idOrigen_Destino " +
						"WHERE FACTURA_VENTA_idFACTURA_VENTA=?;");//TODO cambiar esto cuando se actualice la base de datos
				preparedStatement.setInt(1 ,idFactura);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					Viaje viaje = new Viaje();
					viaje.setIdRemito(resultSet.getInt("idRemito"));
					viaje.setFecha(resultSet.getDate("Fecha").toString());
					viaje.setHoraEntrada(resultSet.getTime("HoraEntrada").toString());
					viaje.setBruto(resultSet.getDouble("Bruto"));
					viaje.setTara(resultSet.getDouble("Tara"));
					viaje.setDistanciaRecorrida(resultSet.getString("DistanciaKM"));
					Finca finca = fincaRepository.getFincaById(resultSet.getInt("FINCA_idFinca"));
					viaje.setFinca(finca);
					Ingenio ingenio = ingenioRepository.getIngenioById(resultSet.getInt("INGENIO_idIngenio"));
					viaje.setIngenio(ingenio);
					Camion camion = camionRepository.getCamionById(resultSet.getInt("CAMION_idCamion"));
					Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
					viaje.setConductor(conductor);
					viaje.setCamion(camion);
					list.add(viaje);
					System.out.printf("Viaje agregado%n" +
									"idRemito: %s %n" +
									"Fecha   : %s %n" +
									"Bruto   : %s %n" +
									"Tara    : %s %n",
							viaje.getIdRemito(),
							viaje.getFecha(),
							viaje.getBruto(),
							viaje.getTara());
				}
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		}

		public ObservableList<Viaje> getViajesByIdIngenio(int idIngenio){
			ObservableList<Viaje> list = FXCollections.observableArrayList();

			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT v.idRemito, v.Fecha, v.HoraEntrada, v.Bruto, v.Tara," +
						" od.DistanciaKM, v.Origen_Destino_idOrigen_Destino, v.Empleado_idEmpleado, v.CAMION_idCamion, " +
						"od.FINCA_idFinca, od.INGENIO_idIngenio FROM VIAJE v INNER JOIN ORIGEN_DESTINO od ON od.idOrigen_Destino = " +
						"v.Origen_Destino_idOrigen_Destino WHERE od.INGENIO_idIngenio = ?;");
				preparedStatement.setInt(1 ,idIngenio);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					Viaje viaje = new Viaje();
					viaje.setIdRemito(resultSet.getInt("idRemito"));
					viaje.setFecha(resultSet.getDate("Fecha").toString());
					viaje.setHoraEntrada(resultSet.getTime("HoraEntrada").toString());
					viaje.setBruto(resultSet.getDouble("Bruto"));
					viaje.setTara(resultSet.getDouble("Tara"));
					viaje.setDistanciaRecorrida(resultSet.getString("DistanciaKM"));
					Finca finca = fincaRepository.getFincaById(resultSet.getInt("FINCA_idFinca"));
					viaje.setFinca(finca);
					Ingenio ingenio = ingenioRepository.getIngenioById(resultSet.getInt("INGENIO_idIngenio"));
					viaje.setIngenio(ingenio);
					Camion camion = camionRepository.getCamionById(resultSet.getInt("CAMION_idCamion"));
					Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
					viaje.setConductor(conductor);
					viaje.setCamion(camion);
					list.add(viaje);
					System.out.printf("Viaje agregado%n" +
									"idRemito: %s %n" +
									"Fecha   : %s %n" +
									"Bruto   : %s %n" +
									"Tara    : %s %n",
							viaje.getIdRemito(),
							viaje.getFecha(),
							viaje.getBruto(),
							viaje.getTara());
				}
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		}

		/**
		 * Devuelve una lista de todos lo viajes de un determinado Ingenio
		 * que no han sido liquidados en alguna facturación
		 *
		 * @param idIngenio Identificador del Ingenio
		 * @return Devuelve una Lista de Viajes de ese Ingenio que no fueron liquidados
		 */

		public ObservableList<Viaje> getViajesSinLiqByIngenioId(int idIngenio){
			ObservableList<Viaje> list = FXCollections.observableArrayList();

			try {
				connection= JDBCConnection.getInstanceConnection();
				preparedStatement=connection.prepareStatement("SELECT v.idRemito, v.Fecha, v.HoraEntrada, v.Bruto," +
						" v.Tara, od.DistanciaKM, v.Origen_Destino_idOrigen_Destino, v.Empleado_idEmpleado, " +
						"v.CAMION_idCamion, od.FINCA_idFinca, od.INGENIO_idIngenio FROM VIAJE v " +
						"INNER JOIN ORIGEN_DESTINO od ON od.idOrigen_Destino = v.Origen_Destino_idOrigen_Destino " +
						"LEFT JOIN LINEA_VIAJE lv ON v.idRemito = lv.VIAJE_idRemito " +
						"WHERE lv.idLINEA_VIAJE IS NULL AND od.INGENIO_idIngenio = ?;");
				preparedStatement.setInt(1 ,idIngenio);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()){
					Viaje viaje = new Viaje();
					viaje.setIdRemito(resultSet.getInt("idRemito"));
					viaje.setFecha(resultSet.getDate("Fecha").toString());
					viaje.setHoraEntrada(resultSet.getTime("HoraEntrada").toString());
					viaje.setBruto(resultSet.getDouble("Bruto"));
					viaje.setTara(resultSet.getDouble("Tara"));
					viaje.setDistanciaRecorrida(resultSet.getString("DistanciaKM"));
					Finca finca = fincaRepository.getFincaById(resultSet.getInt("FINCA_idFinca"));
					viaje.setFinca(finca);
					Ingenio ingenio = ingenioRepository.getIngenioById(resultSet.getInt("INGENIO_idIngenio"));
					viaje.setIngenio(ingenio);
					Camion camion = camionRepository.getCamionById(resultSet.getInt("CAMION_idCamion"));
					Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
					viaje.setConductor(conductor);
					viaje.setCamion(camion);
					list.add(viaje);
					System.out.printf("Viaje agregado%n" +
									"idRemito: %s %n" +
									"Fecha   : %s %n" +
									"Bruto   : %s %n" +
									"Tara    : %s %n",
							viaje.getIdRemito(),
							viaje.getFecha(),
							viaje.getBruto(),
							viaje.getTara());
				}
				preparedStatement.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			return list;
		}

	}
