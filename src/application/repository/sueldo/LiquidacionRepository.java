package application.repository.sueldo;

import java.math.BigDecimal;
import java.sql.*;

import application.database.JDBCConnection;
import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.ConceptoCalculado;
import application.model.sueldo.DetalleLiquidacionEmpleado;
import application.model.sueldo.LiquidacionEmpleado;
import application.model.sueldo.Liquidacion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LiquidacionRepository {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    private int lastIdLiquidacionEmpleado;

    public static ObservableList<Liquidacion> buscarLiquidaciones(){


        ObservableList<Liquidacion> liquidaciones = FXCollections.observableArrayList();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = JDBCConnection.getInstanceConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM LIQUIDACION");
            while(resultSet.next()){
                Liquidacion liquidacion = new Liquidacion();
                liquidacion.setId(resultSet.getInt("idLIQUIDACION"));
                liquidacion.setTotalHaberesRemunerativos(resultSet.getDouble("total_haberes_remunerativos"));
                liquidacion.setTotalHaberesNoRemunerativos(resultSet.getDouble("total_haberes_no_remunerativos"));
                liquidacion.setTotalRetenciones(resultSet.getDouble("total_retenciones"));
                liquidacion.setFechaLiquidacion(resultSet.getString("timestamp"));
                liquidaciones.add(liquidacion);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return liquidaciones;

    }

    /**
     * Crea una liquidacion
     *
     * @return Liquidacion creada
     */
    public Liquidacion newLiquidacion(Liquidacion liquidacion) {

        try {
            Connection connection = JDBCConnection.getInstanceConnection();
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO LIQUIDACION (timestamp, total_haberes_remunerativos, " +
                    "total_haberes_no_remunerativos, total_retenciones ) VALUES (NOW(), ? , ?, ?);");
            preparedStatement.setDouble(1,liquidacion.getTotalHaberesRemunerativos());
            preparedStatement.setDouble(2,liquidacion.getTotalHaberesNoRemunerativos());
            preparedStatement.setDouble(3,liquidacion.getTotalRetenciones());
            preparedStatement.executeUpdate();
            System.out.println("SQL LIQUIDACION INSERT: " + preparedStatement);
            resultSet = preparedStatement.executeQuery("SELECT idLiquidacion, timestamp " +
                    "FROM LIQUIDACION " +
                    "WHERE idLiquidacion = LAST_INSERT_ID();");
            if (resultSet.next()) {
                liquidacion.setId(resultSet.getInt("idLiquidacion"));
                liquidacion.setFechaLiquidacion(resultSet.getString("timestamp"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //crear una liquidacion_empleado por cada empleado, es asociado a una liquidacion
        for (LiquidacionEmpleado liquidacionEmpleado : liquidacion.getLiquidacionesEmpleados()) {

            try {

                Connection connection = JDBCConnection.getInstanceConnection();
                PreparedStatement preparedStatement  = connection.prepareStatement("INSERT INTO LIQUIDACION_EMPLEADO " +
                        "(importe_neto,total_haberes_remunerativos, total_haberes_no_remunerativos,total_retenciones, " +
                        "total_bruto, EMPLEADO_idEmpleado, inicio_periodo, fin_periodo, LIQUIDACION_idLiquidacion, " +
                        "fecha_liquidacion)" +
                        " VALUES (?,?,?,?,?,?,?,?,?,NOW());");
                preparedStatement.setDouble(1,liquidacionEmpleado.getImporteNeto());
                preparedStatement.setDouble(2,liquidacionEmpleado.getTotalHaberesRemunerativos());
                preparedStatement.setDouble(3,liquidacionEmpleado.getTotalHaberesNoRemunerativos());
                preparedStatement.setDouble(4,liquidacionEmpleado.getTotalRetenciones());
                preparedStatement.setDouble(5,liquidacionEmpleado.getTotalBruto());
                preparedStatement.setInt(6,liquidacionEmpleado.getEmpleado().getIdEmpleado());
                preparedStatement.setString(7,liquidacionEmpleado.getInicioPeriodo());
                preparedStatement.setString(8,liquidacionEmpleado.getFinPeriodo());
                preparedStatement.setInt(9,liquidacion.getId());
                preparedStatement.executeUpdate();
                resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
                if (resultSet.next()){
                    liquidacionEmpleado.setId(resultSet.getInt(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            liquidacionEmpleado.setId(getLastIdLiquidacionEmpleado());
            System.out.println("Empleado a liquidar: " + liquidacionEmpleado.getId());

            //grabacion de detalle_liquidacion, es decir ConceptoCalculado
            for (ConceptoCalculado conceptoCalculado : liquidacionEmpleado.getConceptosLiquidados()) {
                try {

                    Connection connection = JDBCConnection.getInstanceConnection();
                    preparedStatement  = connection.prepareStatement("INSERT INTO DETALLE_LIQUIDACION_EMPLEADO " +
                            "(cantidad,monto,LiquidacionEmpleado_idLiquidacionEmpleado,CONCEPTO_SUELDO_idCodigoConcepto)" +
                            " VALUES (?,?,?,?);");
                    preparedStatement.setDouble(1,conceptoCalculado.getFactor());
                    preparedStatement.setDouble(2,conceptoCalculado.getMontoCalculado());
                    preparedStatement.setInt(3,liquidacionEmpleado.getId());
                    preparedStatement.setInt(4,conceptoCalculado.getIdConceptoSueldo());//todo funcionara???
                    System.out.println("SQL CONCEPTO CALCULADO: " + preparedStatement);
                    preparedStatement.executeUpdate();
                   // resultSet = preparedStatement.executeQuery("SELECT LAST_INSERT_ID()");
                    //if (resultSet.next()){
                    //    conceptoCalculado.setId(resultSet.getInt(1));
                     //   conceptoCalculado.setIdConceptoSueldo();
                    // }
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return liquidacion;
    }

    public ObservableList<LiquidacionEmpleado> getEmpleadosLiquidadosByidLiquidacion(int idLIQUIDACION){
        ObservableList<LiquidacionEmpleado> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT EMPLEADO.idEmpleado, EMPLEADO.Apellido, EMPLEADO.Nombre, " +
                    "CATEGORIA_EMPLEADO.idCategoriaEmpleado, CATEGORIA_EMPLEADO.NombreCategoria, LIQUIDACION_EMPLEADO.total_haberes_remunerativos, " +
                    "LIQUIDACION_EMPLEADO.total_haberes_no_remunerativos, LIQUIDACION_EMPLEADO.total_retenciones " +
                    "                    FROM LIQUIDACION_EMPLEADO" +
                    "                    INNER JOIN" +
                    "                    EMPLEADO ON EMPLEADO.idEmpleado = LIQUIDACION_EMPLEADO.EMPLEADO_idEmpleado" +
                    "                    INNER JOIN" +
                    "                            CATEGORIA_EMPLEADO ON CATEGORIA_EMPLEADO.idCategoriaEmpleado = EMPLEADO.CATEGORIA_EMPLEADO_idCategoriaEmpleado" +
                    "                    WHERE" +
                    "                    LIQUIDACION_EMPLEADO.idLiquidacionEmpleado like ?");
            preparedStatement.setInt(1, idLIQUIDACION);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                LiquidacionEmpleado empleadosLiquidados = new LiquidacionEmpleado();
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(resultSet.getInt(1));
                empleado.setApellido(resultSet.getString(2));
                empleado.setNombre(resultSet.getString(3));
                empleado.setCategoriaEmpleado(new CategoriaEmpleado(resultSet.getInt(4),resultSet.getString(5)));
                empleadosLiquidados.setEmpleado(empleado);
                empleadosLiquidados.setTotalHaberesRemunerativos(resultSet.getDouble(6));
                empleadosLiquidados.setTotalHaberesNoRemunerativos(resultSet.getDouble(7));
                empleadosLiquidados.setTotalRetenciones(resultSet.getDouble(8));
                list.add(empleadosLiquidados);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
