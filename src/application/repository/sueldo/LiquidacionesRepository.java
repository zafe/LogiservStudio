package application.repository.sueldo;

import java.sql.*;
import java.text.SimpleDateFormat;

import application.database.JDBCConnection;
import application.model.info.Empleado;
import application.model.sueldo.Liquidaciones;
import application.model.venta.Viaje;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LiquidacionesRepository {

    public static ObservableList<Liquidaciones> buscarLiquidaciones(){


        ObservableList<Liquidaciones> liquidaciones = FXCollections.observableArrayList();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = JDBCConnection.getInstanceConnection().createStatement();
            resultSet = statement.executeQuery("SELECT * FROM LIQUIDACION l;");

            while(resultSet.next()){
                Liquidaciones liquidacion = new Liquidaciones();
                liquidacion.setId(resultSet.getInt("idLIQUIDACION"));
                liquidacion.setTotalHaberesRemunerativos(resultSet.getDouble("total_haberes_remunerativos"));
                liquidacion.setTotalHaberesNoRemunerativos(resultSet.getDouble("total_haberes_no_remunerativos"));
                liquidacion.setTotalRetenciones(resultSet.getDouble("total_retenciones"));
                liquidacion.setFechaLiquidacion(resultSet.getString("timestamp"));

                System.out.println("Empleado " + liquidacion.getTotalHaberesRemunerativos() + " "
                        + liquidacion.getTotalHaberesNoRemunerativos() + " " + liquidacion.getTotalRetenciones() + " ");
                liquidaciones.add(liquidacion);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return liquidaciones;

    }

}