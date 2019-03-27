package application.repository.venta;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.venta.Cheque;
import application.model.venta.PagoCheque;
import application.repository.info.DomicilioRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PagoChequeRepository {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;


    public void save(PagoCheque pagoCheque){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO PAGO_CHEQUE " +
                    "(FechaCobro,Comision,SaldoEfectivo,CHEQUE_idCHEQUE) VALUES (?,?,?,?)") ;


            preparedStatement.setString(1,pagoCheque.getFechaCobro());
            preparedStatement.setFloat(2,pagoCheque.getComision());
            preparedStatement.setFloat(3,pagoCheque.getSaldoEfectivo());
            preparedStatement.setInt(4,pagoCheque.getCheque().getIdCheque());


            System.out.println(preparedStatement);
            preparedStatement.executeUpdate();

            String cuerpoMsj = "Pago Cheque agregado correctamente.\n";
            Alerta.alertaInfo("Cheques: ",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(PagoCheque pagoCheque){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("UPDATE PAGO_CHEQUE as p " +
                    "    SET p.FechaCobro = ?, p.Comision=?, p.SaldoEfectivo =?, p.CHEQUE_idCHEQUE=? WHERE p.idPAGO_CHEQUE = ?");

            preparedStatement.setString(1,pagoCheque.getFechaCobro());
            preparedStatement.setFloat(2,pagoCheque.getComision());
            preparedStatement.setFloat(3,pagoCheque.getSaldoEfectivo());
            preparedStatement.setInt(4,pagoCheque.getCheque().getIdCheque());


            System.out.println("update(PagoCheque pagocheque): " + preparedStatement);
            preparedStatement.executeUpdate();
            String headerMsj="Actualización: Pago cheqeu actualizado";
            String cuerpoMsj = "Pago Cheque: modificado correctamente.";
            Alerta.alertaInfo("Pago Cheques", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int idPagoCheque){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("DELETE FROM PAGO_CHEQUE WHERE idPAGO_CHEQUE=?");
            preparedStatement.setInt(1, idPagoCheque);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ObservableList<PagoCheque> view(){
        ObservableList<PagoCheque> list = FXCollections.observableArrayList();
        ChequeRepository chequeRepository = new ChequeRepository();
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM PAGO_CHEQUE ");
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){


                PagoCheque pagoCheque = new PagoCheque(
                        resultSet.getInt("idPAGO_CHEQUE"),
                        resultSet.getString("FechaCobro"),
                        resultSet.getFloat("Comision"),
                        resultSet.getFloat("SaldoEfectivo"),
                        chequeRepository.getChequeById(resultSet.getInt("CHEQUE_idCHEQUE")));

                list.add(pagoCheque);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public PagoCheque getPagoChequeById(int idPagoCheque){

        PagoCheque pagoCheque = new PagoCheque();
        ChequeRepository chequeRepository = new ChequeRepository();

        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "SELECT * FROM PAGO_CHEQUE WHERE idPAGO_CHEQUE=?;");
            preparedStatement.setInt(1, idPagoCheque);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){

                        pagoCheque.setIdPagoCheque(resultSet.getInt("idPAGO_CHEQUE"));
                        pagoCheque.setFechaCobro(resultSet.getString("FechaEmision"));
                        pagoCheque.setComision(resultSet.getFloat("FechaPago"));
                        pagoCheque.setSaldoEfectivo(resultSet.getFloat("CodigoBancario"));
                        int idCheque = resultSet.getInt("CHEQUE_idCHEQUE");
                        pagoCheque.setCheque(chequeRepository.getChequeById(idCheque));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pagoCheque;
    }
}
