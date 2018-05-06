package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The Class Connector.
 * 
 * @author Rasmus
 */
public class Connector {

	/**
	 * Gets the connection.
	 *
	 * @return the connection
	 * @throws Exception the exception
	 */
	public static Connection getConnection() throws Exception {

		String url = "jdbc:mysql://localhost/MatadorProjekt";
		String username = "root";
		String password = "root";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection conn = DriverManager.getConnection(url, username, password);
			System.out.println("SUCCESS!");
			return conn;
		} catch (SQLException e) {
			System.err.print(e.getMessage());
		} catch (Exception e) {
			System.out.println(e);
		}
		System.out.println("CONNECTION ERROR");
		return null;
	}
}
