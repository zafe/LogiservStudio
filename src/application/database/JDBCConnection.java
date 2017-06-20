package application.database;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JDBCConnection {
	
	private static Connection connection;

	static Properties properties = new Properties();
	
	protected JDBCConnection() {}
	
	public static Connection getInstanceConnection(){
		
		if(connection == null)
		{
		try {
			properties.load(new FileInputStream("db.properties"));
			
			String url = properties.getProperty("url");
			String user = properties.getProperty("user");
			String password = properties.getProperty("password");
			
			connection = DriverManager.getConnection(url, user, password);
			
			System.out.println("Conectado a " + url);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e){
			e.printStackTrace();
		}
		
		}
		
		return connection;
	}

	
}
