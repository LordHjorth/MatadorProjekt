package connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import connection.Connector;

import gameContent.Player;

/**
 * The Class SQLMethods.
 *
 * @author Gruppe 25
 */
public class SQLMethods {
	
	/** The connector. */
	Connection con;
	
	/**
	 * Instantiates a new SQL methods.
	 */
	public SQLMethods() {
		try {
			con = Connector.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Creates the players in DB.
	 *
	 * @param player the player
	 * @param ID the id
	 * @param color the color
	 * @throws Exception the exception
	 */
	public void createPlayersInDB(Player player, int ID, String color) throws Exception {
		try {
			int playerID = ID + 1;
			CallableStatement cst = con.prepareCall("{ call CreatePlayerID(?, ?, ?, ?, ?, ?, ?, ?) }");
			cst.setInt("id", playerID); //id
			cst.setString("name", player.getName()); //name
			cst.setString("color", color); //color
			cst.setString("position", player.getCurrentPosition().toString()); //position
			cst.setBoolean("prison", player.isInPrison()); // is in prison
			cst.setInt("balance", player.getBalance()); // balance 
			cst.setBoolean("pardon", false); // pardon needs implementation for Player objects
			cst.setInt("gameID", 1); //gameID
			cst.execute();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	/**
	 * Update view when end turn.
	 *
	 * @param player the player
	 * @return the result set
	 */
	public ResultSet updateViewEndTurn(Player player) {
		try {
			CallableStatement cst = con.prepareCall("{ call UpdatePlayer(?, ?, ?, ?, ?, ?) }");
			cst.setString("name", player.getName());
			cst.setBoolean("prison", player.isInPrison());
			cst.setInt("balance", player.getBalance());
			cst.setString("position", player.getCurrentPosition().getName());
			cst.setBoolean("pardon", false); // pardon needs implementation for Player objects
			cst.setInt("gameID", 1); //gameID is always 1 at the moment - only one game at a time
			return cst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//TODO: 
	//New game (see newGame_SQL.txt)
	//Update players after each turn
	//Move player
	//Transaction
	//

}
