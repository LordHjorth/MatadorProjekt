package connection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cards.PardonCard;

import java.sql.*;

import gameContent.Card;
import gameContent.Game;
import gameContent.Player;
import gameContent.Space;
import properties.RealEstate;
import spaces.Property;
import view.viewDB;

/**
 * The Class DAO.
 * 
 * @author Rasmus, Simone, Monica
 */
public class DAO {

	private DAL dal;
	private viewDB vdb;

	/**
	 * Instantiates a new dao.
	 */
	public DAO() {

		dal = new DAL();
		vdb = new viewDB(this);

	}

	/**
	 * Gets the vdb.
	 *
	 * @return the vdb
	 */
	public viewDB getVDB() {
		return vdb;
	}

	/**
	 * Reset DB.
	 */
	public void resetDB() {
		dal.resetDB();
	}

	/**
	 * Creates the player DB.
	 *
	 * @param p the p
	 * @param ID the id
	 * @param color the color
	 */
	public void createPlayerDB(Player p, int ID, String color) {

		try {
			dal.createPlayersInDB(p, ID, color); // adds player to database
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update player view.
	 *
	 * @param player the player
	 * @param game the game
	 * @return the object[]
	 */
	public Object[] updatePlayerView(Player player, Game game) {
		ResultSet rs = dal.updateViewEndTurn(player);
		List<Space> spaces = game.getSpaces();
		Object[] data = new Object[5];
		try {
			rs.absolute(player.getID());
			data[0] = spaces.get(rs.getInt(Constants.POS_INDEX.toString())).getName();
			data[1] = rs.getInt(Constants.BALANCE.toString());
			data[2] = rs.getBoolean(Constants.IN_PRISON.toString());
			data[3] = rs.getInt(Constants.PARDON.toString());
			System.out.println(data[3].toString());
			data[4] = rs.getString(Constants.COLOR.toString());
		} catch (SQLException e) {

		}
		return data;
	}

	/**
	 * Update property view.
	 *
	 * @return the object[][]
	 */
	public Object[][] updatePropertyView() {
		int numberOfProperties = dal.getNumberOfOwnedProperties();
		Object[][] data = new Object[numberOfProperties][5];
		ResultSet rs = dal.getPropertyByPlayer();
		try {
			int i = 0;
			while (rs.next()) {
				// data[i] = new Object[3];
				data[i][0] = rs.getString(Constants.PROPERTY_NAME.toString());
				data[i][1] = rs.getString(Constants.NAME.toString());
				data[i][2] = rs.getInt(Constants.HOUSES.toString());
				data[i][3] = rs.getInt(Constants.HOTEL.toString());
				data[i][4] = rs.getBoolean(Constants.MORTGAGE.toString());
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	/**
	 * Gets the number of players.
	 *
	 * @return the number of players
	 */
	public int getNumberOfPlayers() {
		return dal.getNumberOfPlayers();
	}

	/**
	 * Load player.
	 *
	 * @param game the game
	 * @param ID the id
	 * @return the player
	 */
	public Player loadPlayer(Game game, int ID) {
		List<Space> properties = game.getSpaces();
		List<Card> ownedCards = new ArrayList<Card>();
		List<Card> cards = game.getCardDeck();
		ResultSet rs = dal.LoadPlayers();
		Player player = new Player();
		try {
			rs.absolute(ID + 1);
			player.setID(rs.getInt(Constants.PLAYER_ID.toString()));
			player.setName(rs.getString(Constants.NAME.toString()));
			player.setBalance(rs.getInt(Constants.BALANCE.toString()));
			player.setCurrentPosition(properties.get(rs.getInt(Constants.POS_INDEX.toString())));
			player.setInPrison(rs.getBoolean(Constants.IN_PRISON.toString()));
			String color = rs.getString(Constants.CAR_COLOR.toString());
			switch (color) {
			case "Blue":
				player.setColor(Color.blue);
				break;
			case "Red":
				player.setColor(Color.red);
				break;
			case "Black":
				player.setColor(Color.black);
				break;
			case "Green":
				player.setColor(Color.green);
				break;
			case "Yellow":
				player.setColor(Color.yellow);
				break;
			case "Pink":
				player.setColor(Color.pink);
				break;
			}

			int i = 0;
			for (Card card : cards) {
				if (card instanceof PardonCard) {
					if (i < rs.getInt(Constants.PARDON.toString())) {
						ownedCards.add(card);
					}
					i++;
				}
			}
			for(Card card : ownedCards) {
				game.removeSpecificCard(card);
			}
			player.setOwnedCards(ownedCards);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}

	/**
	 * Load properties.
	 *
	 * @param game the game
	 */
	public void loadProperties(Game game) {

		List<Player> players = game.getPlayers();
		List<Space> properties = game.getSpaces();
		try {
			for (Player player : players) {
				ResultSet props = dal.getProperties(player);
				while (props.next()) {
					Property p = ((Property) properties.get(props.getInt(Constants.PLAYER_ID.toString())));
					player.addOwnedProperty(p);
					p.setOwner(player);
					p.setMortgaged(props.getBoolean(Constants.MORTGAGE.toString()));
					if (p instanceof RealEstate) {
						((RealEstate) p).addHouses(props.getInt(Constants.HOUSES.toString()));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Update property owner.
	 *
	 * @param player the player
	 * @param property the property
	 */
	public void updatePropertyOwner(Player player, Property property) {
		dal.updatePropertyOwner(player, property);
		vdb.updPropertyView();
	}

	/**
	 * Update property mortgage.
	 *
	 * @param property the property
	 */
	public void updatePropertyMortgage(Property property) {
		dal.updatePropertyMortgage(property);
		vdb.updPropertyView();
	}

	/**
	 * Adds the property to player.
	 *
	 * @param property the property
	 * @param player the player
	 */
	public void addPropertyToPlayer(Property property, Player player) {
		dal.addPropertyToPlayer(property, player);
		vdb.updPropertyView();
	}

	/**
	 * Update houses.
	 *
	 * @param p the p
	 */
	public void updateHouses(Property p) {
		dal.updateHouses(p);
		vdb.updPropertyView();
	}

}
