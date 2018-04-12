package connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import gameContent.Player;

public class viewDB {
	
	SQLMethods sql;
	JFrame view;
	JTable table;
	DefaultTableModel model;
	Object[] columns;
	
	public viewDB() {
		view = new JFrame();
		columns = new Object[] {"GameID", "Name", "Position", "Balance", "Prisoner", "Pardon", "Color"};
		sql = new SQLMethods();
	}
	
	public void createViewOfDB(Object[][] data) {
		//Updates values in a row matching the player ID.
		model = new DefaultTableModel(data, columns);
		table = new JTable(model);
		view.add(table);
		view.add(table.getTableHeader(), BorderLayout.PAGE_START);
		view.setSize(450, 200);
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.getContentPane().setBackground(Color.WHITE);
	}
	
	public void updateViewOfDB(Player player) {
		//Updates values in DB and shows it in a view.
		ResultSet rs = sql.updateViewEndTurn(player);
		try {
			while(rs.next()) {
				model.setValueAt(rs.getInt(1), player.getID()-1, 0);
				model.setValueAt(rs.getString(2), player.getID()-1, 1);
				model.setValueAt(rs.getString(3), player.getID()-1, 2);
				model.setValueAt(rs.getInt(4), player.getID()-1, 3);
				model.setValueAt(rs.getBoolean(5), player.getID()-1, 4);
				model.setValueAt(rs.getBoolean(6), player.getID()-1, 5); //pardon needs implementation
				model.setValueAt(rs.getString(7), player.getID()-1, 6);
				System.out.println(rs.getInt(1) + model.getValueAt(player.getID()-1, 0).toString());
				System.out.println(rs.getString(2) + model.getValueAt(player.getID()-1, 1).toString());
				System.out.println(rs.getString(3) + model.getValueAt(player.getID()-1, 2).toString());
				System.out.println(rs.getInt(4) + model.getValueAt(player.getID()-1, 3).toString());
				System.out.println(rs.getBoolean(5) + model.getValueAt(player.getID()-1, 4).toString());
				System.out.println(rs.getBoolean(6) + model.getValueAt(player.getID()-1, 5).toString());
				System.out.println(rs.getString(7) + model.getValueAt(player.getID()-1, 6).toString() + "\n");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
