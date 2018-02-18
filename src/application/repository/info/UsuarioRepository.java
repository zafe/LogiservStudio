package application.repository.info;

import application.model.info.Empleado;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.database.JDBCConnection;
import application.model.info.Usuario;

public class UsuarioRepository {

	public static ObservableList<Usuario> buscarUsuarios(){
		
		ObservableList<Usuario> usuarios = FXCollections.observableArrayList();
		Statement statement = null;
		ResultSet resultSet = null;
		
		try {
			statement = JDBCConnection.getInstanceConnection().createStatement();
			resultSet = statement.executeQuery("select * from USUARIO");
			
			while(resultSet.next()){
			Usuario usuario = new Usuario();
			usuario.setIdUsuario(resultSet.getInt("idUsuario"));
			usuario.setNombre_usuario(resultSet.getString("NombreUsuario"));
			usuario.setPassword(resultSet.getString("Password"));
			usuario.setEmpleado(new Empleado());
			usuario.getEmpleado().setIdEmpleado(resultSet.getInt("Empleado_idEmpleado"));
			usuarios.add(usuario);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuarios;
		
	}
	
	public static void create(String nombre, String password, int idEmpleado){
		PreparedStatement preparedstatement;
		
		try {
			preparedstatement = JDBCConnection.getInstanceConnection().prepareStatement("INSERT INTO USUARIO (NombreUsuario, Password, Empleado_idEmpleado) VALUES (?,?,?)");
			preparedstatement.setString(1, nombre);
			preparedstatement.setString(2, password);
			preparedstatement.setInt(3, idEmpleado);		
			preparedstatement.executeUpdate();
			preparedstatement.close();
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void edit(Usuario usuario, int idEmpleado){
		PreparedStatement preparedstatement;

		try {
			preparedstatement = JDBCConnection.getInstanceConnection().prepareStatement(
					"UPDATE USUARIO SET NombreUsuario=?, Password=?, Empleado_idEmpleado=? WHERE idUsuario=?)");
			preparedstatement.setString(1, usuario.getNombre_usuario());
			preparedstatement.setString(2, usuario.getPassword());
			preparedstatement.setInt(3, idEmpleado);
			preparedstatement.setInt(4, usuario.getIdUsuario());
			preparedstatement.executeUpdate();
			preparedstatement.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
			preparedstatement.close();


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
