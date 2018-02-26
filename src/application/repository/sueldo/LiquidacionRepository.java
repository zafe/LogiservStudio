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
            Statement statement = JDBCConnection.getInstanceConnection().createStatement();
            statement.executeUpdate("INSERT INTO LIQUIDACION (timestamp) VALUES (NOW());");
            resultSet = statement.executeQuery("SELECT idLiquidacion, timestamp " +
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
        //set ID liquidacion
        liquidacion.setId(getLastID());

        //crear una liquidacion_empleado por cada empleado, es asociado a una liquidacion
        for (LiquidacionEmpleado liquidacionEmpleado : liquidacion.getLiquidacionesEmpleados()) {

            try {
                preparedStatement = connection.prepareStatement("INSERT INTO LIQUIDACION_EMPLEADO " +
                        " VALUES (?,?,?,?,?,?,?,?,?,?,?);");
                preparedStatement.setString(1, null);
                preparedStatement.setString(2, liquidacionEmpleado.getFechaLiquidacion());
                preparedStatement.setDouble(3, liquidacionEmpleado.getImporteNeto());
                preparedStatement.setDouble(4, liquidacionEmpleado.getTotalHaberesRemunerativos());
                preparedStatement.setDouble(5, liquidacionEmpleado.getTotalHaberesNoRemunerativos());
                preparedStatement.setDouble(6, liquidacionEmpleado.getTotalRetenciones());
                preparedStatement.setDouble(7, liquidacionEmpleado.getTotalBruto());
                preparedStatement.setInt(8, liquidacionEmpleado.getEmpleado().getIdEmpleado());
                preparedStatement.setString(9, liquidacionEmpleado.getInicioPeriodo());
                preparedStatement.setString(10, liquidacionEmpleado.getFinPeriodo());
                preparedStatement.setInt(11, liquidacion.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            liquidacionEmpleado.setId(getLastIdLiquidacionEmpleado());
            System.out.println("Empleado a liquidar: " + liquidacionEmpleado.getId());

            //grabacion de detalle_liquidacion, es decir ConceptoCalculado
            for (ConceptoCalculado conceptoCalculado : liquidacionEmpleado.getConceptosLiquidados()) {
                try {
                    System.out.println(conceptoCalculado);
                    preparedStatement = connection.prepareStatement("INSERT INTO DETALLE_LIQUIDACION_EMPLEADO " +
                            " VALUES (?,?,?,?,?);");
                    preparedStatement.setString(1, null);
                    preparedStatement.setDouble(2, conceptoCalculado.getFactor());
                    preparedStatement.setInt(3, liquidacionEmpleado.getId());
                    preparedStatement.setDouble(4, conceptoCalculado.getMontoCalculado());
                    preparedStatement.setInt(5, conceptoCalculado.getIdConceptoSueldo());
                    preparedStatement.executeUpdate();
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

    public int getLastID() {
        int lastId=0;
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT MAX(idLIQUIDACION) FROM liquidacion;");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                lastId= resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }

    private int getLastIdLiquidacionEmpleado() {
        int lastId=0;
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT MAX(idLiquidacionEmpleado) FROM Liquidacion_Empleado;");
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next())
                lastId= resultSet.getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lastId;
    }
}
