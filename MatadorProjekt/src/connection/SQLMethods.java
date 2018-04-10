package connection;

import java.sql.CallableStatement;
import java.sql.Connection;

import gameContent.Player;

public class SQLMethods {
	
	Connector connector;
	
	public SQLMethods() {
		connector = new Connector();
	}
	

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

}
