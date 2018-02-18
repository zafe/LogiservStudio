package application.repository.calculo;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.calculo.OrigenDestino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrigenDestinoRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    public ObservableList<OrigenDestino> view(){
        ObservableList<OrigenDestino> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("" +
                    " SELECT o.idOrigen_Destino, f.nombre, i.nombre, o.distanciakm " +
                    "FROM origen_destino AS o, finca AS f, ingenio AS i " +
                    "WHERE o.FINCA_idFinca = f.idfinca AND o.INGENIO_idIngenio = i.idIngenio");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                OrigenDestino origenDestino = new OrigenDestino();
                origenDestino.setIdOrigenDestino(resultSet.getInt(1));
                origenDestino.setNombreFinca(resultSet.getString(2));
                origenDestino.setNombreIngenio(resultSet.getString(3));
                origenDestino.setDistanciaKM(resultSet.getFloat(4));
                list.add(origenDestino);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
    public void delete(int idOrigenDestino) {
        try{
           connection= JDBCConnection.getInstanceConnection();
           preparedStatement= connection.prepareStatement("DELETE FROM origen_destino WHERE idOrigen_Destino=?");
           preparedStatement.setInt(1,idOrigenDestino);
           preparedStatement.execute();
           }catch (SQLException ex){
             ex.printStackTrace();
           }
    }
    public void save(OrigenDestino origenDestino) {
        connection= JDBCConnection.getInstanceConnection();
        try {
            preparedStatement =connection.prepareStatement("INSERT INTO origen_destino " +
            "(idOrigen_Destino, DistanciaKM, FINCA_idFinca, INGENIO_idIngenio) VALUES " +
            "(?,?," +
            "(SELECT idFinca FROM finca WHERE nombre=?)," +
            "(SELECT idIngenio FROM ingenio WHERE nombre=?));");
            preparedStatement.setString(1, null);
            preparedStatement.setFloat(2, origenDestino.getDistanciaKM());
            preparedStatement.setString(3, origenDestino.getNombreFinca());
            preparedStatement.setString(4, origenDestino.getNombreIngenio());
            preparedStatement.execute();
            String cuerpoMsj = "Distancia " + origenDestino.getNombreFinca() + " - "+ origenDestino.getNombreIngenio() + " creada correctamente.";
            Alerta.alertaInfo("Distancia entre Finca e Ingenio", cuerpoMsj);
            }catch (SQLException ex){
                ex.printStackTrace();
            }
    }
    public void update(OrigenDestino origenDestino) {
    try {
        connection = JDBCConnection.getInstanceConnection();
        preparedStatement= connection.prepareStatement("" +
        "UPDATE origen_destino " +
        "set DistanciaKM=?, " +
        "FINCA_idFinca=(SELECT idFinca from finca where nombre=?), " +
        "INGENIO_idIngenio=(SELECT idIngenio from ingenio where nombre=?)" +
        "WHERE idOrigen_Destino=?");
        preparedStatement.setFloat(1,origenDestino.getDistanciaKM());
        preparedStatement.setString(2,origenDestino.getNombreFinca());
        preparedStatement.setString(3,origenDestino.getNombreIngenio());
        preparedStatement.setInt(4,origenDestino.getIdOrigenDestino());
        preparedStatement.execute();
        String headerMsj="Actualización: Distancia actualizada";
        String cuerpoMsj = "Distancia: " + origenDestino.getNombreFinca() + " - "+ origenDestino.getNombreIngenio() + " modificada correctamente.";
        Alerta.alertaInfo("Distancias", headerMsj, cuerpoMsj);
    } catch (SQLException e) {
        e.printStackTrace();
    }
  }

  public double getDistanciaByIds(int idFinca, int idIngenio){

       Double distancia = 0.0;

      try {
          connection= JDBCConnection.getInstanceConnection();
          preparedStatement=connection.prepareStatement("" +
                  " SELECT od.DistanciaKM FROM ORIGEN_DESTINO od " +
                  "WHERE od.FINCA_idFinca = ? AND od.INGENIO_idIngenio = ?;");
          preparedStatement.setInt(1,idFinca);
          preparedStatement.setInt(2, idIngenio);
          resultSet = preparedStatement.executeQuery();

          if (resultSet.next())
              distancia = resultSet.getDouble(1);


      } catch (SQLException e) {
          e.printStackTrace();
      }

      return distancia;
  }

  public int getIdByFincaIngenio(int idFinca, int idIngenio){
        int idOrigenDestino = 0;

      try {
          connection= JDBCConnection.getInstanceConnection();
          preparedStatement=connection.prepareStatement("SELECT od.IdOrigen_Destino " +
                  "FROM ORIGEN_DESTINO od WHERE od.FINCA_idFinca = ? AND od.INGENIO_idIngenio = ?;");
          preparedStatement.setInt(1,idFinca);
          preparedStatement.setInt(2, idIngenio);
          resultSet = preparedStatement.executeQuery();

          if (resultSet.next())
              idOrigenDestino = resultSet.getInt(1);


      } catch (SQLException e) {
          e.printStackTrace();
      }

        return idOrigenDestino;
  }
}
