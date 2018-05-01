package connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gameContent.Player;
import spaces.Property;

public class viewDB {

	private SQLMethods sql;
	private JFrame view;
	private JTable tablePlayers;
	private JTable tableProperties;
	private DefaultTableModel modelPlayers;
	private DefaultTableModel modelProperties;
	private Object[] columnsPlayers;
	private Object[] columnsProperties;
	private final int numberOfProperties = 1;

	/**
	 * Instantiates a new view DB.
	 */
	public viewDB() {
		view = new JFrame();
		sql = new SQLMethods();
		columnsPlayers = new Object[] { sql.GAME_ID, sql.NAME, sql.POSITION, sql.BALANCE, sql.IN_PRISON, sql.PARDON,
				sql.COLOR };
		columnsProperties = new Object[] { sql.PROPERTY_NAME, sql.NAME, sql.HOUSES };
	}

	/**
	 * Creates the view of DB.
	 *
	 * @param data
	 *            the data
	 */
	public void createViewOfDB(Object[][] data) {

		view.setLayout(new BorderLayout());
		// Players
		modelPlayers = new DefaultTableModel(data, columnsPlayers);
		tablePlayers = new JTable(modelPlayers);
		tablePlayers.setGridColor(Color.blue);
		view.add(tablePlayers, "North");
		// view.add(tablePlayers.getTableHeader(), BorderLayout.PAGE_START);

		// Properties
		modelProperties = new DefaultTableModel(numberOfProperties, columnsProperties.length);
		modelProperties.setColumnIdentifiers(columnsProperties);
		tableProperties = new JTable(modelProperties);
		tableProperties.setGridColor(Color.red);
		view.add(tableProperties, "South");
		// view.add(tableProperties.getTableHeader(), BorderLayout.PAGE_START);

		// Window
		view.setSize(500, 1000);
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.getContentPane().setBackground(Color.WHITE);
	}

	/**
	 * Update view of DB.
	 *
	 * @param players
	 *            the players
	 */
	public void updatePlayerView(List<Player> players) {
		// Updates values in DB and shows it in a view.

		for (Player player : players) {
			ResultSet rs = sql.updateViewEndTurn(player);
			try {
				rs.absolute(player.getID());
				modelPlayers.setValueAt(rs.getString(sql.POSITION), player.getID() - 1, 2); // Position
				modelPlayers.setValueAt(rs.getInt(sql.BALANCE), player.getID() - 1, 3); // Balance
				modelPlayers.setValueAt(rs.getBoolean(sql.IN_PRISON), player.getID() - 1, 4); // Prison
				modelPlayers.setValueAt(rs.getBoolean(sql.PARDON), player.getID() - 1, 5); // TODO: pardon needs
																							// implementation
				modelPlayers.setValueAt(rs.getString(sql.COLOR), player.getID() - 1, 6); // Color
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Inserts properties to view.
	 *
	 * @param prop
	 *            the prop
	 * @param player
	 *            the player
	 */
	public void updPropertyView(Property prop, Player player) {

		ResultSet rs = sql.getPropertyByPlayer();

		try {
			int i = 0;
				while(rs.next()) {
				
				if(i>=modelProperties.getRowCount()) {
					Object[] row = new Object[columnsProperties.length];
					row[0] = rs.getString(sql.PROPERTY_NAME);
					row[1] = rs.getString(sql.NAME);
					row[2] = rs.getInt(sql.HOUSES);
					modelProperties.addRow(row);
				}	
				else {
					modelProperties.setValueAt(rs.getString(sql.PROPERTY_NAME), i, 0);
					modelProperties.setValueAt(rs.getString(sql.NAME), i, 1);
					modelProperties.setValueAt(rs.getInt(sql.HOUSES), i, 2);
				}
				i++;
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
