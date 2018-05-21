package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import connection.Constants;
import connection.DAO;
import gameContent.Game;
import gameContent.Player;

/**
 * The Class viewDB.
 * @author Rasmus, Nicolas
 */
public class viewDB {
	
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
	 * @param dao
	 * 	
	 */
	public viewDB(DAO dao) {
		view = new JFrame();
		this.dao = dao;
		columnsPlayers = new Object[] { Constants.GAME_ID.toString(), Constants.NAME.toString(), Constants.POSITION.toString(), Constants.BALANCE.toString(),
				Constants.IN_PRISON.toString(), Constants.PARDON.toString(), Constants.COLOR.toString() };
		columnsProperties = new Object[] { Constants.PROPERTY_NAME.toString(), Constants.NAME.toString(), Constants.HOUSES.toString(), Constants.HOTEL.toString(), Constants.MORTGAGE.toString() };
	}
	
	/**
	 * Creates the view of DB.
	 *
	 * @param data
	 *            the data
	 */
	public void createViewOfDB(Object[][] data) {
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
		tableProperties.setGridColor(Color.black);
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
	 * @param game
	 * 			the game           
	 */
	public void updatePlayerView(List<Player> players, Game game) {
		// Updates values in DB and shows it in a view.

		for (Player player : players) {
			Object[] p = dao.updatePlayerView(player, game);
			modelPlayers.setValueAt(p[0], player.getID() - 1, 2); // Position
			modelPlayers.setValueAt(p[1], player.getID() - 1, 3); // Balance
			modelPlayers.setValueAt(p[2], player.getID() - 1, 4); // Prison
			modelPlayers.setValueAt(p[3], player.getID() - 1, 5); // TODO: pardon needs implementation
			modelPlayers.setValueAt(p[4], player.getID() - 1, 6); // Color
		}
	}

	/**
	 * update the property view
	 *
	 * 
	 *           
	 * 
	 *            
	 */
	public void updPropertyView() {

		Object[][] data = dao.updatePropertyView();
		
		for(int i = 0; i < data.length; i++) {
			if (i >= modelProperties.getRowCount()) {
				Object[] row = new Object[columnsProperties.length];
				row[0] = data[i][0];
				row[1] = data[i][1];
				row[2] = data[i][2];
				row[3] = data[i][3];
				row[4] = data[i][4];
				modelProperties.addRow(row);
			} else {
				modelProperties.setValueAt(data[i][0], i, 0);
				modelProperties.setValueAt(data[i][1], i, 1);
				modelProperties.setValueAt(data[i][2], i, 2);
				modelProperties.setValueAt(data[i][3], i, 3);
				modelProperties.setValueAt(data[i][4], i, 4);
			}
		}
	}
}
