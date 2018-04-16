package main;

import connection.StartGameView;
import controllers.GameController;
import controllers.MiniMonopoly;
import gameContent.Game;

public class Main {

	/**
	 * The main method which creates a game, shuffles the chance cards, creates
	 * players, and then starts the game.
	 * 
	 * @param args
	 *            not used
	 */
	public static void main(String[] args) {

//		StartGameView s = new StartGameView();
//		s.loadOrStartNewGame();

		Game game = MiniMonopoly.createGame();
		game.shuffleCardDeck();

		GameController controller = new GameController(game);
		controller.initializeGUI();
		controller.createPlayers();

		controller.play();
	}
}
