package application.repository.calculo;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.Camion;
import application.model.calculo.CargaCombustible;
import application.model.compra.FacturaCompra;
import application.model.info.Empleado;
import application.repository.compra.FacturaCompraRepository;
import application.repository.info.EmpleadoRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class CargaCombustibleRepository {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	FincaRepository fincaRepository = new FincaRepository();
	IngenioRepository ingenioRepository = new IngenioRepository();
	EmpleadoRepository empleadoRepository = new EmpleadoRepository();
	CamionRepository camionRepository = new CamionRepository();
	FacturaCompraRepository facturaCompraRepository = new FacturaCompraRepository();

	public void save(CargaCombustible cargaCombustible, int idConductor, int idCamion, int idFacturaCompra) {
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement("INSERT INTO CARGA_COMBUSTIBLE (FechaCarga, HoraCarga, CantidadLitros, EMPLEADO_idEmpleado,CAMION_idCamion"
					+ ", COMPRA_ARTICULO_idFacturaCompraArticulo)"
					+ " values(?,?,?,?,?,?)");
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				preparedStatement.setDate(1, (java.sql.Date) dateFormat.parse(cargaCombustible.getFechaCarga()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				DateFormat formatter = new SimpleDateFormat("HH:mm");
				java.sql.Time horaEntrada;
				horaEntrada = new java.sql.Time(formatter.parse(cargaCombustible.getHoraCarga()).getTime());
				preparedStatement.setTime(2, horaEntrada);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preparedStatement.setDouble(3, cargaCombustible.getCantidadLitros());
			preparedStatement.setDouble(4, idConductor);
			preparedStatement.setInt(5, idCamion);
			preparedStatement.setInt(6, idFacturaCompra);
			preparedStatement.executeUpdate();
			preparedStatement.close();
			String cuerpoMsj = "Carga de Combustible " + cargaCombustible.getIdCargaCombustible() + " agregada correctamente.\n";
			Alerta.alertaInfo("Carga de Combustible", cuerpoMsj);
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}


	public void update(CargaCombustible cargaCombustible, int idEmpleado, int idCamion, int idFacturaCompra) {
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement("UPDATE CARGA_COMBUSTIBLE SET FechaCarga=?, HoraCarga=?, CantidadLitros=?, EMPLEADO_idEmpleado=?,CAMION_idCamion"
					+ "=?, COMPRA_ARTICULO_idFacturaCompraArticulo=?"
					+ "WHERE idCARGA_COMBUSTIBLE=?");
			try {
				SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
				preparedStatement.setDate(1, (java.sql.Date) dateFormat.parse(cargaCombustible.getFechaCarga()));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				DateFormat formatter = new SimpleDateFormat("HH:mm");
				java.sql.Time horaEntrada;
				horaEntrada = new java.sql.Time(formatter.parse(cargaCombustible.getHoraCarga()).getTime());
				preparedStatement.setTime(2, horaEntrada);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			preparedStatement.setDouble(3, cargaCombustible.getCantidadLitros());
			preparedStatement.setDouble(4, idEmpleado);
			preparedStatement.setInt(5, idCamion);
			preparedStatement.setInt(6, idFacturaCompra);
			preparedStatement.close();
			String headerMsj = "Actualización: Carga de combustible actualizada";
			String cuerpoMsj = "Carga de combustible: " + cargaCombustible.getIdCargaCombustible() + " modificada correctamente.";
			Alerta.alertaInfo("Carga de combustible", headerMsj, cuerpoMsj);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void delete(CargaCombustible viaje) {
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement(
					"DELETE FROM CARGA_COMBUSTIBLE WHERE idCARGA_COMBUSTIBLE=?");
			preparedStatement.setInt(1, viaje.getIdCargaCombustible());
			preparedStatement.executeUpdate();
			preparedStatement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ObservableList<CargaCombustible> view() {

		ObservableList<CargaCombustible> list = FXCollections.observableArrayList();
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement("SELECT cc.idCARGA_COMBUSTIBLE, cc.FechaCarga, cc.HoraCarga, cc.CantidadLitros, cc.EMPLEADO_idEmpleado," +
					"       e.Nombre, e.Apellido, c.idCamion, c.Marca, c.Modelo, ca.Fecha\n" +
					"FROM CARGA_COMBUSTIBLE cc\n" +
					"   INNER JOIN EMPLEADO e ON EMPLEADO_idEmpleado = e.idEmpleado" +
					"   INNER JOIN CAMION c ON CAMION_idCamion = c.idCamion" +
					"   INNER JOIN COMPRA_ARTICULO ca ON COMPRA_ARTICULO_idFacturaCompraArticulo = ca.idFacturaCompraArticulo;");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				CargaCombustible cargaCombustible = new CargaCombustible();
				cargaCombustible.setIdCargaCombustible(resultSet.getInt("idCARGA_COMBUSTIBLE"));
				cargaCombustible.setFechaCarga(resultSet.getDate("FechaCarga").toString());
				cargaCombustible.setHoraCarga(resultSet.getTime("HoraCarga").toString());
				cargaCombustible.setCantidadLitros(resultSet.getDouble("CantidadLitros"));
				Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
				cargaCombustible.setConductor(conductor);
				Camion camion = camionRepository.getCamionById(resultSet.getInt("idCamion"));
				cargaCombustible.setCamion(camion);
				//todo ahora FacturaCompra facturaCompra = facturaCompraRepository.getFacturaCompraById(resultSet.getInt(""));
				//todo ahora cargaCombustible.setFacturaCompra(facturaCompra);
				list.add(cargaCombustible);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return list;
	}

	public CargaCombustible search(int idCargaCombustible) {
		CargaCombustible cargaCombustible = new CargaCombustible();
		CamionRepository camionRepository = new CamionRepository();
		EmpleadoRepository empleadoRepository = new EmpleadoRepository();
		FacturaCompraRepository facturaCompraRepository = new FacturaCompraRepository();

		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement = connection.prepareStatement("SELECT cc.idCARGA_COMBUSTIBLE, cc.FechaCarga, cc.HoraCarga," +
					" c.idCamion, e.idEmpleado, ca.idFacturaCompraArticulo FROM CARGA_COMBUSTIBLE cc " +
					"INNER JOIN EMPLEADO e ON cc.EMPLEADO_idEmpleado = e.idEmpleado " +
					"INNER JOIN CAMION c ON cc.CAMION_idCamion = c.idCamion" +
					"INNER JOIN COMPRA_ARTICULO ca ON cc.COMPRA_ARTICULO_idFacturaCompraArticulo = ca.idFacturaCompraArticulo" +
					"    WHERE idCARGA_COMBUSTIBLE =?;");
			preparedStatement.setInt(1, idCargaCombustible);
			resultSet = preparedStatement.executeQuery();
			cargaCombustible.setIdCargaCombustible(resultSet.getInt("idCARGA_COMBUSTIBLE"));
			cargaCombustible.setFechaCarga(resultSet.getString("FechaCarga"));
			cargaCombustible.setHoraCarga(resultSet.getString("HoraCarga"));
			Camion camion = camionRepository.getCamionById(resultSet.getInt("CAMION_idCamion"));
			cargaCombustible.setCamion(camion);
			Empleado conductor = empleadoRepository.getEmpleadoById(resultSet.getInt("EMPLEADO_idEmpleado"));
			cargaCombustible.setConductor(conductor);
			FacturaCompra facturaCompra = facturaCompraRepository.getFacturaCompraById(resultSet.getInt("COMPRA_ARTICULO_idFacturaCompraArticulo"));
			cargaCombustible.setFacturaCompra(facturaCompra);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cargaCombustible;
	}

}