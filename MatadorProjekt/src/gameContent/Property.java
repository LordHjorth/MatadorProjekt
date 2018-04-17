package gameContent;

import controllers.GameController;
import exceptions.PlayerBrokeException;

/**
 * A property which is a space that can be owned by a player.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Property extends Space {
	
	//TODO - add category for fields, so houses/hotels can be added.
	private int cost;
	private int rent;
	private String category;
	private int houses = 0;
	private int houseCost = cost / 10;
	public final String[] categories = {"Ships", "Brewery", "Vestegnen", "Valby", "Frederiksberg", "Hellerup", "Østerbro", "Kongens Nytorv", "Indre By", "København K"};

	
	private Player owner = null;
	

	/**
	 * Returns the cost of this property.
	 * 
	 * @return the cost of this property
	 */
	public int getCost() {
		return cost;
	}

	/**
	 * Sets the cost of this property.
	 * 
	 * @param cost the new cost of this property
	 */
	public void setCost(int cost) {
		this.cost = cost;
		notifyChange();
	}

	/**
	 * Returns the rent to be payed for this property.
	 * 
	 * @return the rent for this property
	 */
	public int getRent() {
		return rent;
	}

	/**
	 * Sets the rent for this property.
	 * 
	 * @param rent the new rent for this property
	 */
	public void setRent(int rent) {
		this.rent = rent + rent * houses;
		notifyChange();
	}

	/**
	 * Returns the owner of this property. The value is <code>null</code>,
	 * if the property currently does not have an owner.
	 * 
	 * @return the owner of the property or <code>null</code>
	 */
	public Player getOwner() {
		return owner;
	}
	
	public boolean hasOwner() {
		if(owner == null) {
			return false;
		}
		else {
			return true;
		}
	}

	/**
	 * Sets the owner of this property  to the given owner (which can be 
	 * <code>null</code>).
	 * 
	 * @param player the new owner of the property
	 */
	public void setOwner(Player player) {
		this.owner = player;
		notifyChange();
	}

	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the houses
	 */
	public int getHouses() {
		return houses;
	}

	/**
	 * @param houses, the houses to add
	 */
	public void addHouses(int houses) {
		this.houses += houses;
	}
	
	/**
	 * @param houses, the houses to remove
	 */
	public void removeHouses(int houses) {
		this.houses -= houses;
	}

	/**
	 * @return the houseCost
	 */
	public int getHouseCost() {
		return houseCost;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		if (owner == null) {
			controller.offerToBuy(this, player);
		} 
		else if (owner.equals(player)) {
			controller.buyHouse(player, this);
		}
		else if (!owner.equals(player)) {
			// TODO also check whether the property is mortgaged
			// TODO the computation of the actual rent could be delegated
			//      the subclasses of Property, which can take the specific
			//      individual conditions into account. Note that the
			//      groups of properties (which are not part of the model
			//      yet also need to be taken into account).
			controller.payment(player, rent, owner);
		}
	}

}
