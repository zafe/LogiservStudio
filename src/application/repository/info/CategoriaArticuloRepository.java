package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.compra.CategoriaArticulo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import java.sql.*;

public class CategoriaArticuloRepository {

    Connection connection;
    PreparedStatement preparedStatement;
    ResultSet resultSet;

    public void save(CategoriaArticulo categoriaArticulo) {
//        System.out.println("Paso por aqui sin romperme");
//        connection;
        connection= JDBCConnection.getInstanceConnection();
        try {            System.out.println("conexion: "+ JDBCConnection.getInstanceConnection().isClosed());

            preparedStatement =connection.prepareStatement("INSERT INTO CATEGORIA_ARTICULO(NombreCategoria) VALUES(?)");
            //            preparedStatement.setNull(1, Types.INTEGER);
            preparedStatement.setString(1, categoriaArticulo.getNombre());
            preparedStatement.execute();
            String cuerpoMsj = "Categoría " + categoriaArticulo.getNombre() + "creada correctamente.";
            Alerta.alertaInfo("Categoría de Artículos", cuerpoMsj);

        }catch (SQLException ex){
            System.err.println("salio todo mal viejo");
            ex.printStackTrace();
        }
    }

    public void update(CategoriaArticulo categoriaArticulo) {
        try{
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("UPDATE CATEGORIA_ARTICULO SET NombreCategoria=? " +
                    " where idCategoriaArticulo=?");
            preparedStatement.setString(1, categoriaArticulo.getNombre());
            preparedStatement.setInt(2, categoriaArticulo.getIdCategoriaArticulo());

            String headerMsj = "Actualización: categoría de artículo realizada";
            String cuerpoMsj =  "Categoría '" + categoriaArticulo.getNombre() + "' modificada correctamente.";
            Alerta.alertaInfo("Categoría Artículo",headerMsj,cuerpoMsj);
        }catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void delete(CategoriaArticulo categoriaArticulo) {
        System.out.println(categoriaArticulo.getIdCategoriaArticulo());
        try{
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement= connection.prepareStatement("DELETE FROM CATEGORIA_ARTICULO WHERE idCategoriaArticulo=?");
            preparedStatement.setInt(1,categoriaArticulo.getIdCategoriaArticulo());
            preparedStatement.execute();
            }catch (SQLException ex){
            ex.printStackTrace();
        }

    }

    public ObservableList<CategoriaArticulo> viewAll() {
        ObservableList<CategoriaArticulo> list = FXCollections.observableArrayList();
        try {
            connection= JDBCConnection.getInstanceConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM CATEGORIA_ARTICULO");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                CategoriaArticulo categoriaArticulo = new CategoriaArticulo();
                categoriaArticulo.setIdCategoriaArticulo(resultSet.getInt(1));
                categoriaArticulo.setNombre(resultSet.getString(2));
                list.add(categoriaArticulo);
            }
        }catch (SQLException ex){
            ex.printStackTrace();
        }
        return list;
    }
}
