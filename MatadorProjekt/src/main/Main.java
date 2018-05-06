package main;

import view.StartGameView;

public class Main {

	/**
	 * The main method which creates a game, shuffles the chance cards, creates
	 * players, and then starts the game.
	 * 
	 * @param args
	 *            not used
	 *            
	 *  @author Rasmus
	 */
	public static void main(String[] args) {

		StartGameView s = new StartGameView();
		s.loadOrStartNewGame();

	}
}
