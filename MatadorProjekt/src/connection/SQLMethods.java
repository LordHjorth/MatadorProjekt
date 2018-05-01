package connection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import connection.Connector;

import gameContent.Player;
import properties.RealEstate;
import spaces.Property;

/**
 * The Class SQLMethods.
 *
 * @author Gruppe 25
 */
public class SQLMethods {
	
	/** The connector. */
	private Connection con;
	public final String PLAYER_ID = "id";
	public final String NAME = "name";
	public final String BALANCE = "balance";
	public final String POSITION = "position";
	public final String POS_INDEX = "PosIndex";
	public final String IN_PRISON = "inPrison";
	public final String COLOR = "color";
	public final String PARDON = "pardon";
	public final String GAME_ID = "gameID";
	public final String PROPERTY_ID = "PropID";
	public final String PROPERTY_NAME = "PropertyName";
	public final String HOUSES="houses";
	
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
			CallableStatement cst = con.prepareCall("{ call CreatePlayerID(?, ?, ?, ?, ?, ?, ?, ?, ?) }");
			cst.setInt(PLAYER_ID, playerID); //id
			cst.setString(NAME, player.getName()); //name
			cst.setString(COLOR, color); //color
			cst.setString(POSITION, player.getCurrentPosition().getName()); //position
			cst.setInt(POS_INDEX, player.getCurrentPosition().getIndex());
			cst.setBoolean(IN_PRISON, player.isInPrison()); // is in prison
			cst.setInt(BALANCE, player.getBalance()); // balance 
			cst.setBoolean(PARDON, false); // pardon needs implementation for Player objects
			cst.setInt(GAME_ID, 1); //gameID
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
			CallableStatement cst = con.prepareCall("{ call UpdatePlayer(?, ?, ?, ?, ?, ?, ?) }");
			cst.setString(NAME, player.getName());
			cst.setBoolean(IN_PRISON, player.isInPrison());
			cst.setInt(BALANCE, player.getBalance());
			cst.setString(POSITION, player.getCurrentPosition().getName());
			cst.setInt(POS_INDEX, player.getCurrentPosition().getIndex());
			cst.setBoolean(PARDON, false); // pardon needs implementation for Player objects
			cst.setInt(GAME_ID, 1); //gameID is always 1 at the moment - only one game at a time
			cst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return showView();
	}
	
	/**
	 * Show view.
	 *
	 * @return the result set
	 */
	public ResultSet showView() {
		try {
			Statement st = con.createStatement();
			return st.executeQuery("SELECT * FROM getViewOfGameStatus");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Reset DB.
	 */
	public void resetDB() {
		try {
			CallableStatement cst = con.prepareCall("{ call resetDB() }");
			cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the number of players.
	 *
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select * from getnumberofplayers");
			rs.next();
			return rs.getInt("count(*)");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	/**
	 * Load players.
	 *
	 * @return the result set
	 */
	public ResultSet LoadPlayers() {
		try {
			Statement st = con.createStatement();
			return st.executeQuery("select * from LoadPlayers");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Adds the property to player.
	 *
	 * @param prop the prop
	 * @param player the player
	 */
	public void addPropertyToPlayer(Property prop, Player player) {
		int houses = 0;
		try {																	//Property ID, Property Name, Player ID, Player Name
			CallableStatement cst = con.prepareCall("{ call AddNewPropertyToPlayer(?,?,?,?, ?) }");
			cst.setInt(PROPERTY_ID, prop.getIndex());
			cst.setString(PROPERTY_NAME, prop.getName());
			cst.setInt(PLAYER_ID, player.getID());
			cst.setString(NAME, player.getName());
			if(prop instanceof RealEstate) {
				houses = ((RealEstate) prop).getHouses();
			}
			cst.setInt(HOUSES, houses);
			cst.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the property by player.
	 *
	 * @return the property by player
	 */
	public ResultSet getPropertyByPlayer() {
		try {
			Statement st = con.createStatement();
			return st.executeQuery("select * from PropertyByPlayers");
		} catch(SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Update houses.
	 *
	 * @param prop the prop
	 * @param houses the houses
	 */
	public void updateHouses(Property prop, int houses) {
		try {
			CallableStatement cst = con.prepareCall(" { call updateHouses(?,?) } ");
			System.out.println(prop.getName() + " - " + prop.getIndex() + " - " + houses);
			cst.setInt(PROPERTY_ID, prop.getIndex());
			cst.setInt(HOUSES, houses);
			cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//TODO: 
	//New game (see newGame_SQL.txt)
	//Update players after each turn
	//Move player
	//Transaction
	//

}