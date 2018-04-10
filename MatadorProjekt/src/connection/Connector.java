package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Connector {
	
public static void main(String[] args) throws Exception {
	
		createTable();
		
	}

	public static Connection getConnection() throws Exception {
		try {
			String driver = "com.mysql.jdbc.Driver";
			String url = "jdbc:mysql://10.16.175.65:3306/" + "matadorspil" + "?useSSL=false";
			
			String username = "gruppe25";
			String password = "25";
			
			Class.forName(driver);
			
			
			Connection conn = DriverManager.getConnection(url, username, password);
			
			System.out.println("Succesfuld tilslutning");
			return conn;
		} catch (Exception e) {
			System.out.println(e);
		}

		System.out.println("Tilslutningen er fejlet");
		return null;
	}

	public static void createTable() throws Exception {

		try {
//			getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			Connection con = getConnection();
			PreparedStatement create = con.prepareStatement(
					"Create Table IF NOT EXISTS Testtabel (Landenavn varchar(255), Forkortelse varchar(255), Kontinent Varchar(255))");
			create.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			System.out.println("Tabel lavet");
		}

	}

}
