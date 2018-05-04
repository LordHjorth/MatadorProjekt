package connection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;

import gameContent.Game;
import gameContent.Player;
import gameContent.Space;
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

	public void createPlayerDB(Player p, int ID, String color) {

		try {
			dal.createPlayersInDB(p, ID, color); // adds player to database
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object[] updatePlayerView(Player player) {
		ResultSet rs = dal.updateViewEndTurn(player);
		Object[] data = new Object[5];
		try {
			rs.absolute(player.getID());
			data[0] = rs.getString(constant.POSITION);
			data[1] = rs.getInt(constant.BALANCE);
			data[2] = rs.getBoolean(constant.IN_PRISON);
			data[3] = rs.getBoolean(constant.PARDON);
			data[4] = rs.getString(constant.COLOR);
		} catch (SQLException e) {

		}
		return data;
	}

	public Object[][] updatePropertyView(int rowCount) {
		Object[][] data = new Object[rowCount][];
		ResultSet rs = dal.getPropertyByPlayer();
		try {
			int i = 0;
			while (rs.next()) {
				data[i] = new Object[3];
				data[i][0] = rs.getString(constant.PROPERTY_ID);
				data[i][1] = rs.getString(constant.NAME);
				data[i][2] = rs.getInt(constant.HOUSES);
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

	public Player loadPlayer(Game game) {
		List<Space> properties = game.getSpaces();
		ResultSet rs = dal.LoadPlayers();
		Player player = new Player();
		try {
			while (rs.absolute(player.getID())) {
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

				/*
				 * List<Card> cards = game.getPardonCards(); for (int k = 0; k <
				 * rs.getInt(sql.PARDON); k++) { if (!cards.isEmpty()) {
				 * p.setOwnedCard(cards.get(k)); } }
				 */
			}
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
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	//SEPERATED HERE!
	
	public void addPropertyToPlayer(Property property, Player player) {
		dal.addPropertyToPlayer(property, player);
	}
	
	public void updateHouses(Property p, int i) {
		dal.updateHouses(p, i);
	}

	
	
	
	
	
	
	
}
