package test;
import java.util.List;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import controllers.GameController;
import controllers.MiniMonopoly;
import gameContent.Game;
import gameContent.Player;
import gameContent.Space;
import properties.RealEstate;

public class Test {
  
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@org.junit.Test
	public void testgetPlayerValue() {
		Player player= new Player();
		Game game=MiniMonopoly.createGame();
		GameController controller= new GameController(game);
		player.setBalance(13000); //13000 kr balance
		List<Space> feltliste=controller.getSpaces();
	
		
		player.addOwnedProperty((RealEstate) feltliste.get(1)); //1200 kr grund
		player.addOwnedProperty((RealEstate) feltliste.get(3)); //1200 kr grund
	
		RealEstate rødovre= (RealEstate) player.getProperty((RealEstate) feltliste.get(1)); 
		rødovre.addHouses(5); //120kr *5 huse =600kr 
	    int test1= controller.getPlayerValue(player);
		Assert.assertEquals("Wrong Value",16000,test1);//13000+1200+1200+600 = 16000
		player.addOwnedProperty((RealEstate) feltliste.get(39)); //8000 kr grund
		RealEstate rådhus= (RealEstate) player.getProperty((RealEstate) feltliste.get(39)); 
		rådhus.addHouses(4); //800 kr* 4 huse = 3200 kr
		int test2= controller.getPlayerValue(player);
		
		
	    Assert.assertEquals("Wrong Value",27200,test2); //16000+8000+3200 
		

	}

}
