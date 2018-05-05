package controllers;

import java.awt.Color;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import cards.PardonCard;
import connection.SQLMethods;
import connection.viewDB;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Game;
import gameContent.Player;
import gameContent.Space;
import properties.RealEstate;
import spaces.Jail;
import spaces.Property;

/**
 * The overall controller of a Monopoly game. It provides access to all basic
 * actions and activities for the game. All other activities of the game, should
 * be implemented by referring to the basic actions and activities in this
 * class. This is necessary, since the GUI does not support MVC yet.
 * 
 * Note that this controller is far from being finished and many things could be
 * done in a much nicer and cleaner way! But, it shows the general idea of how
 * the model, view (GUI), and the controller could work with each other, and how
 * different parts of the games activities can be separated from each other, so
 * that different parts can be added and extended independently from each other.
 * 
 * Note that it is crucial that all changes to the {@link gameContent.Game} need
 * to be made through this controller, since the GUI does not follow the MVC
 * pattern. For fully implementing the game, it will be necessary to add more of
 * these basic actions in this class.
 * 
 * The action methods of the {@link gameContent.Space} and the
 * {@link gameContent.Card} can be implemented based on the basic actions and
 * activities of this game controller. Then, the game controller takes care of
 * updating the GUI.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */

public class GameController {

	private Game game;
	private gui_main.GUI gui;
	private Map<String, List<Property>> category2Properties = new HashMap<String, List<Property>>();
	private View view;
	private viewDB vdb;
	private int diethrow;
	private boolean disposed = false;

	private SQLMethods sql; // added by gruppe B

	/**
	 * Constructor for a controller of a game.
	 * 
	 * @param game
	 *            the game
	 */
	public GameController(Game game) {
		super();
		this.game = game;
		sql = new SQLMethods(); // Added by gruppe B
		vdb = new viewDB();
		this.createcategoryList();

	}

	/**
	 * This method will be called when the game is started to create the
	 * participating players. Right now, the creation of players is hard-coded. But
	 * this should be done by interacting with the user.
	 * 
	 * @author gruppe B Should be more defensive programming so players could not
	 *         use the same name, as this makes errors occur with both the gui and
	 *         the database.
	 */
	public void createPlayers() {
		int NumberOfPlayers = gui.getUserInteger("Enter the amount of players (2-6)", 2, 6);
		String color = "blue";
		Object[][] data = new Object[NumberOfPlayers][];

		for (int i = 0; i < NumberOfPlayers; i++) {
			Player p = new Player();
			int playerNumber = i + 1;
			String name = gui.getUserString("Enter the name of Player " + playerNumber);
			p.setName(name);
			p.setCurrentPosition(game.getSpaces().get(0));
			p.setID(playerNumber);

			switch (i) {
			case 0:
				p.setColor(Color.blue);
				color = "Blue";
				break;
			case 1:
				p.setColor(Color.red);
				color = "Red";
				break;
			case 2:
				p.setColor(Color.black);
				color = "Black";
				break;
			case 3:
				p.setColor(Color.green);
				color = "Green";
				break;
			case 4:
				p.setColor(Color.yellow);
				color = "Yellow";
				break;
			case 5:
				p.setColor(Color.pink);
				color = "Pink";
				break;
			}
			game.addPlayer(p);

			try {
				sql.createPlayersInDB(p, i, color); // adds player to database
			} catch (Exception e) {
				e.printStackTrace();
			}
			// "GameID", "Name", "Position", "Balance", "Prisoner", "Pardon available",
			// "CarColor"
			data[i] = new Object[] { 1, p.getName(), 0, p.getBalance(), false, false, "" }; // gameID = 1 for now.
		}
		vdb.createViewOfDB(data); // adds player info to a view
		view.createPlayers();
	}

