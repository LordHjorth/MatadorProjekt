package connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;

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
		columns = new Object[] {"GameID", "Name", "Position", "Balance", "Prisoner", "Pardon available", "Car Color"};
		sql = new SQLMethods();
	}
	
	public void createViewOfDB(Object[][] data) {
		model = new DefaultTableModel(data, columns);
		table = new JTable(model);
		view.add(table);
		view.add(table.getTableHeader(), BorderLayout.PAGE_START);
		view.setSize(400, 300);
		view.setVisible(true);
		view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		view.getContentPane().setBackground(Color.WHITE);
	}
	
	public void updateViewOfDB(Player player) {
		//Object value, row, column
		model.setValueAt(1, player.getID()-1, 0);
		model.setValueAt(player.getName(), player.getID()-1, 1);
		model.setValueAt(player.getCurrentPosition().getName(), player.getID()-1, 2);
		model.setValueAt(player.getBalance(), player.getID()-1, 3);
		model.setValueAt(player.isInPrison(), player.getID()-1, 4);
		model.setValueAt(false, player.getID()-1, 5); //pardon needs implementation
		model.setValueAt(player.getColor(), player.getID()-1, 6);
		
	}
}
