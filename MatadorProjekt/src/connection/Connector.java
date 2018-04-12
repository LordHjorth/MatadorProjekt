package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {

	/*
	 * private static String dbUrl = "jdbc:mysql://localhost/EmptyBandit"; private
	 * static String dbUsername = "root"; private static String dbPassword =
	 * "gruppe25"; private int i = 0;
	 * 
	 * public Connection dbConnect() { Connection connection = null; try {
	 * Class.forName("com.mysql.jdbc.Driver"); connection =
	 * DriverManager.getConnection(dbUrl, dbUsername, dbPassword); return
	 * connection; } catch (SQLException e) { System.err.print(e.getMessage() +
	 * " Noob - ingen sql bandit at hente her!"); } catch (Exception e) {
	 * System.err.print(e.getMessage() +
	 * " Noob - nu er du bare en meget grim og dum abe :-)"); } return null; }
	 * 
	 * @author Gruppe 25
	 */
	public static Connection getConnection() throws Exception {

		String url = "jdbc:mysql://localhost/MatadorProjekt";
		String username = "root";
		String password = "123";
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
