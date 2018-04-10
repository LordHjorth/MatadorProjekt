package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
	
	private static String dbUrl = "jdbc:mysql://localhost/PiSU";
	private static String dbUsername = "gruppe25";
	private static String dbPassword = "25";

	public Connection dbConnect() {
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
			return connection;
		} catch (SQLException e) {
			System.err.print(e.getMessage() + " Noob - ingen sql bandit at hente her!");
		} catch (Exception e) {
			System.err.print(e.getMessage() + " Noob - nu er du bare en meget grim og dum abe :-)");
		}
		return null;
	}

}
