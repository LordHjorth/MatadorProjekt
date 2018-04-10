package main;

import controllers.GameController;
import controllers.MiniMonopoly;
import gameContent.Game;

public class Main {
	
	/**
	 * The main method which creates a game, shuffles the chance
	 * cards, creates players, and then starts the game.
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		Game game = MiniMonopoly.createGame();
		game.shuffleCardDeck();
		connection.Connector con = new connection.Connector();
		con.dbConnect();
		
		GameController controller = new GameController(game);
		controller.createPlayers();
		controller.initializeGUI();
		
		controller.play();
	}

}