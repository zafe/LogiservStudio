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
                    connection.prepareStatement("INSERT INTO CHEQUE VALUES(?,?,?,?,?,?,?)") :
                    connection.prepareStatement("INSERT INTO CHEQUE VALUES(?,?,?,?,?,?,?,?)");


            preparedStatement.setString(1,cheque.getFechaEmision());
            preparedStatement.setString(2,cheque.getFechaPago());
            preparedStatement.setString(3,cheque.getCodigoBancario());
            preparedStatement.setString(4,cheque.getBanco());
            preparedStatement.setFloat(5,cheque.getMonto());
            preparedStatement.setString(6,cheque.getTipoCheque());
            preparedStatement.setString(7,cheque.getEstadoCheque());

            if (cheque.getFacturaVenta() != null) preparedStatement.setInt(8,cheque.getFacturaVenta().getIdFacturaVenta());

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
            preparedStatement = (cheque.getFacturaVenta() == null ) ? connection.prepareStatement("UPDATE CHEQUES as p " +
                            "    SET p.FechaEmision = ?, p.FechaPago=?, p.CodigoBancario =?, p.Banco=?, p.Monto=?, " +
                    "p.TipoCheque=?, p.Estado=? WHERE p.idCHEQUE = ?")
                    : connection.prepareStatement("UPDATE CHEQUES as p " +
                    "    SET p.FechaEmision = ?, p.FechaPago=?, p.CodigoBancario =?, p.Banco=?, p.Monto=?, " +
                    "p.TipoCheque=?, p.Estado=?, p.FACTURA_VENTA_idFACTURA_VENTA=?" +
                    "    WHERE p.idCHEQUE = ?");
            preparedStatement.setString(1,cheque.getFechaEmision());
            preparedStatement.setString(2,cheque.getFechaPago());
            preparedStatement.setString(3,cheque.getCodigoBancario());
            preparedStatement.setString(4,cheque.getBanco());
            preparedStatement.setFloat(5, cheque.getMonto());
            preparedStatement.setString(6,cheque.getTipoCheque());
            preparedStatement.setString(7,cheque.getEstadoCheque());
            if (cheque.getFacturaVenta() != null )
                preparedStatement.setInt(8,cheque.getFacturaVenta().getIdFacturaVenta());

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
                        resultSet.getString(7),
                        resultSet.getString(8));

                        if (resultSet.getInt(9 ) != 0)
                            cheque.setFacturaVenta(facturaVentaRepository.search(resultSet.getInt(9)));

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