	/**
	 * Load players from DB to game logic and gui.
	 */
	public void LoadPlayers() {
		int AmountOfPlayers = sql.getNumberOfPlayers();
		ResultSet rs = sql.LoadPlayers();
		Object[][] data = new Object[AmountOfPlayers][];
		int i = 0;

		try {
			while (!rs.equals(null) && rs.next()) {
				Player p = new Player();
				p.setID(rs.getInt("id"));
				p.setName(rs.getString("name"));
				p.setBalance(rs.getInt("balance"));
				p.setCurrentPosition(game.getSpaces().get(rs.getInt("PosIndex")));
				p.setInPrison(rs.getBoolean("inPrison"));

				/*
				 * - Not sure how to add a pardon card. if(rs.getBoolean("havePardon")==true) {
				 * 
				 * p.setOwnedCard(card); }
				 */

				String color = rs.getString("carColor");
				switch (color) {
				case "Blue":
					p.setColor(Color.blue);
					break;
				case "Red":
					p.setColor(Color.red);
					break;
				case "Black":
					p.setColor(Color.black);
					break;
				case "Green":
					p.setColor(Color.green);
					break;
				case "Yellow":
					p.setColor(Color.yellow);
					break;
				case "Pink":
					p.setColor(Color.pink);
					break;
				}
				game.addPlayer(p);
				data[i] = new Object[] { 1, p.getName(), 0, p.getBalance(), p.isInPrison(), false, color }; // gameID =
																											// 1 for
																											// now.
				i++;

			}
			vdb.createViewOfDB(data); // adds player info to a view
			view.createPlayers();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method will initialize the GUI. It should be called after the players of
	 * the game are created.
	 */
	public void initializeGUI() {
		this.view = new View(game);
		gui = view.createGUI();
	}

	/**
	 * The main method to start the game with the given player. The game is started
	 * with the current player of the game; this makes it possible to resume a game
	 * at any point.
	 */
	public void play() {
		List<Player> players = game.getPlayers();
		Player c = game.getCurrentPlayer();

		int current = 0;
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			if (c.equals(p)) {
				current = i;
			}
		}

		boolean terminated = false;
		while (!terminated) {
			Player player = players.get(current);
			if (!player.isBroke()) {
				try {
					this.makeMove(player);
				} catch (PlayerBrokeException e) {
					// We could react to the player having gone broke
				}
			}

			// Check whether we have a winner
			Player winner = null;
			int countActive = 0;
			for (Player p : players) {
				if (!p.isBroke()) {
					countActive++;
					winner = p;
				}
			}
			if (countActive == 1) {
				gui.showMessage("Player " + winner.getName() + " has won with " + winner.getBalance() + "kr.");
				break;
			} else if (countActive < 1) {
				// This can actually happen in very rare conditions and only
				// if the last player makes a stupid mistake (like buying something
				// in an auction in the same round when the last but one player went
				// bankrupt)
				gui.showMessage("All players are broke.");
				break;

			}
			
			this.updateOwnedCategories(player);
			
			boolean offerrunning = true;
			do {
				String selection = gui.getUserButtonPressed("Player " +player.getName()+ ", what do you want to do?", "Trade", "Buy Houses",
						"Sell Houses", "Mortage Property", "Continue Game");
				switch (selection) {
				case "Trade":
					this.offerToTrade(player);
					break;
				case "Buy Houses":
					this.offerToBuyHouse(player);
					break;
				case "Sell Houses":
					this.offerToSellHouses(player);
					break;
				case "Mortage Property":
					this.offerToMortgageProperty(player);
					break;
				case "Continue Game":
					offerrunning = false;
					break;
				default:
					offerrunning = false;
					break;

				}

			} while (offerrunning);
			this.updateBoard();
			current = (current + 1) % players.size();
			game.setCurrentPlayer(players.get(current));
			vdb.updatePlayerView(game.getPlayers());
			// if (current == 0) {
			// String selection = gui.getUserButtonPressed("A round is finished. Do you want
			// to continue the game?",
			// "yes", "no");
			// if (selection.equals("no")) {
			// terminated = true;
			// }
			// }
		}

		dispose();
	}

	/**
	 * This method implements a activity of asingle move of the given player. It
	 * throws a {@link exceptions.PlayerBrokeException} if the player goes broke in
	 * this move. Note that this is still a very basic implementation of the move of
	 * a player; many aspects are still missing.
	 * 
	 * @param player
	 *            the player making the move
	 * @throws PlayerBrokeException
	 *             if the player goes broke during the move
	 */
	public void makeMove(Player player) throws PlayerBrokeException {

		boolean castDouble;
		int doublesCount = 0;

		if (!player.getOwnedCards().isEmpty() && player.isInPrison()) {
			if (gui.getUserLeftButtonPressed("Do you want to use your pardon card?", "Yes", "No") == true) {

				player.setInPrison(false);
				player.removeOwnedCard();

			}
		}
		if (player.isInPrison()) {
			if (gui.getUserLeftButtonPressed(
					"Player " + player.getName() + " is in prison. Do you want to pay you out or cast a double",
					"Pay 1000", "roll dice") == true)

			{
				player.setInPrison(false);
				player.payMoney(1000);
			}
		}

		do {
			int die1 = 3;//(int) (1 + 6.0 * Math.random());
			int die2 = 2;//(int) (1 + 6.0 * Math.random());
			castDouble = (die1 == die2);
			gui.setDice(die1, die2);
			this.setDieThrow(die1, die2);

			if (player.isInPrison() && castDouble) {
				player.setInPrison(false);
				gui.showMessage("Player " + player.getName() + " leaves prison now since he cast a double!");
			} else if (player.isInPrison()) {
				gui.showMessage("Player " + player.getName() + " stays in prison since he did not cast a double!");
			}

			if (castDouble) {
				doublesCount++;
				if (doublesCount > 2) {
					gui.showMessage("Player " + player.getName() + " has cast the third double and goes to jail!");
					gotoJail(player);
					return;
				}
			}
			if (!player.isInPrison()) {
				// make the actual move by computing the new position and then
				// executing the action moving the player to that space
				int pos = player.getCurrentPosition().getIndex();
				List<Space> spaces = game.getSpaces();
				int newPos = (pos + die1 + die2) % spaces.size();
				Space space = spaces.get(newPos);

				moveToSpace(player, space);
				if (castDouble) {
					gui.showMessage("Player " + player.getName() + " cast a double and makes another move.");
				}
			}
		} while (castDouble);
	}

	/**
	 * This method implements the activity of moving the player to the new position,
	 * including all actions associated with moving the player to the new position.
	 * 
	 * @param player
	 *            the moved player
	 * @param space
	 *            the space to which the player moves
	 * @throws PlayerBrokeException
	 *             when the player goes broke doing the action on that space
	 */
	public void moveToSpace(Player player, Space space) throws PlayerBrokeException {
		int posOld = player.getCurrentPosition().getIndex();
		player.setCurrentPosition(space);

		if (posOld > player.getCurrentPosition().getIndex()) {
			gui.showMessage(
					"Player " + player.getName() + " receives " + game.getMoneyForPassingGo() + " kr. for passing Go!");
			this.paymentFromBank(player, game.getMoneyForPassingGo());
		}
		gui.showMessage(
				"Player " + player.getName() + " arrives at " + space.getIndex() + ": " + space.getName() + ".");

		// Execute the action associated with the respective space. Note
		// that this is delegated to the field, which implements this action
		space.doAction(this, player);
	}

	/**
	 * The method implements the action of a player going directly to jail.
	 * 
	 * @param player
	 *            the player going to jail
	 */
	public void gotoJail(Player player) {
		List<Jail> jailFields = new ArrayList<>();
		for (Space space : game.getSpaces()) {
			if (space instanceof Jail) {
				jailFields.add((Jail) space);
			}
		}
		player.setCurrentPosition(jailFields.get(0));
		player.setInPrison(true);

	}

	/**
	 * The method implementing the activity of taking a chance card.
	 * 
	 * @param player
	 *            the player taking a chance card
	 * @throws PlayerBrokeException
	 *             if the player goes broke by this activity
	 */
	public void takeChanceCard(Player player) throws PlayerBrokeException {
		Card card = game.drawCardFromDeck();
		gui.displayChanceCard(card.getText());
		gui.showMessage("Player " + player.getName() + " draws a chance card.");
		if (card instanceof PardonCard) {
			player.setOwnedCard(card);

		}
		try {
			card.doAction(this, player);
		} finally {
			gui.displayChanceCard("done");
		}
	}

	/**
	 * This method implements the action returning a drawn card to the bottom of the
	 * deck.
	 * 
	 * @param card
	 *            returned card
	 */
	public void returnChanceCardToDeck(Card card) {
		game.returnCardToDeck(card);
	}

	/**
	 * This method implements the activity where a player can obtain cash by selling
	 * houses back to the bank, by mortgaging own properties, or by selling
	 * properties to other players. This is called, whenever the player does not
	 * have enough cash available for an action. If he does not at least free the
	 * given amount of money, he will be broke; this is to help the player make the
	 * right choices to free enough money.
	 * 
	 * @param player
	 *            the player
	 * @param amount
	 *            the amount the player should have available after the act
	 */
	public void obtainCash(Player player, int amount) {

		boolean offerrunning = true;
		do {
			String selection = gui.getUserButtonPressed("Player " + player.getName()+" How do you want to free money?", "Trade", "Sell Houses",
					"Mortage Property", "Continue game");
			switch (selection) {
			case "Trade":
				this.offerToTrade(player);
				break;
			case "Sell Houses":
				this.offerToSellHouses(player);
				break;
			case "Mortage Property":
				this.offerToMortgageProperty(player);
				break;
			case "Continue game":
				offerrunning = false;
				break;
			default:
				offerrunning = false;
				break;

			}

		} while (offerrunning);

	}

	/**
	 * This method implements the activity of offering a player to buy a property.
	 * This is typically triggered by a player arriving on an property that is not
	 * sold yet. If the player chooses not to buy, the property will be set for
	 * auction.
	 * 
	 * @param property
	 *            the property to be sold
	 * @param player
	 *            the player the property is offered to
	 * @throws PlayerBrokeException
	 *             when the player chooses to buy but could not afford it
	 */
	public void offerToBuy(Property property, Player player) throws PlayerBrokeException {
		if (player.getBalance() <= property.getCost()) {
			this.obtainCash(player, property.getCost());
		}

		String choice = gui.getUserButtonPressed("Player " + player.getName() + ": Do you want to buy "
				+ property.getName() + " for " + property.getCost() + "kr.?", "yes", "no");
		if (choice.equals("yes")) {
			try {
				paymentToBank(player, property.getCost());
			} catch (PlayerBrokeException e) {
				// if the payment fails due to the player being broke,
				// the an auction (among the other players is started
				auction(property, player);
				// then the current move is aborted by casting the
				// PlayerBrokeException again
				throw e;
			}
			player.addOwnedProperty(property);
			property.setOwner(player);
			vdb.updPropertyView(property, player);
			sql.addPropertyToPlayer(property, player);
			return;
		}

		// In case the player does not buy the property an auction
		// is started @rasmus.
		auction(property, player);
	}

	/**
	 * This method implements a payment activity to another player, which involves
	 * the player to obtain some cash on the way, in case he does not have enough
	 * cash available to pay right away. If he cannot free enough money in the
	 * process, the player will go bankrupt.
	 * 
	 * @param payer
	 *            the player making the payment
	 * @param amount
	 *            the payed amount
	 * @param receiver
	 *            the beneficiary of the payment
	 * @throws PlayerBrokeException
	 *             when the payer goes broke by this payment
	 */
	public void payment(Player payer, int amount, Player receiver) throws PlayerBrokeException {
		if (payer.getBalance() < amount) {
			obtainCash(payer, amount);
			if (payer.getBalance() < amount) {
				playerBrokeTo(payer, receiver);
				throw new PlayerBrokeException(payer);
			}
		}
		gui.showMessage("Player " + payer.getName() + " pays " + amount + "kr. to player " + receiver.getName() + ".");
		payer.payMoney(amount);
		receiver.receiveMoney(amount);
	}

	/**
	 * This method implements the action of a player receiving money from the bank.
	 * 
	 * @param player
	 *            the player receiving the money
	 * @param amount
	 *            the amount
	 */
	public void paymentFromBank(Player player, int amount) {
		player.receiveMoney(amount);
		gui.showMessage("Player " + player.getName() + " receives " + amount + " kr from the bank.");
	}

	/**
	 * This method implements the activity of a player making a payment to the bank.
	 * Note that this might involve the player to obtain some cash; in case he
	 * cannot free enough cash, he will go bankrupt to the bank.
	 * 
	 * @param player
	 *            the player making the payment
	 * @param amount
	 *            the amount
	 * @throws PlayerBrokeException
	 *             when the player goes broke by the payment
	 */
	public void paymentToBank(Player player, int amount) throws PlayerBrokeException {
		if (amount > player.getBalance()) {
			obtainCash(player, amount);
			if (amount > player.getBalance()) {
				playerBrokeToBank(player);
				throw new PlayerBrokeException(player);
			}
		}
		gui.showMessage("Player " + player.getName() + " pays " + amount + "kr to the bank.");
		player.payMoney(amount);
	}

	/**
	 * This method implements the activity of auctioning a property.
	 * 
	 * @param property
	 *            the property which is for auction
	 */
	public void auction(Property property, Player player) {
		gui.showMessage("Now, there is an auction of " + property.getName() + " and the price will start at "
				+ property.getCost());
		List<Player> AuctionGrantedPlayers = new ArrayList<Player>(game.getPlayers());
		List<Player> NotGrantedPlayers = new ArrayList<Player>();
		NotGrantedPlayers.add(game.getCurrentPlayer());
		int bid = 0;
		Player winner = null;

		for (Player biddingPlayer : AuctionGrantedPlayers) {
			if (NotGrantedPlayers.contains(biddingPlayer) || biddingPlayer.isBroke()) {
				continue;
			} else {
				int minimum = property.getCost() > bid + 1 ? property.getCost() : bid + 1; // makes sure to always use
																							// the most recent bid
				String answer = gui.getUserButtonPressed(
						"Would " + biddingPlayer.getName() + " like to bid? - the price is " + minimum, "Yes", "No");
				if (answer.equals("Yes")) {
					bid = gui.getUserInteger("Place your bid", minimum, 1000000000); // maximum bid of 1.000.000.000
					winner = biddingPlayer;
				} else {
					NotGrantedPlayers.add(biddingPlayer);
				}
			}
		}

		if (winner != null) {
			gui.showMessage(winner.getName() + " won the auction.");
			try {
				paymentToBank(winner, bid);
				winner.addOwnedProperty(property);
				vdb.updPropertyView(property, winner);
				sql.addPropertyToPlayer(property, winner);
				gui.showMessage("You've succesfully bought the property " + property.getName());
				property.setOwner(winner);
				property.setActualRent();
				this.updateOwnedCategories(winner);
			} catch (PlayerBrokeException e) {
				gui.showMessage(
						"You haven't bought the property " + property.getName() + " You didn't have enough money");
				e.printStackTrace();
			}
		} else {
			gui.showMessage("No winner - auction has ended!");
		}
	}

	/**
	 * Action handling the situation when one player is broke to another player. All
	 * money and properties are the other player.
	 * 
	 * @param brokePlayer
	 *            the broke player
	 * @param benificiary
	 *            the player who receives the money and assets
	 */
	public void playerBrokeTo(Player brokePlayer, Player benificiary) {
		int amount = brokePlayer.getBalance();
		benificiary.receiveMoney(amount);
		brokePlayer.setBalance(0);
		brokePlayer.setBroke(true);

		// We assume here, that the broke player has already sold all his houses! But,
		// if
		// not, we could make sure at this point that all houses are removed from
		// properties (properties with houses on are not supposed to be transferred,
		// neither
		// in a trade between players, nor when player goes broke to another player)
		for (Property property : brokePlayer.getOwnedProperties()) {
			property.setOwner(benificiary);
			benificiary.addOwnedProperty(property);
		}
		brokePlayer.removeAllProperties();

		while (!brokePlayer.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(brokePlayer.getOwnedCards().get(0));
		}

		gui.showMessage("Player " + brokePlayer.getName() + "went broke and transfered all" + "assets to "
				+ benificiary.getName());
	}

	/**
	 * Action handling the situation when a player is broke to the bank.
	 * 
	 * @param player
	 *            the broke player
	 */
	public void playerBrokeToBank(Player player) {

		player.setBalance(0);
		player.setBroke(true);

		for (Property property : player.getOwnedProperties()) {
			property.setMortgaged(false);
			if (property instanceof RealEstate) {
				RealEstate realtemp = (RealEstate) property;
				realtemp.removeAllHouses();
			}
		}

		for (Property property : player.getOwnedProperties()) {
			property.setOwner(null);
		}
		player.removeAllProperties();

		gui.showMessage("Player " + player.getName() + " went broke");

		while (!player.getOwnedCards().isEmpty()) {
			game.returnCardToDeck(player.getOwnedCards().get(0));
		}
	}

	/**
	 * @author emil_ Method used to create a list with categories and mapping them
	 *         with strings.
	 */

	private void createcategoryList() {
		for (Space space : game.getSpaces()) {
			if (space instanceof Property) {
				Property property = (Property) space;
				List<Property> list = category2Properties.get(property.getCategory());
				if (list == null) {
					list = new ArrayList<Property>();
					category2Properties.put(property.getCategory(), list);
				}
				list.add(property);
			}
		}
	}

	/**
	 * @author emil_
	 * @param A
	 *            string, determining the category
	 * @return A list of all properties in the category.
	 */
	public List<Property> getAllPropertiesofCategory(String category) {
		return new ArrayList<Property>(category2Properties.get(category));

	}

	// method to set and get diethrow, used in order to calculate brewery rent
	// @emil_
	private void setDieThrow(int i, int j) {
		this.diethrow = i + j;
	}

	public int getDieThrow() {
		return diethrow;
	}

	public List<Space> getSpaces() {

		return game.getSpaces();
	}

	public List<Player> getListOfPlayers() {
		return game.getPlayers();
	}

	/**
	 * @author emil_ Mortgage offer
	 */
	private void offerToMortgageProperty(Player player) {
		if (player.getOwnedProperties().isEmpty()) {
			gui.showMessage("You do not own any properties");
		} else if (!player.getOwnedProperties().isEmpty()) {
			boolean running = true;
			while (running) {
				String select = gui.getUserButtonPressed("What do you want to do?", "Mortgage Property",
						"Unmortgage Property", "Return");
				switch (select) {
				case "Mortgage Property":

					Property[] k = new Property[player.getOwnedProperties().size()];
					player.getOwnedProperties().toArray(k);
					List<Property> l = new ArrayList<Property>();
					for (int j = 0; j < player.getOwnedProperties().size(); j++) {
						l.add(k[j]);
					}

					Property choosenp = this.chooseProperty(player, l);

					if (this.sellHousesInPropertyGroupToAllowMortgage(player, choosenp)
							&& choosenp.isMortaged() == false) {
						this.paymentFromBank(player, choosenp.getCost() / 2);
						choosenp.setMortgaged(true);
						choosenp.setActualRent();
						gui.showMessage("You have mortgaged: " + choosenp.getName());
						//view.update(choosenp);
					} else if (choosenp.isMortaged() == true) {
						gui.showMessage("That property is already mortgaged");
					}

					break;

				case "Unmortgage Property":
					Property[] k1 = new Property[player.getOwnedProperties().size()];
					player.getOwnedProperties().toArray(k1);
					List<Property> l1 = new ArrayList<Property>();
					for (int j = 0; j < player.getOwnedProperties().size(); j++) {
						l1.add(k1[j]);
					}

					Property choosenp1 = this.chooseProperty(player, l1);
					if (choosenp1.isMortaged() == true) {
						try {
							this.paymentToBank(player, choosenp1.getCost() / 2);
						} catch (PlayerBrokeException e) {

							e.printStackTrace();
						}
						choosenp1.setMortgaged(false);
						choosenp1.setActualRent();
						//view.update(choosenp1);
						gui.showMessage("You have unmortgaged: " + choosenp1.getName());
					} else if (!choosenp1.isMortaged() == true) {
						gui.showMessage(choosenp1.getName() + " is not mortgaged.");
					}

					break;
				case "Return":
					running = false;
					break;
				default:
					running = false;
					break;
				}
			}
		}
	}

	/**
	 * @author emil_ A method that sells all houses on a property group to allow
	 *         mortage;
	 * @return returns true when all houses in a property group is sold, thereby
	 *         allowing mortgage.
	 */

	private boolean sellHousesInPropertyGroupToAllowMortgage(Player player, Property property) {
		String s = property.getCategory();
		boolean running = true;
		while (running) {
			int houseamount = 0;
			List<Property> nyliste = this.getAllPropertiesofCategory(s);
			nyliste.retainAll(player.getOwnedProperties());
			RealEstate[] realestatearray = new RealEstate[this.getAllPropertiesofCategory(s).size()];

			for (Property r : nyliste) {
				if (r instanceof RealEstate) {
					int i = 0;
					realestatearray[i] = (RealEstate) r;
					houseamount += ((RealEstate) r).getHouses();
				}

			}
			if (houseamount != 0) {
				gui.showMessage("You must sell all houses in the realestate group first!");
				Property p = this.chooseProperty(player, nyliste);
				this.sellHouses(player, (RealEstate) p);

			} else if (houseamount == 0) {
				running = false;
			}
		}
		return true;

	}

	/**
	 * If a player owns all RealEstate in a category the player is offered to buy
	 * houses at the end of the round.
	 * 
	 * @author emil_
	 * @param player
	 */
	private void offerToBuyHouse(Player player) {

		if (!this.getPlayerOwnedRealEstateOfCategories(player).isEmpty()) {
			boolean running = true;
			while (running) {
				String select = gui.getUserButtonPressed("Do you want to buy houses?", "yes", "no");
				if (select.equals("yes")) {
					List<Property> l = this.getPlayerOwnedRealEstateOfCategories(player);
					Property realestate = this.chooseProperty(player, l);
					this.buyHouse(player, (RealEstate) realestate);
				} else if (select.equals("no")) {
					running = false;
				}

			}
		} else if (this.getPlayerOwnedRealEstateOfCategories(player).isEmpty()) {
			gui.showMessage("You do not own any property groups");
		}

	}

	private void offerToSellHouses(Player player) {

		if (!this.getPlayerOwnedRealEstateOfCategories(player).isEmpty()) {
			boolean running = true;
			while (running) {
				String select = gui.getUserButtonPressed("Do you want to sell houses?", "yes", "no");
				if (select.equals("yes")) {
					List<Property> l = this.getPlayerOwnedRealEstateOfCategories(player);
					Property realestate = this.chooseProperty(player, l);
					this.sellHouses(player, (RealEstate) realestate);
				} else if (select.equals("no")) {
					running = false;
				}

			}
		} else if (this.getPlayerOwnedRealEstateOfCategories(player).isEmpty()) {
			gui.showMessage("You do not own any property groups");
		}

	}

	/**
	 * Method used to offer a player the oppertunity to buy houses on owned
	 * properties. It should have been possible to buy a hotel after buying 4
	 * houses, but for the sake of simplicity we only "work" in houses.
	 * 
	 * @author rasmus_
	 * @param player
	 * @param realestate
	 */
	private void buyHouse(Player player, RealEstate realestate) {

		String choice = gui.getUserButtonPressed(
				"Player " + player.getName() + ": Do you want to buy houses? at " + realestate.getName(), "yes", "no");
		if (choice.equals("yes")) {

			if (realestate.getHouses() >= 5) { // 5 is the maximum number of houses - hotels is not an option at
												// this
												// point.
				gui.showMessage("You can't buy anymore houses.");
				return;
			} else {
				int minimum = 1;
				int maximum = 5 - realestate.getHouses();
				int amount = gui.getUserInteger(
						"How many houses du you want to buy? The price for one house is: " + realestate.getHouseCost(),
						minimum, maximum);

				realestate.addHouses(amount);
				realestate.setActualRent();

				try {
					this.paymentToBank(player, realestate.getHouseCost() * amount);
					sql.updateHouses(realestate, amount);
					vdb.updPropertyView(realestate, player);
				} catch (PlayerBrokeException e) {
					e.printStackTrace();
				}
				//view.update(realestate);
			}
		}
	}

	/**
	 * @author emil_
	 * @param player
	 * @param realestate
	 *            Method used in order to sell houses.
	 */

	private void sellHouses(Player player, RealEstate realestate) {

		if (realestate.getHouses() != 0) {

			int minimum = 1;
			int maximum = realestate.getHouses();
			int amount = gui.getUserInteger("How many houses du you want to sell? You are paid: "
					+ (realestate.getHouseCost() / 2) + "pr. house.", minimum, maximum);
			int amount2 = maximum - amount;

			realestate.removeHouses(amount);
			realestate.setActualRent();

			this.paymentFromBank(player, (realestate.getHouseCost() / 2) * amount);

			sql.updateHouses(realestate, amount2);
			vdb.updPropertyView(realestate, player);
			//view.update(realestate);

		}

	}

	/**
	 * @author emil_ Method giving the player the oppertunity to trade.
	 */
	private void offerToTrade(Player player) {
		if (player.getOwnedProperties().isEmpty()) {
			gui.showMessage("You do not own any properties");
		} else if (!player.getOwnedProperties().isEmpty()) {

			boolean running = true;
			while (running) {
				String selection = gui.getUserButtonPressed("Player "+player.getName()+", what do you want to trade in?", "Property", "Pardoncard",
						"End Trading");
				switch (selection) {
				case "Property": {
					if (player.getOwnedProperties().isEmpty()) {
						gui.showMessage("You do not own any properties");
						return;
					}
					Property[] k = new Property[player.getOwnedProperties().size()];
					player.getOwnedProperties().toArray(k);
					List<Property> l = new ArrayList<Property>();
					for (int j = 0; j < player.getOwnedProperties().size(); j++) {
						l.add(k[j]);
					}

					Property chosenproperty = this.chooseProperty(player, l);
					int chosenamount = gui.getUserInteger("How much do you want for your property?");
					String sel= gui.getUserButtonPressed("Are you sure you want to trade " +chosenproperty.getName()+"?", "yes","no");
					if(sel.equals("yes")) {
						this.tradeChoosenProperty(player, chosenproperty, chosenamount);	
					}
		

				}
					break;
				case "Pardoncard": {
					if (player.getOwnedCards().isEmpty()) {
						gui.showMessage("You do not own a pardoncard");
						return;
					} else {
						Card[] cards = new Card[player.getOwnedCards().size()];
						player.getOwnedCards().toArray(cards);
						int chosenamount = gui.getUserInteger("How much do you want for your pardoncard?");
						this.tradePardonCard(player, cards[0], chosenamount);

					}

				}
				case "End Trading": {
					running = false;
				}
					break;
				default: {
					running = false;
				}
				}

			}
		}
	}

	private void tradeChoosenProperty(Player player, Property property, int chosenamount) {
		gui.showMessage(property.getName() + " is up for trade, and the price will start at " + chosenamount);
		List<Player> tradeGrantedPlayers = new ArrayList<Player>(game.getPlayers());
		List<Player> notGrantedPlayers = new ArrayList<Player>();
		notGrantedPlayers.add(game.getCurrentPlayer());
		int bid = 0;
		Player winner = null;

		for (Player biddingPlayer : tradeGrantedPlayers) {
			if (notGrantedPlayers.contains(biddingPlayer) || biddingPlayer.isBroke()) {
				continue;
			} else {
				int minimum = chosenamount > bid + 1 ? chosenamount : bid + 1; // makes sure to always use the most
																				// recent bid

				String answer = gui.getUserButtonPressed(
						"Would " + biddingPlayer.getName() + " like to bid? - the price is " + minimum, "Yes", "No");
				if (answer.equals("Yes")) {
					bid = gui.getUserInteger("Place your bid", minimum, 1000000000); // maximum bid of 1.000.000.000
					winner = biddingPlayer;
				} else {
					notGrantedPlayers.add(biddingPlayer);
				}
			}
		}

		if (winner != null) {
			gui.showMessage(winner.getName() + " Bought the property");
			try {
				payment(winner, bid, player);
				player.removeOwnedProperty(property);
				winner.addOwnedProperty(property);
				gui.showMessage("You've succesfully bought the property " + property.getName());
				property.setOwner(winner);
				this.updateOwnedCategories(winner);
				property.setActualRent();
				// vdb.updPropertyView(property, winner);
				// sql.addPropertyToPlayer(property, winner);

			} catch (PlayerBrokeException e) {
				gui.showMessage(
						"You haven't bought the property " + property.getName() + " You didn't have enough money");
				e.printStackTrace();
			}
		} else {
			gui.showMessage("Nobody wanted to buy the property!");
		}
	}

	/**
	 * 
	 * @param player
	 * @param card
	 * @param chosenamount
	 */
	
	private void tradePardonCard(Player player, Card card, int chosenamount) {
		gui.showMessage("A pardoncard is up for trade, and the price will start at " + chosenamount);
		List<Player> tradeGrantedPlayers = new ArrayList<Player>(game.getPlayers());
		List<Player> notGrantedPlayers = new ArrayList<Player>();
		notGrantedPlayers.add(game.getCurrentPlayer());
		int bid = 0;
		Player winner = null;

		for (Player biddingPlayer : tradeGrantedPlayers) {
			if (notGrantedPlayers.contains(biddingPlayer) || biddingPlayer.isBroke()) {
				continue;
			} else {
				int minimum = chosenamount > bid + 1 ? chosenamount : bid + 1; // makes sure to always use the most
																				// recent bid

				String answer = gui.getUserButtonPressed(
						"Would " + biddingPlayer.getName() + " like to bid? - the price is " + minimum, "Yes", "No");
				if (answer.equals("Yes")) {
					bid = gui.getUserInteger("Place your bid", minimum, 1000000000); // maximum bid of 1.000.000.000
					winner = biddingPlayer;
				} else {
					notGrantedPlayers.add(biddingPlayer);
				}
			}
		}

		if (winner != null) {
			gui.showMessage(winner.getName() + " Bought the pardoncard");
			try {
				payment(winner, bid, player);
				player.removeOwnedCard();
				winner.setOwnedCard(card);
				gui.showMessage("You've succesfully bought the pardoncard");

			} catch (PlayerBrokeException e) {
				gui.showMessage("You haven't bought the pardoncard, you didn't have enough money");
				e.printStackTrace();
			}
		} else {
			gui.showMessage("Nobody wanted to buy the pardoncard!");
		}
	}

	/**
	 * private method used in order to choose a piece of Property, used in a
	 * trade/sellinghouses/buyinghouses/mortage. method.
	 * 
	 * @author emil_
	 * @param player
	 * @return
	 */

	private Property chooseProperty(Player player, List<Property> realestatelist) {
		Collection<String> kategorier = new HashSet<String>();
		for (Property r : realestatelist) {
			kategorier.add(r.getCategory());
		}
		String[] choose = new String[kategorier.size()];
		kategorier.toArray(choose);
		String chosencategory = gui.getUserButtonPressed("Hvilket område?", choose);

		List<Property> nyliste = this.getAllPropertiesofCategory(chosencategory);
		nyliste.retainAll(realestatelist);
		Property[] ejendomme = new Property[nyliste.size()];
		String[] options = new String[nyliste.size()];

		int j = 0;
		for (Property prop : nyliste) {
			ejendomme[j] = prop;
			options[j] = prop.getName();
			j++;
		}

		String chosenproperty = gui.getUserButtonPressed("Hvilken Grund?", options);
		Property prop = null;
		for (int n = 0; n < options.length; n++) {
			if (chosenproperty.equals(ejendomme[n].getName())) {

				prop = ejendomme[n];
			}
		}
		return prop;

	}

	/**
	 * @author emil_
	 * @param player
	 * @return List<RealEstate> This method returns a list of realestate, provided a
	 *         player owns all the realestate of a category. This is used in
	 *         conjugation with the offer to buy houses.
	 */

	private List<Property> getPlayerOwnedRealEstateOfCategories(Player player) {
		Property[] k = new Property[player.getOwnedProperties().size()];
		player.getOwnedProperties().toArray(k);
		List<Property> l = new ArrayList<Property>();

		for (int j = 0; j < player.getOwnedProperties().size(); j++) {
			if (this.checkOwnershipOfCategory(player, k[j]) && k[j] instanceof RealEstate) {

				l.add((RealEstate) k[j]);
			}

		}

		return l;

	}

	/**
	 * @author emil_
	 * @param player
	 * @param Property
	 * @return returns the amount owned of a certain category group as an int. This
	 *         method is used for rent calculations for ships and breweries.
	 */
	public int checkOwnershipAmount(Player player, Property p) {
		List<Property> prop = this.getAllPropertiesofCategory(p.getCategory());
		int i = 0;
		for (Property property : prop) {
			if (player.equals(property.getOwner())) {
				i++;
			}
		}
		return i;
	}

	/**
	 * @author emil_
	 * @param player
	 * @param realestate
	 * @return boolean Method used in order to check if a player owns a category of
	 *         realestate, used to double rent on owned realestate group.
	 */

	public boolean checkOwnershipOfCategory(Player player, Property realestate) {
		List<Property> l = this.getAllPropertiesofCategory(realestate.getCategory());
		for (Property property : l) {
			if (!player.equals(property.getOwner())) {

				return false;
			}
		}
		return true;
	}
/**
 * 
 *@author emil_
 * @param player
 * Method to update the categories a player own. 
 */
	
	
	private void updateOwnedCategories(Player player) {

		for (Property p : player.getOwnedProperties()) {
			if(this.checkOwnershipOfCategory(player, p)) {
				player.addOwnedPropertyCategories(p.getCategory());
			}
		
		}

	}
	private void updateBoard() {
		
		for(Space space:game.getSpaces()) {
			if(space instanceof Property) {
				for(Player player:game.getPlayers()){
					if(player.equals(((Property) space).getOwner())) {
						((Property) space).setActualRent();;
					}
				}
				
			}
		
		}
		
	}

	/**
	 * Method for disposing of this controller and cleaning up its resources.
	 */
	public void dispose() {
		if (!disposed) {
			disposed = true;
			view.dispose();
			// we should also dispose of the GUI here. But this works only
			// for my private version on the GUI and not for the GUI currently
			// deployed via Maven (or other official versions)
		}
	}

}
