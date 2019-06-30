package application.repository.info;

import application.comunes.Alerta;
import application.database.JDBCConnection;
import application.model.info.Empleado;
import application.model.info.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.swing.undo.UndoableEditSupport;
import java.sql.*;

public class UsuarioRepository {
	Connection connection;
	PreparedStatement preparedStatement;
	ResultSet resultSet;
	public static ObservableList<Usuario> buscarUsuarios(){

		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		Statement statement = null;
		ResultSet resultSet = null;

		
		try {
			statement = JDBCConnection.getInstanceConnection().createStatement();
			resultSet = statement.executeQuery("SELECT idUsuario, NombreUsuario, Password, idEmpleado, Nombre, Apellido " +
					"FROM USUARIO INNER JOIN EMPLEADO " +
					"ON Empleado_idEmpleado=idEmpleado");

			while(resultSet.next()){
				Usuario usuario = new Usuario();
				usuario.setIdUsuario(resultSet.getInt(1));
				usuario.setNombre_usuario(resultSet.getString(2));
				usuario.setPassword(resultSet.getString(3));
				usuario.setEmpleado(new Empleado());
				usuario.getEmpleado().setIdEmpleado(resultSet.getInt(4));
				usuario.getEmpleado().setNombre(resultSet.getString(5));
				usuario.getEmpleado().setApellido(resultSet.getString(6));
				usuarios.add(usuario);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}
	
	public void create(Usuario usuario){
		PreparedStatement preparedstatement;
		try {
			preparedstatement = JDBCConnection.getInstanceConnection().prepareStatement("INSERT INTO USUARIO VALUES (?,?,MD5(?),?,?)");
			preparedstatement.setString(1, null);
			preparedstatement.setString(2, usuario.getNombre_usuario());
			preparedstatement.setString(3, usuario.getPassword());
			preparedstatement.setInt(4, usuario.getEmpleado().getIdEmpleado());
			preparedstatement.setInt(5, usuario.getRoot());
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void edit(Usuario usuario){
		PreparedStatement preparedstatement;
		try {
			preparedstatement = JDBCConnection.getInstanceConnection().prepareStatement(
					"UPDATE USUARIO SET NombreUsuario=?, Password=MD5(?), Empleado_idEmpleado=? WHERE idUsuario=?");
			preparedstatement.setString(1, usuario.getNombre_usuario());
			preparedstatement.setString(2, usuario.getPassword());
			preparedstatement.setInt(3, usuario.getEmpleado().getIdEmpleado());
			preparedstatement.setInt(4, usuario.getIdUsuario());
			preparedstatement.executeUpdate();
			String headerMsj="Actualización: usuario actualizado";
			String cuerpoMsj = "Usuario: " + usuario.getNombre_usuario() + " modificado correctamente.";
			Alerta.alertaInfo("Usuarios", headerMsj, cuerpoMsj);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public static void delete(Usuario usuario){
		PreparedStatement preparedstatement;
		try {
			preparedstatement = JDBCConnection.getInstanceConnection().prepareStatement(
					"DELETE FROM USUARIO WHERE idUsuario=?");
			preparedstatement.setInt(1, usuario.getIdUsuario());
			preparedstatement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	public boolean login(String nombreUsuario, String pass){
		boolean okLogin=false;
		try {
			connection = JDBCConnection.getInstanceConnection();
			preparedStatement=connection.prepareStatement("" +
					"SELECT * FROM USUARIO WHERE NombreUsuario=? AND Password=?");
			preparedStatement.setString(1,nombreUsuario);
			preparedStatement.setString(2, pass);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next())
				okLogin = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return okLogin;
	}
}
