package application.repository.info;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
			usuario.setEmpleado(resultSet.getInt("idUsuario"));
			System.out.println("USUARIO ID!!!!! ->" + usuario.getIdUsuario());
			usuarios.add(usuario);
			}
			
			System.out.println(usuarios.get(0).getNombre_usuario());
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return usuarios;
		
	}
	
}
