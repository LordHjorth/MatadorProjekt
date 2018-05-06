package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	/*
	 * @author Gruppe B
	 */
	public static Connection getConnection() throws Exception {

		String url = "jdbc:mysql://localhost/MatadorProjekt";
		String username = "root";
		String password = "";
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
