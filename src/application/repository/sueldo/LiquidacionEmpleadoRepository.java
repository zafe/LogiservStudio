package application.repository.sueldo;

import application.database.JDBCConnection;
import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.LiquidacionEmpleado;
import application.repository.info.EmpleadoRepository;
import application.view.venta.cruds.EmitirFacturaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class LiquidacionEmpleadoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public void save(LiquidacionEmpleado liquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("INSERT INTO LIQUIDACION_EMPLEADO " +
                    "VALUES(?,?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1,null);
            preparedStatement.setString(2,liquidacionEmpleado.getFechaLiquidacion());
            preparedStatement.setDouble(3,liquidacionEmpleado.getImporteNeto());
            preparedStatement.setDouble(4,liquidacionEmpleado.getTotalHaberesRemunerativos());
            preparedStatement.setDouble(5,liquidacionEmpleado.getTotalHaberesNoRemunerativos());
            preparedStatement.setDouble(6,liquidacionEmpleado.getTotalRetenciones());
            preparedStatement.setDouble(7,liquidacionEmpleado.getTotalBruto());
//            preparedStatement.setInt(8, liquidacionEmpleado.getIdPeriodoLiquidacion());
//            preparedStatement.setInt(9,liquidacionEmpleado.getIdEmpleado());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(LiquidacionEmpleado liquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("UPDATE LIQUIDACION_EMPLEADO" +
                    "SET  fecha_liquidacion=?,  importe_neto=?, total_haberes_remunerativos=?," +
                    " total_haberes_no_remunerativos=?, total_retenciones=?, total_bruto=?," +
                    " PERIODO_LIQ_idPERIODO_LIQ=?, EMPLEADO_idEmpleado=?" +
                    "WHERE idLiquidacionEmpleado=?");
            preparedStatement.setString(1,liquidacionEmpleado.getFechaLiquidacion());
            preparedStatement.setDouble(2,liquidacionEmpleado.getImporteNeto());
            preparedStatement.setDouble(3,liquidacionEmpleado.getTotalHaberesRemunerativos());
            preparedStatement.setDouble(5,liquidacionEmpleado.getTotalRetenciones());
            preparedStatement.setDouble(6,liquidacionEmpleado.getTotalBruto());
//            preparedStatement.setInt(7,liquidacionEmpleado.setIdPeriodoLiquidacion());
//            preparedStatement.setInt(8,liquidacionEmpleado.setIdEmpleado());
            preparedStatement.setInt(9,liquidacionEmpleado.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void delete(LiquidacionEmpleado liquidacionEmpleado){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM LIQUIDACION_EMPLEADO WHERE  idLiquidacionEmpleado=?");
            preparedStatement.setInt(1,liquidacionEmpleado.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ObservableList<LiquidacionEmpleado> view(){
        ObservableList<LiquidacionEmpleado> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT * FROM LIQUIDACION_EMPLEADO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                LiquidacionEmpleado liquidacionEmpleado = new LiquidacionEmpleado();
                liquidacionEmpleado.setId(resultSet.getInt(1));
                liquidacionEmpleado.setFechaLiquidacion(resultSet.getString(2));
                liquidacionEmpleado.setImporteNeto(resultSet.getDouble(3));
                liquidacionEmpleado.setTotalHaberesRemunerativos(resultSet.getDouble(4));
                liquidacionEmpleado.setTotalHaberesNoRemunerativos(resultSet.getDouble(5));
                liquidacionEmpleado.setTotalRetenciones(resultSet.getDouble(6));
                liquidacionEmpleado.setTotalBruto(resultSet.getDouble(7));
//                liquidacionEmpleado.setIdPeriodoLiquidacion(resultSet.getInt(8));
//                liquidacionEmpleado.setIdEmpleado(resultSet.getInt(9));
                list.add(liquidacionEmpleado);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Busca LiquidacionEmpleado a partir de un idLiquidacion
     *
     * @param idLiquidacion
     * @return ObservableList<LiquidacionEmpleado>
     */
    public ObservableList<LiquidacionEmpleado> getLiqEmpleadoByIdLiq(int idLiquidacion){
        ObservableList<LiquidacionEmpleado> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("SELECT idLiquidacionEmpleado, fecha_liquidacion, importe_neto, total_haberes_remunerativos, \n" +
                    "\ttotal_haberes_no_remunerativos, total_retenciones, total_bruto, EMPLEADO_idEmpleado, CUIT,\n" +
                    "\tCATEGORIA_EMPLEADO_idCategoriaEmpleado, NombreCategoria, Nombre, Apellido, FechaNacimiento, inicio_periodo, fin_periodo \n" +
                    "\t\t\t\t\tFROM LIQUIDACION_EMPLEADO INNER JOIN EMPLEADO INNER JOIN CATEGORIA_EMPLEADO\n" +
                    "\t\t\t\t\tON CATEGORIA_EMPLEADO_idCategoriaEmpleado = idCategoriaEmpleado AND \n" +
                    "\t\t\t\t\tEMPLEADO_idEmpleado = idEmpleado \n" +
                    "\t\t\t\t\tWHERE LIQUIDACION_idLIQUIDACION = ?");
            preparedStatement.setInt(1, idLiquidacion);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                LiquidacionEmpleado liquidacionEmpleado = new LiquidacionEmpleado();
                liquidacionEmpleado.setId(resultSet.getInt("idLiquidacionEmpleado"));
                liquidacionEmpleado.setFechaLiquidacion(resultSet.getString("fecha_liquidacion"));
                liquidacionEmpleado.setImporteNeto(resultSet.getDouble("importe_neto"));
                liquidacionEmpleado.setTotalHaberesRemunerativos(resultSet.getDouble("total_haberes_remunerativos"));
                liquidacionEmpleado.setTotalHaberesNoRemunerativos(resultSet.getDouble("total_haberes_no_remunerativos"));
                liquidacionEmpleado.setTotalRetenciones(resultSet.getDouble("total_retenciones"));
                liquidacionEmpleado.setTotalBruto(resultSet.getDouble("total_bruto"));
                liquidacionEmpleado.setEmpleado(new Empleado(
                        resultSet.getInt("EMPLEADO_idEmpleado"),
                        resultSet.getString("Nombre"),
                        resultSet.getString("Apellido"),
                        resultSet.getString("CUIT"),
                        resultSet.getString("FechaNacimiento"),
                        null,
                        new CategoriaEmpleado(resultSet.getInt("CATEGORIA_EMPLEADO_idCategoriaEmpleado"),
                                resultSet.getString("NombreCategoria")),
                        null,
                        null
                ));
                liquidacionEmpleado.setInicioPeriodo(resultSet.getString("inicio_periodo"));
                liquidacionEmpleado.setFinPeriodo(resultSet.getString("fin_periodo"));
                list.add(liquidacionEmpleado);
               /* System.out.println("-----------------------------------------------------------\n" +
                        "id: " + liquidacionEmpleado.getId() + "\n neto: " + liquidacionEmpleado.getImporteNeto()
                        + "\n fecha: " +liquidacionEmpleado.getFechaLiquidacion() + "\n THR: " + liquidacionEmpleado.getTotalHaberesRemunerativos()
                        + "\n THNR: " +liquidacionEmpleado.getTotalHaberesNoRemunerativos() + "\n TR: " + liquidacionEmpleado.getTotalRetenciones()
                        + "\n bruto: " + liquidacionEmpleado.getTotalBruto()
                        + "\nEmpleado: " + liquidacionEmpleado.getEmpleado()
                        + "\n\t categoria: " + liquidacionEmpleado.getEmpleado().getCategoriaEmpleado().getNombre()
                        + "\n inicio: " + liquidacionEmpleado.getInicioPeriodo() + " - fin: " + liquidacionEmpleado.getFinPeriodo()
                );*/
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}