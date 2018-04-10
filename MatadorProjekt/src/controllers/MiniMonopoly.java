package controllers;

import java.util.ArrayList;
import java.util.List;

import cards.CardMove;
import cards.CardReceiveMoneyFromBank;
import cards.PayTax;
import gameContent.Card;
import gameContent.Chance;
import gameContent.Game;
import gameContent.Property;
import gameContent.Space;
import gameContent.Tax;
import properties.Utility;

/**
 * Main class for setting up and running a (Mini-)Monoploy game.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class MiniMonopoly {
	
	/**
	 * Creates the initial static situation of a Monopoly game. Note
	 * that the players are not created here, and the chance cards
	 * are not shuffled here.
	 * 
	 * @return the initial game board and (not shuffled) deck of chance cards 
	 */
	public static Game createGame() {

		// Create the initial Game set up (note that, in this simple
		// setup, we use only 11 spaces). Note also that this setup
		// could actually be loaded from a file or database instead
		// of creating it programmatically.
		Game game = new Game();
		
		Space go = new Space();
		go.setName("Go");
		game.addSpace(go);
		
		Property p = new Property();
		p.setName("R�dovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		
		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Hvidovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		
		Tax t = new Tax();
		t.setName("Pay tax (10% on Cash)");
		game.addSpace(t);

		Utility s = new Utility();
		s.setName("�resund");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);

		p = new Property();
		p.setName("Roskildevej");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new Property();
		p.setName("Valby Langgade");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		
		p = new Property();
		p.setName("All�gade");
		p.setCost(2400);
		p.setRent(150);
		game.addSpace(p);
		
		Space prison = new Space();
		prison.setName("Prison");
		game.addSpace(prison);
		
		p = new Property();
		p.setName("Frederiksberg All�");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Coca-Cola Tapperi");
		p.setCost(3000);
		p.setRent(300);
		game.addSpace(p);
		
		p = new Property();
		p.setName("B�lowsvej");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		
		p = new Property();
		p.setName("Gl. Kongevej");
		p.setCost(3200);
		p.setRent(250);
		game.addSpace(p);
		
		List<Card> cards = new ArrayList<Card>();
		
		CardMove move = new CardMove();
		move.setTarget(game.getSpaces().get(9));
		move.setText("Move to All�gade!");
		cards.add(move);
		
		PayTax tax = new PayTax();
		tax.setText("Pay 10% income tax!");
		cards.add(tax);
		
		CardReceiveMoneyFromBank b = new CardReceiveMoneyFromBank();
		b.setText("You receive 100$ from the bank.");
		b.setAmount(100);
		cards.add(b);
		game.setCardDeck(cards);

		return game;
	}

}
