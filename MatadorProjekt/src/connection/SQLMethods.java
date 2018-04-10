package connection;

import java.sql.CallableStatement;
import java.sql.Connection;

import gameContent.Player;

/**
 * @author Gruppe 25
 */
public class SQLMethods {
	
	/** The connector. */
	Connector connector;
	
	/**
	 * Instantiates a new SQL methods.
	 */
	public SQLMethods() {
		connector = new Connector();
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
			Connection con = connector.getConnection();
			// id, name1, color, prison, balance1, pardon, gameID
			int playerID = ID + 1;
			CallableStatement cst = con.prepareCall("{ call CreatePlayerID(?, ?, ?, ?, ?, ?, ?) }");
			cst.setInt("id", playerID);
			cst.setString("name", player.getName());
			cst.setString("color", color);
			cst.setBoolean("prison", player.isInPrison());
			cst.setInt("balance", player.getBalance());
			cst.setBoolean("pardon", false); // pardon needs implementation for Player objects
			cst.setInt("gameID", 1);
			cst.execute();
		} catch (Exception e) {
			System.out.println(e);
		} 
	}
	
	//TODO: 
	//New game (see newGame_SQL.txt)
	//Move player
	//Transaction
	//

}
