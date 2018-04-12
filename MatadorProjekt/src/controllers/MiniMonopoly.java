package controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cards.CardMove;
import cards.CardReceiveMoneyFromBank;
import cards.PayTax;
import gameContent.Card;
import gameContent.Chance;
import gameContent.Game;
import gameContent.Jail;
import gameContent.Parking;
import gameContent.Property;
import gameContent.Space;
import gameContent.Start;
import gameContent.Tax;
import properties.Brewery;
import properties.RealEstate;
import properties.Shipping;
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
		
		Start go = new Start();
		go.setName("Start");
		game.addSpace(go);
		
		Property p = new RealEstate();
		p.setName("Rødovrevej");
		p.setCost(1200);
		p.setRent(50);
		p.setColor(new Color(75, 155, 225));
		game.addSpace(p);
		
		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Hvidovrevej");
		p.setCost(1200);
		p.setRent(50);
		game.addSpace(p);
		p.setColor(new Color(75, 155, 225));
		
		Tax t = new Tax();
		t.setName("Betal indkomstskat");
		game.addSpace(t);

		Utility s = new Shipping();
		s.setName("Samsøfærgen");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);
		

		p = new RealEstate();
		p.setName("Roskildevej");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		p.setColor(new Color(255,130,102));
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Valby Langgade");
		p.setCost(2000);
		p.setRent(100);
		game.addSpace(p);
		p.setColor(new Color(255,130,102));
		
		p = new RealEstate();
		p.setName("Allégade");
		p.setCost(2400);
		p.setRent(150);
		game.addSpace(p);
		p.setColor(new Color(255,130,102));
		
		Space jail= new Jail();
		jail.setName("Fængsel");
		game.addSpace(jail);
		
		p = new RealEstate();
		p.setName("Frederiksberg Allé");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		p.setColor(new Color(204,204,0));
		
		p = new Brewery();
		p.setName("Tuborg");
		p.setCost(3000);
		p.setRent(300);
		game.addSpace(p);
		
		
		p = new RealEstate();
		p.setName("Bülowsvej");
		p.setCost(2800);
		p.setRent(200);
		game.addSpace(p);
		p.setColor(new Color(204,204,0));
		
		
		p = new RealEstate();
		p.setName("Gl. Kongevej");
		p.setCost(3200);
		p.setRent(250);
		game.addSpace(p);
		p.setColor(new Color(204,204,0));
		
		
		s = new Shipping();
		s.setName("Greenaa-Hundested");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);
		
		p = new RealEstate();
		p.setName("Berntoffsvej");
		p.setCost(3600);
		p.setRent(300);
		game.addSpace(p);
		p.setColor(Color.GRAY);
		
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Hellerupvej");
		p.setCost(3600);
		p.setRent(300);
		game.addSpace(p);
		p.setColor(Color.GRAY);
		
		p = new RealEstate();
		p.setName("Strandvejen");
		p.setCost(4000);
		p.setRent(350);
		game.addSpace(p);
		p.setColor(Color.GRAY);
		
		Space parking= new Parking();
		parking.setName("Gratis Parkering");
		game.addSpace(parking);
		
		p = new RealEstate();
		p.setName("Trianglen");
		p.setCost(4400);
		p.setRent(400);
		game.addSpace(p);
		p.setColor(new Color(255,51,51));
	
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Østerbrogade");
		p.setCost(4400);
		p.setRent(400);
		game.addSpace(p);
		p.setColor(new Color(255,51,51));
		
		
		p = new RealEstate();
		p.setName("Grønningen");
		p.setCost(4800);
		p.setRent(450);
		game.addSpace(p);
		p.setColor(new Color(255,51,51));
		
		
		s = new Shipping();
		s.setName("Molslinjen");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);
		
		p = new RealEstate();
		p.setName("Bredgade");
		p.setCost(5200);
		p.setRent(500);
		game.addSpace(p);
		p.setColor(Color.white);
		
		p = new RealEstate();
		p.setName("Kgs. Nytorv");
		p.setCost(5200);
		p.setRent(500);
		game.addSpace(p);
		p.setColor(Color.white);
		
		p = new Brewery();
		p.setName("Carlsberg");
		p.setCost(3000);
		p.setRent(300);
		game.addSpace(p);
	
		
		p = new RealEstate();
		p.setName("Østergade");
		p.setCost(5600);
		p.setRent(550);
		game.addSpace(p);
		p.setColor(Color.white);
		
		Space gotoPrison = new Jail();
		gotoPrison.setName("De fængsles");
		game.addSpace(gotoPrison);
		
		p = new RealEstate();
		p.setName("Amagertorv");
		p.setCost(6000);
		p.setRent(600);
		game.addSpace(p);
		p.setColor(new Color(255,255,102));
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Vimmelskaftet");
		p.setCost(6000);
		p.setRent(600);
		game.addSpace(p);
		p.setColor(new Color(255,255,102));
		
		p = new RealEstate();
		p.setName("Nygade");
		p.setCost(6400);
		p.setRent(650);
		game.addSpace(p);
		p.setColor(new Color(255,255,102));
		
		s = new Shipping();
		s.setName("Skandinavisk Linjetrafik");
		s.setCost(4000);
		s.setRent(500);
		game.addSpace(s);
		
		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);
		
		p = new RealEstate();
		p.setName("Frederiksberg gade");
		p.setCost(7800);
		p.setRent(700);
		p.setColor(new Color(151,46,172));
		game.addSpace(p);
		
		t = new Tax();
		t.setName("Betal Ekstraordinær statsskat (2000kr)");
		game.addSpace(t);

		
		p = new RealEstate();
		p.setName("Rådhuspladsen");
		p.setCost(8000);
		p.setRent(750);
		p.setColor(new Color(151,46,172));
		game.addSpace(p);
		
		
		//Chancecards
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
