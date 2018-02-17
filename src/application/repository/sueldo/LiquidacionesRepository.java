package application.repository.sueldo;

import java.sql.*;
import java.text.SimpleDateFormat;

import application.database.JDBCConnection;
import application.model.info.CategoriaEmpleado;
import application.model.info.Empleado;
import application.model.sueldo.ConceptoSueldo;
import application.model.sueldo.LiquidacionEmpleado;
import application.model.sueldo.Liquidaciones;
import application.model.venta.Viaje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LiquidacionesRepository {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public static ObservableList<Liquidaciones> buscarLiquidaciones(){


        ObservableList<Liquidaciones> liquidaciones = FXCollections.observableArrayList();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = JDBCConnection.getInstanceConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM LIQUIDACION");
            while(resultSet.next()){
                Liquidaciones liquidacion = new Liquidaciones();
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

    public ObservableList<LiquidacionEmpleado> getEmpleadosLiquidadosByidLiquidacion(int idLIQUIDACION){
        ObservableList<LiquidacionEmpleado> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT EMPLEADO.idEmpleado, EMPLEADO.Apellido, EMPLEADO.Nombre, " +
                    " CATEGORIA_EMPLEADO.NombreCategoria, LIQUIDACION_EMPLEADO.total_haberes_remunerativos, " +
                    "LIQUIDACION_EMPLEADO.total_haberes_no_remunerativos, LIQUIDACION_EMPLEADO.total_retenciones " +
                    "                    FROM LIQUIDACION_EMPLEADO\n" +
                    "                    INNER JOIN\n" +
                    "                    EMPLEADO ON EMPLEADO.idEmpleado = LIQUIDACION_EMPLEADO.EMPLEADO_idEmpleado\n" +
                    "                    INNER JOIN\n" +
                    "                            CATEGORIA_EMPLEADO ON CATEGORIA_EMPLEADO.idCategoriaEmpleado = EMPLEADO.CATEGORIA_EMPLEADO_idCategoriaEmpleado\n" +
                    "                    WHERE\n" +
                    "                    LIQUIDACION_EMPLEADO.idLiquidacionEmpleado like ?");
            preparedStatement.setInt(1, idLIQUIDACION);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                LiquidacionEmpleado empleadosLiquidados = new LiquidacionEmpleado();
                Empleado empleado = new Empleado();
                empleado.setIdEmpleado(resultSet.getInt(1));
                empleado.setApellido(resultSet.getString(2));
                empleado.setNombre(resultSet.getString(3));
                empleado.setCategoria(resultSet.getString(4));
                empleadosLiquidados.setEmpleado(empleado);
                empleadosLiquidados.setTotalHaberesRemunerativos(resultSet.getDouble(5));
                empleadosLiquidados.setTotalHaberesNoRemunerativos(resultSet.getDouble(6));
                empleadosLiquidados.setTotalRetenciones(resultSet.getDouble(7));
                list.add(empleadosLiquidados);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}