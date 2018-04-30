package connection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import controllers.View;
import gameContent.Player;
import gameContent.Property;

public class PropertyView {

	private SQLMethods sql;
	private JFrame frame;
	private JTable table;
	private DefaultTableModel model;
	private Object[] columns;
	private final int numberOfProperties = 1;

	/**
	 * Instantiates a new property view.
	 */
	public PropertyView() {
		sql = new SQLMethods();
		frame = new JFrame();
		columns = new Object[] { sql.PROPERTY_NAME, sql.NAME, sql.HOUSES }; 
		model = new DefaultTableModel(numberOfProperties, columns.length);
	}

	/**
	 * Creates the property view.
	 */
	public void createPropertyView() {	
		model.setColumnIdentifiers(columns);
		table = new JTable(model);
		frame.add(table);
		frame.add(table.getTableHeader(), BorderLayout.PAGE_START);
		frame.setSize(200, 1000);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setBackground(Color.blue);

	}

	/**
	 * Update property view.
	 *
	 * @param prop the prop
	 * @param player the player
	 */
	public void updatePropertyView(Property prop, Player player) {
		
		sql.addPropertyToPlayer(prop, player);
		ResultSet rs = sql.getPropertyByPlayer();
		
		try {
			rs.last();			
			Object[] row = new Object[columns.length];
			row[0] = rs.getString(sql.PROPERTY_NAME);
			row[1] = rs.getString(sql.NAME);
			row[2] = rs.getInt(sql.HOUSES);
			model.addRow(row);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
