package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	/*
	 * @author Gruppe B
	 */
	private static Connection conn = null;

	public static Connection getConnection() throws Exception {

		String url = "jdbc:mysql://localhost/MatadorProjekt";
		String username = "root";
		String password = "root";
		if (conn == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(url, username, password);
				System.out.println("SUCCESS!");
				return conn;
			} catch (SQLException e) {
				System.err.print(e.getMessage());
				System.out.println("HEJ");
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		return conn;
	}
}
