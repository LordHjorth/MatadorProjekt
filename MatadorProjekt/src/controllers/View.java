package controllers;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import designPatterns.Observer;
import designPatterns.Subject;
import gameContent.Chance;
import gameContent.Game;
import gameContent.Jail;
import gameContent.Parking;
import gameContent.Player;
import gameContent.Property;
import gameContent.Space;
import gameContent.Start;
import gameContent.Tax;
import gui_fields.GUI_Brewery;
import gui_fields.GUI_Car;
import gui_fields.GUI_Car.Pattern;
import gui_fields.GUI_Car.Type;
import gui_fields.GUI_Chance;
import gui_fields.GUI_Field;
import gui_fields.GUI_Jail;
import gui_fields.GUI_Player;
import gui_fields.GUI_Refuge;
import gui_fields.GUI_Shipping;
import gui_fields.GUI_Start;
import gui_fields.GUI_Street;
import gui_fields.GUI_Tax;
import gui_main.GUI;
import properties.Brewery;
import properties.RealEstate;
import properties.Shipping;

/**
 * This class implements a view on the Monopoly game based on the original
 * Matador GUI; it serves as a kind of adapter to the Matador GUI. This class
 * realizes the MVC-principle on top of the Matador GUI. In particular, the view
 * implements an observer of the model in the sense of the MVC-principle, which
 * updates the GUI when the state of the game (model) changes.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class View implements Observer {

	private Game game;
	private GUI gui;

	private Map<Player, GUI_Player> player2GuiPlayer = new HashMap<Player, GUI_Player>();
	private Map<Player, Integer> player2position = new HashMap<Player, Integer>();
	private Map<Space, GUI_Field> space2GuiField = new HashMap<Space, GUI_Field>();

	GUI_Field[] fields;

	private boolean disposed = false;

	/**
	 * Constructor for the view of a game based on a game and an already running
	 * Matador GUI.
	 * 
	 * @param game
	 *            the game
	 * @param gui
	 *            the GUI
	 */
	public View(Game game) {
		this.game = game;
		

	}

	public GUI createGUI() {
		if (gui == null) {
			fields = new GUI_Field[game.getSpaces().size()];
			int i = 0;
			for (Space space : game.getSpaces()) {

				if (space instanceof RealEstate) {
					String cost = "Cost: " + ((RealEstate) space).getCost() + "";
					String rent = "Rent: " + ((RealEstate) space).getRent() + "";
					fields[i] = new GUI_Street();
					fields[i].setTitle(space.getName());
					fields[i].setSubText(cost);
					fields[i].setDescription(rent);
					fields[i].setBackGroundColor(space.getColor());

				} else if (space instanceof Brewery) {
					String cost = "Cost: " + ((Brewery) space).getCost() + "";
					String rent = "Rent: " + ((Brewery) space).getRent() + "";
					fields[i] = new GUI_Brewery();
					fields[i].setTitle(space.getName());
					fields[i].setSubText(cost);
					fields[i].setDescription(rent);
					fields[i].setBackGroundColor(space.getColor());

				} else if (space instanceof Chance) {
					fields[i] = new GUI_Chance(((Chance) space).getIcon(),space.getName(),"",Color.BLACK,Color.WHITE);

				} else if (space instanceof Shipping) {
					String cost = "Cost: " + ((Shipping) space).getCost() + "";
					String rent = "Rent: " + ((Shipping) space).getRent() + "";
					fields[i] = new GUI_Shipping();
					fields[i].setTitle(space.getName());
					fields[i].setSubText(cost);
					fields[i].setDescription(rent);
					fields[i].setBackGroundColor(space.getColor());

				} else if (space instanceof Start) {
					fields[i] = new GUI_Start(space.getName(), space.getName(), "", Color.RED,Color.black);
					
				} else if (space instanceof Tax) {
					fields[i] = new GUI_Tax();
					fields[i].setTitle(space.getName());
					fields[i].setDescription("");
					fields[i].setSubText("");
					
				} else if (space instanceof Jail) {
					fields[i] = new GUI_Jail();
					fields[i].setTitle(space.getName());
					fields[i].setSubText(space.getName());
					fields[i].setDescription(space.getName());

				} else if (space instanceof Parking) {
					fields[i] = new GUI_Refuge();
					fields[i].setTitle(space.getName());
					fields[i].setSubText(space.getName());
					fields[i].setDescription("");
				} else {
					// TODO
				}
				space2GuiField.put(space, fields[i++]);
			}
			gui = new GUI(fields);

		}
		return gui;
	}

	public void createPlayers() {
		for (Player player : game.getPlayers()) {
			GUI_Car car = new GUI_Car(player.getColor(), player.getColor(), Type.CAR, Pattern.FILL);
			GUI_Player guiPlayer = new GUI_Player(player.getName(), player.getBalance(), car);
			gui.addPlayer(guiPlayer);
			player2GuiPlayer.put(player, guiPlayer);
			updatePlayer(player);
			player.attach(this);

		}
	}

	@Override
	public void update(Subject subject) {
		if (!disposed) {
			if (subject instanceof Player) {
				updatePlayer((Player) subject);
			}
			if(subject instanceof Property) {
				fields[((Property) subject).getIndex()].setDescription(((Property) subject).getRent() + "");;
			}

			// TODO update other subjects in the GUI
			// in particular properties (sold, houses, ...)

		}
	}

	/**
	 * This method updates a player's state in the GUI. Right now, this concerns the
	 * players position and balance only. But, this should also include other
	 * information (being i prison, available cards, ...)
	 * 
	 * @param player
	 *            the player who's state is to be updated
	 */
	private void updatePlayer(Player player) {
		GUI_Player guiPlayer = this.player2GuiPlayer.get(player);
		if (guiPlayer != null) {
			guiPlayer.setBalance(player.getBalance());

			GUI_Field[] guiFields = gui.getFields();
			Integer oldPosition = player2position.get(player);
			if (oldPosition != null && oldPosition < guiFields.length) {
				guiFields[oldPosition].setCar(guiPlayer, false);
			}
			int pos = player.getCurrentPosition().getIndex();
			if (pos < guiFields.length) {
				player2position.put(player, pos);
				guiFields[pos].setCar(guiPlayer, true);
			}

			String label = null;
			if (player.isBroke()) {
				label = player.getName() + " (broke)";
			} else if (player.isInPrison()) {
				label = player.getName() + " (in prison)";
			} else {
				label = player.getName();
			}
			if (!guiPlayer.getName().equals(label)) {
				guiPlayer.setName(label);
			}
		}
	}
	
	public void setBorderColor(Player player,Property property){
		int i= property.getIndex();
		GUI_Field[] field=this.gui.getFields();
		field[i].setForeGroundColor(player.getColor());
		updatePlayer(player);
	}

	void dispose() {
		if (!disposed) {
			disposed = true;
			for (Player player : game.getPlayers()) {
				// unregister from the player as observer
				player.detach(this);
			}
		}
	}

}
