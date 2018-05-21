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
 * @author Rasmus
 */
public class DAL {
	
	/** The connector. */
	private Connection con;
	
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
			cst.setInt(Constants.PLAYER_ID.toString(), player.getID()); //id
			cst.setString(Constants.NAME.toString(), player.getName()); //name
			cst.setString(Constants.COLOR.toString(), color); //color
			cst.setInt(Constants.POS_INDEX.toString(), player.getCurrentPosition().getIndex());
			cst.setBoolean(Constants.IN_PRISON.toString(), player.isInPrison()); // is in prison
			cst.setInt(Constants.BALANCE.toString(), player.getBalance()); // balance 
			cst.setInt(Constants.PARDON.toString(), player.getOwnedCards().size()); // pardon needs implementation for Player objects
			cst.setInt(Constants.GAME_ID.toString(), 1); //gameID
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
			cst.setString(Constants.NAME.toString(), player.getName());
			cst.setBoolean(Constants.IN_PRISON.toString(), player.isInPrison());
			cst.setInt(Constants.BALANCE.toString(), player.getBalance());
			cst.setInt(Constants.POS_INDEX.toString(), player.getCurrentPosition().getIndex());
			cst.setInt(Constants.PARDON.toString(), player.getOwnedCards().size()); // pardon needs implementation for Player objects
			cst.setInt(Constants.GAME_ID.toString(), 1); //gameID is always 1 at the moment - only one game at a time
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
	
	/**
	 * Gets the number of owned properties.
	 *
	 * @return the number of owned properties
	 */
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
		try {													
			//Property ID, Property Name, Player ID, Player Name
			CallableStatement cst = con.prepareCall("{ call AddNewPropertyToPlayer(?,?,?,?,?) }");
			cst.setInt(Constants.PROPERTY_ID.toString(), prop.getIndex());
			cst.setString(Constants.PROPERTY_NAME.toString(), prop.getName());
			cst.setInt(Constants.PLAYER_ID.toString(), player.getID());
			if(prop instanceof RealEstate) {
				houses = ((RealEstate) prop).getHouses();
			}
			cst.setInt(Constants.HOUSES.toString(), houses);
			cst.setBoolean(Constants.MORTGAGE.toString(), prop.isMortgaged());
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
	 * 
	 */
	public void updateHouses(Property prop) {
		try {
			CallableStatement cst = con.prepareCall(" { call updateHouses(?, ?, ?) } ");
			cst.setInt(Constants.PROPERTY_ID.toString(), prop.getIndex());
			cst.setInt(Constants.HOUSES.toString(), ((RealEstate) prop).getHouses());
			cst.setBoolean(Constants.HOTEL.toString(), ((RealEstate) prop).hasHotel());
			cst.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gets the properties.
	 *
	 * @param player the player
	 * @return the properties
	 */
	public ResultSet getProperties(Player player) {

		try {
			CallableStatement cst = con.prepareCall(" { call getPropertiesForPlayer(?) } ");
			cst.setInt(Constants.PLAYER_ID.toString(), player.getID());
			return cst.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * Update property owner.
	 *
	 * @param player the player
	 * @param property the property
	 */
	public void updatePropertyOwner(Player player, Property property) {
		try {
			CallableStatement cst = con.prepareCall(" { call tradeProperty(?,?) } ");
			cst.setInt(Constants.POS_INDEX.toString(), property.getIndex());
			cst.setInt(Constants.PLAYER_ID.toString(), player.getID());
			cst.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Update property mortgage.
	 *
	 * @param property the property
	 */
	public void updatePropertyMortgage(Property property) {
		try {
			CallableStatement cst = con.prepareCall(" { call mortgageProperty(?,?) } ");
			cst.setInt(Constants.POS_INDEX.toString(), property.getIndex());
			cst.setBoolean(Constants.MORTGAGE.toString(), property.isMortgaged());
			cst.execute();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}

}