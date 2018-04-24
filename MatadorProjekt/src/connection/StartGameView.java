package connection;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import controllers.GameController;
import controllers.MiniMonopoly;
import gameContent.Game;

public class StartGameView {

	private JFrame frame;
	private JButton Load;
	private JButton New;
	private SQLMethods sql;
	

	public StartGameView() {
		frame = new JFrame();
		Load = new JButton();
		New = new JButton();
		sql = new SQLMethods();
	}

	public void loadOrStartNewGame() {
		// Add two buttons - 'Load Game' and 'Start New Game'
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
				frame.dispose();
			}
		});

		frame.getContentPane().add(New);
		frame.getContentPane().add(Load);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void LoadGame() {
		System.out.println("LOAD GAME");
		frame.setVisible(false);
	}

	public void NewGame() { // virker ikke
		new Thread() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				super.run();
				System.out.println("NEW GAME");

				Game game = MiniMonopoly.createGame();
				game.shuffleCardDeck();

				GameController controller = new GameController(game);

				controller.initializeGUI();
				
				System.out.println("1");
				controller.createPlayers();
				System.out.println("2");
				controller.play();
				sql.resetDB();
			}

		}.start();

	}

}
