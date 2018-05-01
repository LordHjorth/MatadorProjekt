package controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import cards.CardMove;
import cards.CardMoveToShipping;
import cards.CardPayMoney;
import cards.CardReceiveMoneyFromBank;
import cards.GotoJail;
import cards.Legat;
import cards.PardonCard;
import cards.PayPropertyTax;
import cards.Relativemove;
import gameContent.Card;
import gameContent.Game;
import gameContent.Space;
import properties.Brewery;
import properties.RealEstate;
import properties.Shipping;
import spaces.Chance;
import spaces.Jail;
import spaces.Parking;
import spaces.Start;
import spaces.Tax;

/**
 * Main class for setting up and running a (Mini-)Monoploy game.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class MiniMonopoly {

	/**
	 * Creates the initial static situation of a Monopoly game. Note that the
	 * players are not created here, and the chance cards are not shuffled here.
	 * 
	 * @return the initial game board and (not shuffled) deck of chance cards
	 */
	public static Game createGame() {
		// Create the initial Game set up

		Game game = new Game();

		Start go = new Start();
		go.setName("Start");
		game.addSpace(go);

		RealEstate p = new RealEstate();
		p.setName("Rødovrevej");
		p.setCost(1200);
		p.setRent(50);
		p.setHouseCost();
		p.setColor(new Color(75, 155, 225));
		p.setCategory(p.categories[2]); // Vestegnen
		game.addSpace(p);

		Chance chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Hvidovrevej");
		p.setCost(1200);
		p.setRent(50);
		p.setHouseCost();
		p.setCategory(p.categories[2]); // Vestegnen
		p.setColor(new Color(75, 155, 225));
		game.addSpace(p);

		Tax t = new Tax();
		t.setName("Betal indkomstskat");
		game.addSpace(t);

		Shipping s = new Shipping();
		s.setName("Samsøfærgen");
		s.setCost(4000);
		s.setRent(500);
		p.setHouseCost();
		s.setCategory(s.categories[0]); // Ships
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Roskildevej");
		p.setCost(2000);
		p.setRent(100);
		p.setHouseCost();
		p.setCategory(p.categories[3]); // Valby
		p.setColor(new Color(255, 130, 102));
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Valby Langgade");
		p.setCost(2000);
		p.setRent(100);
		p.setHouseCost();
		p.setCategory(p.categories[3]); // Valby
		p.setColor(new Color(255, 130, 102));
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Allégade");
		p.setCost(2400);
		p.setRent(150);
		p.setHouseCost();
		p.setCategory(p.categories[3]); // Valby
		p.setColor(new Color(255, 130, 102));
		game.addSpace(p);

		Jail jail = new Jail();
		jail.setName("Fængsel");
		game.addSpace(jail);

		p = new RealEstate();
		p.setName("Frederiksberg Allé");
		p.setCost(2800);
		p.setRent(200);
		p.setHouseCost();
		p.setCategory(p.categories[4]); // Frederiksberg
		p.setColor(new Color(204, 204, 0));
		game.addSpace(p);

		Brewery b = new Brewery();
		b.setName("Tuborg");
		b.setCost(3000);
		b.setRent(100);
		b.setCategory(b.categories[1]); // Brewery
		game.addSpace(b);

		p = new RealEstate();
		p.setName("Bülowsvej");
		p.setCost(2800);
		p.setRent(200);
		p.setHouseCost();
		p.setCategory(p.categories[4]); // Frederiksberg
		p.setColor(new Color(204, 204, 0));
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Gl. Kongevej");
		p.setCost(3200);
		p.setRent(250);
		p.setHouseCost();
		p.setCategory(p.categories[4]); // Frederiksberg
		p.setColor(new Color(204, 204, 0));
		game.addSpace(p);

		s = new Shipping();
		s.setName("Greenaa-Hundested");
		s.setCost(4000);
		s.setRent(500);
		s.setCategory(s.categories[0]); // Ship
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Berntoffsvej");
		p.setCost(3600);
		p.setRent(300);
		p.setHouseCost();
		p.setCategory(p.categories[5]); // Hellerup
		p.setColor(Color.GRAY);
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Hellerupvej");
		p.setCost(3600);
		p.setRent(300);
		p.setHouseCost();
		p.setColor(Color.GRAY);
		p.setCategory(p.categories[5]); // Hellerup
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Strandvejen");
		p.setCost(4000);
		p.setRent(350);
		p.setHouseCost();
		p.setColor(Color.GRAY);
		p.setCategory(p.categories[5]); // Hellerup
		game.addSpace(p);

		Space parking = new Parking();
		parking.setName("Gratis Parkering");
		game.addSpace(parking);

		p = new RealEstate();
		p.setName("Trianglen");
		p.setCost(4400);
		p.setRent(400);
		p.setHouseCost();
		p.setColor(new Color(255, 105, 180));
		p.setCategory(p.categories[6]); // Østerbro
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Østerbrogade");
		p.setCost(4400);
		p.setRent(400);
		p.setHouseCost();
		p.setColor(new Color(255, 105, 180));
		p.setCategory(p.categories[6]); // Østerbro
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Grønningen");
		p.setCost(4800);
		p.setRent(450);
		p.setHouseCost();
		p.setColor(new Color(255, 105, 180));
		p.setCategory(p.categories[6]); // Østerbro
		game.addSpace(p);

		s = new Shipping();
		s.setName("Molslinjen");
		s.setCost(4000);
		s.setRent(500);
		s.setCategory(s.categories[0]); // Ship
		game.addSpace(s);

		p = new RealEstate();
		p.setName("Bredgade");
		p.setCost(5200);
		p.setRent(500);
		p.setHouseCost();
		p.setColor(Color.white);
		p.setCategory(p.categories[7]); // Kgs. Nytorv
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Kgs. Nytorv");
		p.setCost(5200);
		p.setRent(500);
		p.setHouseCost();
		p.setColor(Color.white);
		p.setCategory(p.categories[7]); // Kgs. Nytorv
		game.addSpace(p);

		b = new Brewery();
		b.setName("Carlsberg");
		b.setCost(3000);
		b.setRent(100);
		b.setCategory(b.categories[1]); // Brewery
		game.addSpace(b);

		p = new RealEstate();
		p.setName("Østergade");
		p.setCost(5600);
		p.setRent(550);
		p.setHouseCost();
		p.setColor(Color.white);
		p.setCategory(p.categories[7]); // Kgs. Nytorv
		game.addSpace(p);

		Jail gotoPrison = new Jail();
		gotoPrison.setName("De fængsles");
		gotoPrison.setJail(true);
		game.addSpace(gotoPrison);

		p = new RealEstate();
		p.setName("Amagertorv");
		p.setCost(6000);
		p.setRent(600);
		p.setHouseCost();
		p.setColor(new Color(255, 255, 102));
		p.setCategory(p.categories[8]); // Indre By
		game.addSpace(p);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Vimmelskaftet");
		p.setCost(6000);
		p.setRent(600);
		p.setHouseCost();
		p.setColor(new Color(255, 255, 102));
		p.setCategory(p.categories[8]); // Indre By
		game.addSpace(p);

		p = new RealEstate();
		p.setName("Nygade");
		p.setCost(6400);
		p.setRent(650);
		p.setHouseCost();
		p.setColor(new Color(255, 255, 102));
		p.setCategory(p.categories[8]); // Indre By
		game.addSpace(p);

		s = new Shipping();
		s.setName("Skandinavisk Linjetrafik");
		s.setCost(4000);
		s.setRent(500);
		s.setCategory(s.categories[0]); // Ship By
		game.addSpace(s);

		chance = new Chance();
		chance.setName("Chance");
		game.addSpace(chance);

		p = new RealEstate();
		p.setName("Frederiksberg gade");
		p.setCost(7800);
		p.setRent(700);
		p.setHouseCost();
		p.setColor(new Color(151, 46, 172));
		p.setCategory(p.categories[9]); // København K
		game.addSpace(p);

		t = new Tax();
		t.setName("Betal Ekstraordinær statsskat (2000kr)");
		game.addSpace(t);

		p = new RealEstate();
		p.setName("Rådhuspladsen");
		p.setCost(8000);
		p.setRent(750);
		p.setHouseCost();
		p.setColor(new Color(151, 46, 172));
		p.setCategory(p.categories[9]); // København K
		game.addSpace(p);

		// Chancecards
		List<Card> cards = new ArrayList<Card>();

		CardMoveToShipping shipping = new CardMoveToShipping();
		shipping.setText(
				"Ryk brikken frem til det nærmeste redderi og betal ejeren to gange den leje, han ellers er berettiget til. Hvis selskabet ikke ejes af nogen kan De købe det af banken.");
		cards.add(shipping);
		cards.add(shipping);

		Legat legat = new Legat();
		legat.setPaymentAmount(40000);
		legat.setValueLimit(15000);
		legat.setText(
				"De modtager Matador-legatet for værdig trængende, stort kr. 40.000. Ved værdig trængende forstås, at Deres formue, d.v.s. Deres kontante penge + skøder + bygninger ikke overstiger kr. 15.000.");
		cards.add(legat);

		Relativemove moveBack = new Relativemove();
		moveBack.setAmountTomove(-3);
		moveBack.setText("Ryk tre felter tilbage.");
		cards.add(moveBack);

		CardReceiveMoneyFromBank a = new CardReceiveMoneyFromBank();
		a.setText("Værdien af egen avl fra nyttehaven udgør kr. 200, som De modtager af banken.");
		a.setAmount(200);
		cards.add(a);

		CardPayMoney dentist = new CardPayMoney();
		dentist.setText("De har modtaget Deres tandlægeregning. Betal kr. 2.000.");
		dentist.setAmount(2000);
		cards.add(dentist);

		PayPropertyTax propertytax = new PayPropertyTax();
		propertytax.setText("Ejendomsskatterne er steget, ekstraudgifterne er: kr. 800 pr. hus");
		cards.add(propertytax);

		CardPayMoney parkingTicket = new CardPayMoney();
		parkingTicket.setText("De har måttet vedtage en parkeringsbøde. Betal kr. 200 i bøde.");
		parkingTicket.setAmount(200);
		cards.add(parkingTicket);

		CardReceiveMoneyFromBank receive = new CardReceiveMoneyFromBank();
		receive.setText("Kommunen har eftergivet et kvartals skat. Hæv i banken kr. 3.000.");
		receive.setAmount(3000);
		cards.add(receive);

		CardPayMoney carInsurance = new CardPayMoney();
		carInsurance.setText("Betal Deres bilforsikring kr. 1.000.");
		carInsurance.setAmount(1000);
		cards.add(carInsurance);

		CardPayMoney ticket = new CardPayMoney();
		ticket.setText("De har kørt frem for Fuld Stop. Betal kr. 1.000 i bøde.");
		ticket.setAmount(1000);
		cards.add(ticket);

		PardonCard pardon = new PardonCard();
		pardon.setText(
				"I anledning af kongens fødselsdag benådes De herved for fængsel. Dette kort kan opbevares, indtil De får brug for det, eller De kan sælge det. ");
		cards.add(pardon);
		cards.add(pardon);

		CardPayMoney cigarats = new CardPayMoney();
		cigarats.setText("De har været en tur i udlandet og haft for mange cigaretter med hjem. Betal told kr. 200.");
		cigarats.setAmount(200);
		cards.add(cigarats);

		CardReceiveMoneyFromBank c = new CardReceiveMoneyFromBank();
		c.setText("De havde en række med elleve rigtige i tipning. Modtag kr. 1.000.");
		c.setAmount(1000);
		cards.add(c);

		CardPayMoney repair = new CardPayMoney();
		repair.setText("Betal kr. 3.000 for reparation af Deres vogn.");
		repair.setAmount(3000);
		cards.add(repair);

		CardReceiveMoneyFromBank lottery = new CardReceiveMoneyFromBank();
		lottery.setText("De har vundet i Klasselotteriet. Modtag kr. 500.");
		lottery.setAmount(500);
		cards.add(lottery);

		CardReceiveMoneyFromBank bonds = new CardReceiveMoneyFromBank();
		bonds.setText("Deres præmieobligation er kommet ud. De modtager kr. 1.000 af banken.");
		bonds.setAmount(1000);
		cards.add(bonds);

		CardReceiveMoneyFromBank equities = new CardReceiveMoneyFromBank();
		equities.setText("Modtag udbytte af Deres aktier kr. 1.000.");
		equities.setAmount(1000);
		cards.add(equities);

		GotoJail prison = new GotoJail();
		prison.setText(
				"Gå i fængsel. Ryk direkte til fængslet. Selv om De passerer Start, indkasserer De ikke kr. 4.000.");
		cards.add(prison);
		cards.add(prison);

		CardMove Rådhuspladsen = new CardMove();
		Rådhuspladsen.setTarget(game.getSpaces().get(39));
		Rådhuspladsen.setText("Tag ind på Rådhuspladsen.");
		cards.add(Rådhuspladsen);

		CardMove Frederiksberg = new CardMove();
		Frederiksberg.setTarget(game.getSpaces().get(11));
		Frederiksberg.setText("Ryk frem til Frederiksberg Allé. Hvis De passerer Start, indkassér kr. 4.000.");
		cards.add(Frederiksberg);

		CardReceiveMoneyFromBank equitiesTwo = new CardReceiveMoneyFromBank();
		equitiesTwo.setText("Modtag udbytte af Deres aktier kr. 1.000.");
		equitiesTwo.setAmount(1000);
		cards.add(equitiesTwo);

		// TODO modtager penge fra hver enkelt spiller
		CardReceiveMoneyFromBank birthday = new CardReceiveMoneyFromBank();
		birthday.setText("Det er deres fødselsdag. Modtag af hver medspiller kr. 200.");
		birthday.setAmount(200);
		cards.add(birthday);

		CardMove start = new CardMove();
		start.setTarget(game.getSpaces().get(0));
		start.setText("Ryk frem til Start.");
		cards.add(start);

		CardPayMoney repairTwo = new CardPayMoney();
		repairTwo.setText("Betal kr. 3.000 for reparation af Deres vogn.");
		repairTwo.setAmount(3000);
		cards.add(repairTwo);

		CardReceiveMoneyFromBank salaryIncrease = new CardReceiveMoneyFromBank();
		salaryIncrease.setText("Grundet dyrtiden har De fået gageforhøjelse. Modtag kr. 1.000.");
		salaryIncrease.setAmount(1000);
		cards.add(salaryIncrease);

		// TODO evt ny metode
		// PayPropertyTax oilTax = new PayPropertyTax();
		// oilTax.setText("Oliepriserne er steget, og De skal betale: kr. 500 pr. hus,
		// kr. 2.000 pr. hotel.");
		// cards.add(oilTax);

		CardReceiveMoneyFromBank prize = new CardReceiveMoneyFromBank();
		prize.setText("Deres præmieobligation er kommet ud. De modtager kr. 1.000 af banken.");
		prize.setAmount(1000);
		cards.add(prize);

		CardMove grønningen = new CardMove();
		grønningen.setTarget(game.getSpaces().get(24));
		grønningen.setText("Ryk frem til Grønningen. Hvis De passerer Start, indkassér da kr. 4.000.");
		cards.add(grønningen);

		CardReceiveMoneyFromBank bank = new CardReceiveMoneyFromBank();
		bank.setText("You receive 100$ from the bank.");
		bank.setAmount(100);
		cards.add(bank);

		game.setCardDeck(cards);

		return game;
	}

}
