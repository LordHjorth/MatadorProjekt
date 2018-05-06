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
import connection.Constants;

/**
 * The Class SQLMethods.
 *
 * @author Gruppe 25
 */
public class DAL {
	
	/** The connector. */
	private Connection con;
	private Constants constant = new Constants();
	
	/**
	 * Instantiates a new SQL methods.
	 */
	public DAL() {
		try {
			con = Connector.getConnection();
		} catch (Exception e) {
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
			CallableStatement cst = con.prepareCall("{ call CreatePlayerID(?, ?, ?, ?, ?, ?, ?, ?) }");
			cst.setInt(constant.PLAYER_ID, player.getID()); //id
			cst.setString(constant.NAME, player.getName()); //name
			cst.setString(constant.COLOR, color); //color
			cst.setInt(constant.POS_INDEX, player.getCurrentPosition().getIndex());
			cst.setBoolean(constant.IN_PRISON, player.isInPrison()); // is in prison
			cst.setInt(constant.BALANCE, player.getBalance()); // balance 
			cst.setInt(constant.PARDON, player.getOwnedCards().size()); // pardon needs implementation for Player objects
			cst.setInt(constant.GAME_ID, 1); //gameID
			cst.execute();
		} catch (Exception e) {
			e.printStackTrace();
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
			cst.setString(constant.NAME, player.getName());
			cst.setBoolean(constant.IN_PRISON, player.isInPrison());
			cst.setInt(constant.BALANCE, player.getBalance());
			cst.setInt(constant.POS_INDEX, player.getCurrentPosition().getIndex());
			cst.setInt(constant.PARDON, player.getOwnedCards().size()); // pardon needs implementation for Player objects
			cst.setInt(constant.GAME_ID, 1); //gameID is always 1 at the moment - only one game at a time
			cst.execute();
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
	
	public int getNumberOfOwnedProperties() {
		try {
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("select count(*) from Property");
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
			CallableStatement cst = con.prepareCall("{ call AddNewPropertyToPlayer(?,?,?,?,?) }");
			cst.setInt(constant.PROPERTY_ID, prop.getIndex());
			cst.setString(constant.PROPERTY_NAME, prop.getName());
			cst.setInt(constant.PLAYER_ID, player.getID());
			if(prop instanceof RealEstate) {
				houses = ((RealEstate) prop).getHouses();
			}
			cst.setInt(constant.HOUSES, houses);
			cst.setBoolean(constant.MORTGAGE, prop.isMortgaged());
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
	public void updateHouses(Property prop) {
		try {
			CallableStatement cst = con.prepareCall(" { call updateHouses(?, ?, ?) } ");
			
			cst.setInt(constant.PROPERTY_ID, prop.getIndex());
			cst.setInt(constant.HOUSES, ((RealEstate) prop).getHouses());
			cst.setBoolean(constant.HOTEL, ((RealEstate) prop).hasHotel());
			cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ResultSet getProperties(Player player) {

		try {
			CallableStatement cst = con.prepareCall(" { call getPropertiesForPlayer(?) } ");
			cst.setInt(constant.PLAYER_ID, player.getID());
			return cst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	public void updatePropertyOwner(Player player, Property property) {
		try {
			CallableStatement cst = con.prepareCall(" { call tradeProperty(?,?) } ");
			cst.setInt(constant.POS_INDEX, property.getIndex());
			cst.setInt(constant.PLAYER_ID, player.getID());
			cst.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updatePropertyMortgage(Property property) {
		try {
			CallableStatement cst = con.prepareCall(" { call mortgageProperty(?,?) } ");
			cst.setInt(constant.POS_INDEX, property.getIndex());
			cst.setBoolean(constant.MORTGAGE, property.isMortgaged());
			cst.execute();
		} catch(SQLException e) {
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