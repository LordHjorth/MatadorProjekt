package connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gameContent.Player;

public class viewDB {

	private SQLMethods sql;
	private JFrame view;
	private JTable table;
	private DefaultTableModel model;
	private Object[] columns;

	public viewDB() {
		view = new JFrame();
		columns = new Object[] { "GameID", "Name", "Position", "Balance", "Prisoner", "Pardon", "Color" };
		sql = new SQLMethods();
	}

	public void createViewOfDB(Object[][] data) {
		// Updates values in a row matching the player ID.
		model = new DefaultTableModel(data, columns);
		table = new JTable(model);
		view.add(table);
		view.add(table.getTableHeader(), BorderLayout.PAGE_START);
		view.setSize(450, 200);
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.getContentPane().setBackground(Color.WHITE);
	}

	public void updateViewOfDB(List<Player> players) {
		// Updates values in DB and shows it in a view.

		for (Player player : players) {
			ResultSet rs = sql.updateViewEndTurn(player);
			try {
				rs.absolute(player.getID());
				// model.setValueAt(rs.getInt(1), player.getID()-1, 0); //GameID
				// model.setValueAt(rs.getString(2), player.getID()-1, 1); //Name
				model.setValueAt(rs.getString(3), player.getID() - 1, 2); // Position
				model.setValueAt(rs.getInt(4), player.getID() - 1, 3); // Balance
				model.setValueAt(rs.getBoolean(5), player.getID() - 1, 4); // Prison
				model.setValueAt(rs.getBoolean(6), player.getID() - 1, 5); // pardon needs implementation
				model.setValueAt(rs.getString(7), player.getID() - 1, 6); // Color
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
}
