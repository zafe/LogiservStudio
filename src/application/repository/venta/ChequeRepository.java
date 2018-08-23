package application.repository.venta;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.info.Domicilio;
import application.model.info.Localidad;
import application.model.info.Provincia;
import application.model.venta.Cheque;
import application.repository.info.DomicilioRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ChequeRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    DomicilioRepository domicilioRepository = new DomicilioRepository();//TODO BORRAR ESTA LINEA

    FacturaVentaRepository facturaVentaRepository = new FacturaVentaRepository();


    public void save(Cheque cheque){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement =  (cheque.getFacturaVenta() == null)  ?
                    connection.prepareStatement("INSERT INTO CHEQUE (FechaEmision,FechaPago,CodigoBancario,Banco,Monto,TipoCheque,Estado) VALUES(?,?,?,?,?,?,?)") :
                    connection.prepareStatement("INSERT INTO CHEQUE VALUES(?,?,?,?,?,?,?,?)");


            preparedStatement.setString(1,cheque.getFechaEmision());
            preparedStatement.setString(2,cheque.getFechaPago());
            preparedStatement.setString(3,cheque.getCodigoBancario());
            preparedStatement.setString(4,cheque.getBanco());
            preparedStatement.setFloat(5,cheque.getMonto());
            preparedStatement.setString(6,cheque.getTipoCheque());
            preparedStatement.setString(7,cheque.getEstadoCheque());

            if (cheque.getFacturaVenta() != null) preparedStatement.setInt(8,cheque.getFacturaVenta().getIdFacturaVenta());

            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            String cuerpoMsj = "Cheque  " + cheque.getCodigoBancario() + " agregado correctamente.\n";
            Alerta.alertaInfo("Cheques: ",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Cheque cheque){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = (cheque.getFacturaVenta() == null ) ? connection.prepareStatement("UPDATE CHEQUE as c" +
                            "    SET c.FechaEmision = ?, c.FechaPago=?, c.CodigoBancario =?, c.Banco=?, c.Monto=?, " +
                    "c.TipoCheque=?, c.Estado=? WHERE c.idCHEQUE = ?")
                    : connection.prepareStatement("UPDATE CHEQUE as c " +
                    "    SET c.FechaEmision = ?, c.FechaPago=?, c.CodigoBancario =?, c.Banco=?, c.Monto=?, " +
                    "c.TipoCheque=?, c.Estado=?, c.FACTURA_VENTA_idFACTURA_VENTA=?" +
                    "    WHERE c.idCHEQUE = ?");
            preparedStatement.setString(1,cheque.getFechaEmision());
            preparedStatement.setString(2,cheque.getFechaPago());
            preparedStatement.setString(3,cheque.getCodigoBancario());
            preparedStatement.setString(4,cheque.getBanco());
            preparedStatement.setFloat(5, cheque.getMonto());
            preparedStatement.setString(6,cheque.getTipoCheque());
            preparedStatement.setString(7,cheque.getEstadoCheque());
            if (cheque.getFacturaVenta() == null )
                preparedStatement.setInt(8,cheque.getIdCheque());
            else {
                preparedStatement.setInt(8, cheque.getFacturaVenta().getIdFacturaVenta());
                preparedStatement.setInt(9,cheque.getIdCheque());

            }


            System.out.println("update(Cheque cheque): " + preparedStatement);
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: cheque actualizado";
            String cuerpoMsj = "Cheque: " + cheque.getCodigoBancario() + "modificado correctamente.";
            Alerta.alertaInfo("Cheques", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int idCheque){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM CHEQUE WHERE idCheque=?");
            preparedStatement.setInt(1, idCheque);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public ObservableList<Cheque> view(){
        ObservableList<Cheque> list = FXCollections.observableArrayList();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CHEQUE ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Cheque cheque = new Cheque(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getFloat(6),
                        resultSet.getString(8),
                        resultSet.getString(9));

                        if (resultSet.getInt(7 ) > 0)
                            cheque.setFacturaVenta(facturaVentaRepository.search(resultSet.getInt(7)));

                list.add(cheque);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Cheque getChequeById(int idCheque){

        Cheque cheque = new Cheque();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM CHEQUE WHERE idCHEQUE=?;");
            preparedStatement.setInt(1, idCheque);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                        cheque.setIdCheque(resultSet.getInt("idCHEQUE"));
                        cheque.setFechaEmision(resultSet.getString("FechaEmision"));
                        cheque.setFechaPago(resultSet.getString("FechaPago"));
                        cheque.setCodigoBancario(resultSet.getString("CodigoBancario"));
                        cheque.setBanco(resultSet.getString("Banco"));
                        cheque.setMonto(resultSet.getFloat("Monto"));
                        cheque.setTipoCheque(resultSet.getString("TipoCheque"));
                        cheque.setEstadoCheque(resultSet.getString("Estado"));
                        if (resultSet.getInt("FACTURA_VENTA_idFACTURA_VENTA") != 0)
                            cheque.setFacturaVenta(facturaVentaRepository.search(resultSet.getInt("FACTURA_VENTA_idFACTURA_VENTA")));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cheque;
    }
}
