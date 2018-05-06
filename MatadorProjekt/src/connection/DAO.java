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

public class DAO implements IdataAccesObject {

	private DAL dal;
	private viewDB vdb;
	private Constants constant;

	public DAO() {

		dal = new DAL();
		vdb = new viewDB(this);
		constant = new Constants();

	}

	public viewDB getVDB() {
		return vdb;
	}

	public void resetDB() {
		dal.resetDB();
	}

	public void createPlayerDB(Player p, int ID, String color) {

		try {
			dal.createPlayersInDB(p, ID, color); // adds player to database
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object[] updatePlayerView(Player player, Game game) {
		ResultSet rs = dal.updateViewEndTurn(player);
		List<Space> spaces = game.getSpaces();
		Object[] data = new Object[5];
		try {
			rs.absolute(player.getID());
			data[0] = spaces.get(rs.getInt(constant.POS_INDEX)).getName();
			data[1] = rs.getInt(constant.BALANCE);
			data[2] = rs.getBoolean(constant.IN_PRISON);
			data[3] = rs.getInt(constant.PARDON);
			System.out.println(data[3].toString());
			data[4] = rs.getString(constant.COLOR);
		} catch (SQLException e) {

		}
		return data;
	}

	public Object[][] updatePropertyView() {
		int numberOfProperties = dal.getNumberOfOwnedProperties();
		Object[][] data = new Object[numberOfProperties][5];
		ResultSet rs = dal.getPropertyByPlayer();
		try {
			int i = 0;
			while (rs.next()) {
				// data[i] = new Object[3];
				data[i][0] = rs.getString(constant.PROPERTY_NAME);
				data[i][1] = rs.getString(constant.NAME);
				data[i][2] = rs.getInt(constant.HOUSES);
				data[i][3] = rs.getInt(constant.HOTEL);
				data[i][4] = rs.getBoolean(constant.MORTGAGE);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return data;
	}

	public int getNumberOfPlayers() {
		return dal.getNumberOfPlayers();
	}

	public Player loadPlayer(Game game, int ID) {
		List<Space> properties = game.getSpaces();
		List<Card> ownedCards = new ArrayList<Card>();
		List<Card> cards = game.getCardDeck();
		ResultSet rs = dal.LoadPlayers();
		Player player = new Player();
		try {
			rs.absolute(ID + 1);
			player.setID(rs.getInt(constant.PLAYER_ID));
			player.setName(rs.getString(constant.NAME));
			player.setBalance(rs.getInt(constant.BALANCE));
			player.setCurrentPosition(properties.get(rs.getInt(constant.POS_INDEX)));
			player.setInPrison(rs.getBoolean(constant.IN_PRISON));
			String color = rs.getString(constant.CAR_COLOR);
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
					if (i < rs.getInt(constant.PARDON)) {
						ownedCards.add(card);
					}
					i++;
				}
			}
			for(Card card : ownedCards) {
				game.removeSpecificCard(card);
			}
			
			player.setOwnedCards(ownedCards);
			
			/*
			 * List<Card> cards = game.getPardonCards(); for (int k = 0; k <
			 * rs.getInt(sql.PARDON); k++) { if (!cards.isEmpty()) {
			 * p.setOwnedCard(cards.get(k)); } }
			 */

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return player;
	}

	public void loadProperties(Game game) {

		List<Player> players = game.getPlayers();
		List<Space> properties = game.getSpaces();
		try {
			for (Player player : players) {
				ResultSet props = dal.getProperties(player);
				while (props.next()) {
					Property p = ((Property) properties.get(props.getInt(constant.PLAYER_ID)));
					player.addOwnedProperty(p);
					p.setOwner(player);
					p.setMortgaged(props.getBoolean(constant.MORTGAGE));
					if (p instanceof RealEstate) {
						((RealEstate) p).addHouses(props.getInt(constant.HOUSES));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updatePropertyOwner(Player player, Property property) {
		dal.updatePropertyOwner(player, property);
		vdb.updPropertyView();
	}

	public void updatePropertyMortgage(Property property) {
		dal.updatePropertyMortgage(property);
		vdb.updPropertyView();
	}

	// SEPERATED HERE!

	public void addPropertyToPlayer(Property property, Player player) {
		dal.addPropertyToPlayer(property, player);
		vdb.updPropertyView();
	}

	public void updateHouses(Property p) {
		dal.updateHouses(p);
		vdb.updPropertyView();
	}

}
