package application.repository.compra;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.Articulo;
import application.model.compra.CategoriaArticulo;
import com.sun.corba.se.impl.encoding.TypeCodeInputStream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class ArticuloRepository {
    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(Articulo articulo){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("INSERT INTO ARTICULO values(?,?,?,?,?,?)");
            preparedStatement.setString(1, null);
            preparedStatement.setString(2,articulo.getMarca());
            preparedStatement.setString(3,articulo.getModelo());
            preparedStatement.setString(4,articulo.getDescripcion());
            preparedStatement.setInt(5, articulo.getCategoria().getIdCategoriaArticulo());
            preparedStatement.setInt(6,articulo.getStock());
            preparedStatement.executeUpdate();
            String cuerpoMsj = "Artículo '" + articulo.getDescripcion() + "' agregado correctamente.\n";
            Alerta.alertaInfo("Artículos",cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void update(Articulo articulo){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement(
                    "UPDATE ARTICULO " +
                        "SET Marca=?, Modelo=?, Descripcion=?, CATEGORIA_ARTICULO_idCategoriaArticulo=?, stock=? " +
                    "WHERE idArticulo=?");
            preparedStatement.setString(1,articulo.getMarca());
            preparedStatement.setString(2,articulo.getModelo());
            preparedStatement.setString(3,articulo.getDescripcion());
            preparedStatement.setInt(4,articulo.getCategoria().getIdCategoriaArticulo());
            preparedStatement.setInt(5,articulo.getStock());
            preparedStatement.setInt(6,articulo.getIdArticulo());
            preparedStatement.execute();
            String headerMsj="Actualización: artículo actualizado";
            String cuerpoMsj = "Artículo: " + articulo.getDescripcion() + " modificado correctamente.";
            Alerta.alertaInfo("Artículos", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void delete(int id){
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement(
                    "DELETE FROM ARTICULO WHERE idArticulo=?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            } catch (SQLException e) {
            e.printStackTrace();
        }


    }
    public ObservableList<Articulo> view(){
        ObservableList<Articulo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT a.idArticulo, a.Marca, a.Modelo," +
                    " a.Descripcion, ca.idCategoriaArticulo, ca.NombreCategoria, a.stock" +
                    "     FROM ARTICULO a, CATEGORIA_ARTICULO ca " +
                    "    WHERE a.CATEGORIA_ARTICULO_idCategoriaArticulo = ca.idCategoriaArticulo ");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Articulo articulo = new Articulo(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        new CategoriaArticulo(resultSet.getInt(5),resultSet.getString(6)),
                        resultSet.getInt(7));
                list.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public ObservableList<Articulo>  getArticulosByCategoria(int idCategoria){
        ObservableList<Articulo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement=connection.prepareStatement("SELECT a.idArticulo, a.Marca, a.Modelo, "  +
                    "a.Descripcion, ca.idCategoriaArticulo, ca.NombreCategoria, a.stock " +
                    "FROM ARTICULO a, CATEGORIA_ARTICULO ca " +
                    "WHERE a.CATEGORIA_ARTICULO_idCategoriaArticulo = ca.idCategoriaArticulo AND CATEGORIA_ARTICULO_idCategoriaArticulo=?");
            preparedStatement.setInt(1, idCategoria);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                Articulo articulo = new Articulo(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        new CategoriaArticulo(resultSet.getInt(5),resultSet.getString(6)),
                        resultSet.getInt(7));
                list.add(articulo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;

    }
    public void updateStock(Articulo articulo){
        try {
            connection = JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement(
                    "UPDATE ARTICULO " +
                            "SET stock=? " +
                            "WHERE idArticulo=?");
            preparedStatement.setInt(1,articulo.getStock());
            preparedStatement.setInt(2,articulo.getIdArticulo());
            preparedStatement.execute();
            String headerMsj="Actualización: Stock Articulo actualizado";
            String cuerpoMsj = "Stock Artículo: " + articulo.getDescripcion() + " actualizado correctamente.";
            Alerta.alertaInfo("Stock de Artículos", headerMsj, cuerpoMsj);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
