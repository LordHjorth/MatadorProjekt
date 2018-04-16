package connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class StartGameView {
	
	 JFrame frame;
	 JButton Load;
	 JButton New;
	 
	 public StartGameView() {
		 frame = new JFrame();
		 Load = new JButton();
		 New = new JButton();
	 }
	
	public void loadOrStartNewGame() {
		//Add two buttons - 'Load Game' and 'Start New Game'
		frame.setSize(300, 150);
		frame.setLayout(null);
		
		Load.setText("Load Game");
		Load.setBounds(100, 20, 100, 20);
		Load.setEnabled(true);
		Load.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
			    LoadGame();
			  } 
		});
		
		New.setText("New Game");
		New.setBounds(100, 60, 100, 20);
		New.setEnabled(true);
		New.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { 
			    NewGame();
			  } 
		});
		
		frame.getContentPane().add(New);
		frame.getContentPane().add(Load);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void LoadGame() {
		System.out.println("LOAD");
	}
	
	public void NewGame() {
		System.out.println("NEW");
	}

}
