package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import connection.Constants;
import connection.DAL;
import connection.DAO;
import gameContent.Player;
import spaces.Property;

public class viewDB {

	private Constants constant;
	private DAO dao;
	private JFrame view;
	private JTable tablePlayers;
	private JTable tableProperties;
	private JScrollPane playerPanel;
	private JScrollPane propertyPanel;
	private DefaultTableModel modelPlayers;
	private DefaultTableModel modelProperties;
	private Object[] columnsPlayers;
	private Object[] columnsProperties;
	private final int numberOfProperties = 0;

	/**
	 * Instantiates a new view DB.
	 */
	public viewDB(DAO dao) {
		view = new JFrame();
		constant = new Constants();
		this.dao = dao;
		columnsPlayers = new Object[] { constant.GAME_ID, constant.NAME, constant.POSITION, constant.BALANCE,
				constant.IN_PRISON, constant.PARDON, constant.COLOR };
		columnsProperties = new Object[] { constant.PROPERTY_NAME, constant.NAME, constant.HOUSES };
	}

	/**
	 * Creates the view of DB.
	 *
	 * @param data
	 *            the data
	 */
	public void createViewOfDB(Object[][] data) {
		System.out.println("SUT PIK OG DØ");
		// Players
		modelPlayers = new DefaultTableModel(data, columnsPlayers);
		tablePlayers = new JTable(modelPlayers);
		tablePlayers.setLayout(new FlowLayout());
		tablePlayers.setGridColor(Color.blue);
		tablePlayers.setPreferredScrollableViewportSize(new Dimension(600, 200));
		tablePlayers.setFillsViewportHeight(true);
		playerPanel = new JScrollPane(tablePlayers);
		view.add(playerPanel, "North");

		// Properties
		modelProperties = new DefaultTableModel(numberOfProperties, columnsProperties.length);
		modelProperties.setColumnIdentifiers(columnsProperties);
		tableProperties = new JTable(modelProperties);
		tableProperties.setLayout(new FlowLayout());
		tableProperties.setGridColor(Color.blue);
		tableProperties.setPreferredScrollableViewportSize(new Dimension(600, 400));
		tableProperties.setFillsViewportHeight(true);
		propertyPanel = new JScrollPane(tableProperties);
		view.add(propertyPanel, "South");

		// Window
		view.setSize(600, 700);
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
			Object[] p = dao.updatePlayerView(player);
			modelPlayers.setValueAt(p[0], player.getID() - 1, 2); // Position
			modelPlayers.setValueAt(p[1], player.getID() - 1, 3); // Balance
			modelPlayers.setValueAt(p[2], player.getID() - 1, 4); // Prison
			modelPlayers.setValueAt(p[3], player.getID() - 1, 5); // TODO: pardon needs implementation
			modelPlayers.setValueAt(p[4], player.getID() - 1, 6); // Color
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

		Object[][] data = dao.updatePropertyView(modelProperties.getRowCount());
		
		for(int i = 0; i < data.length; i++) {
			if (i >= modelProperties.getRowCount()) {
				Object[] row = new Object[columnsProperties.length];
				row[0] = data[i][0];
				row[1] = data[i][1];
				row[2] = data[i][2];
				modelProperties.addRow(row);
			} else {
				modelProperties.setValueAt(data[i][0], i, 0);
				modelProperties.setValueAt(data[i][0], i, 1);
				modelProperties.setValueAt(data[i][0], i, 2);
			}
		}
	}
}
