package test;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import controllers.GameController;
import controllers.MiniMonopoly;
import gameContent.Card;
import gameContent.Game;
import gameContent.Player;
import gameContent.Space;
import properties.RealEstate;

public class Test {
	Player player1;
	Player player2;
	GameController controller;
	Game game;
	List<Space> feltliste;

	@Before
	public void setUp() throws Exception {
		player1 = new Player();
		player2 = new Player();
		game = MiniMonopoly.createGame();
		this.controller = new GameController(game);
		player1.setBalance(13000); // 13000 kr balance
		
		feltliste = controller.getSpaces();
		

	}

	@After
	public void tearDown() throws Exception {

	}
	@org.junit.Test
	public void testOwnedProperties() {
		RealEstate rødovre = (RealEstate) feltliste.get(1);
		RealEstate hvidovre = (RealEstate) feltliste.get(3);
		player1.addOwnedProperty(rødovre); 
		player1.addOwnedProperty(hvidovre); 
		int i= player1.getOwnedProperties().size();   //2 properties added, so the size of the set should be 2.
		Assert.assertEquals("Wrong Value", 2, i); 
		player1.removeAllProperties(); // Method called to remove all properties meaning the size of the set should now be 0.
		int j=player1.getOwnedProperties().size();
		Assert.assertEquals("Wrong Value", 0, j); 
		
	}
	

	@org.junit.Test
	public void testgetPlayerValue() {
		
		player1.addOwnedProperty((RealEstate) feltliste.get(1)); // 1200 kr grund
		
		player1.addOwnedProperty((RealEstate) feltliste.get(3)); // 1200 kr grund
		RealEstate rødovre = (RealEstate) feltliste.get(1);
		
		rødovre.addHouses(5); // 120kr *5 huse =600kr
		System.out.println(player1.getPlayerValue());
		int test1 = player1.getPlayerValue();
		Assert.assertEquals("Wrong Value", 16000, test1);// 13000+1200+1200+600 = 16000
		player1.addOwnedProperty((RealEstate) feltliste.get(39)); // 8000 kr grund
		RealEstate rådhus = (RealEstate) feltliste.get(39);
		rådhus.addHouses(4); // 800 kr* 4 huse = 3200 kr
		System.out.println(player1.getPlayerValue());
		int test2 = player1.getPlayerValue();
		Assert.assertEquals("Wrong Value", 27200, test2); // 16000+8000+3200

	}

	@org.junit.Test
	public void testPayment() {
		RealEstate rødovre = (RealEstate) feltliste.get(1);
		RealEstate hvidovre = (RealEstate) feltliste.get(3);

		player1.payMoney(rødovre.getCost()); // 1200 kr grund
		player1.payMoney(hvidovre.getCost()); // 1200 kr grund

		int i = player1.getBalance();

		Assert.assertEquals("Wrong Value", 10600, i); // 13000-2400=10600

	}
	@org.junit.Test
	public void testReceiveMoney() {
		RealEstate rødovre = (RealEstate) feltliste.get(1);
		RealEstate hvidovre = (RealEstate) feltliste.get(3);

		player1.receiveMoney(rødovre.getCost()); // 1200 kr grund
		player1.receiveMoney(hvidovre.getCost()); // 1200 kr grund

		int i = player1.getBalance();

		Assert.assertEquals("Wrong Value", 15400, i); // 13000+2400=15400

	}

	@org.junit.Test
	public void testgotoJail() {
		controller.gotoJail(player1);
		boolean bol = player1.isInPrison();
		Space space = player1.getCurrentPosition();
		Assert.assertEquals("Wrong Value", true, bol); // Spillerens boolean value bliver sat til true når gotoJail
														// metoden kaldes.
		Assert.assertEquals("Wrong Value", feltliste.get(10), space); // Spillerens position vil være på index 10, jail
																		// feltet.

	}

	@org.junit.Test
	public void testGetListOfPlayers() {
		game.addPlayer(player1); //Der tilføjes 2 spillere til spillet, derfor bør metoden returnere 2.
		game.addPlayer(player2);
		int i = controller.getListOfPlayers().size();  

		Assert.assertEquals("Wrong Value", 2, i);

	}
	@org.junit.Test
	public void testCardMethods() {
		Card card=game.drawCardFromDeck();  
		player1.setOwnedCard(card);  //Henter et kort fra dækket og tilføjer det til spilleren.

		Assert.assertEquals("Wrong Value", player1.getOwnedCards().get(0), card);
		

	}
	
	

}
