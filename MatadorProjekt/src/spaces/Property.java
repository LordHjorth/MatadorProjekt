package spaces;

import gameContent.Player;
import gameContent.Space;

/**
 * A property which is a space that can be owned by a player.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Property extends Space {

	protected int cost;
	protected int rent;
	protected String category;
	public final String[] categories = { "Ships", "Brewery", "Vestegnen", "Valby", "Frederiksberg", "Hellerup",
			"Østerbro", "Kongens Nytorv", "Indre By", "København K" };
	protected Player owner = null;
	protected boolean mortgaged = false;
	protected int actualrent;

	/**
	 * Checks if is mortgaged.
	 * @author emil_, Nicolas
	 * @return true, if is mortgaged
	 */
	public boolean isMortgaged() {
		return mortgaged;
	}

	/**
	 * Sets the mortgaged.
	 *@author emil_, Nicolas
	 * @param mortgaged the new mortgaged
	 */
	public void setMortgaged(boolean mortgaged) {
		this.mortgaged = mortgaged;
		notifyChange();
	}

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
	 * @param cost
	 *            the new cost of this property
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
	 * @param rent
	 *            the new rent for this property
	 */
	public void setRent(int rent) {
		this.rent = rent;
		notifyChange();
	}

	/**
	 * Returns the owner of this property. The value is <code>null</code>, if the
	 * property currently does not have an owner.
	 * 
	 * @return the owner of the property or <code>null</code>
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * Checks for owner.
	 * @author emil_
	 * @return true, if successful
	 */
	public boolean hasOwner() {
		if (owner == null) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Sets the owner of this property to the given owner (which can be
	 * <code>null</code>).
	 * 
	 * @param player
	 *            the new owner of the property
	 */
	public void setOwner(Player player) {
		this.owner = player;
		notifyChange();
	}

	/**
	 * @author emil_
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * @author emil_
	 * @param category
	 *            the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * Gets the categories.
	 *@author emil_
	 * @return the categories
	 */
	public String[] getCategories() {
		return categories;

	}

	/**
	 * Sets the actual rent
	 * @author emil_, Simone.
	 */
	public void setActualRent() {
		this.actualrent=this.rent;
		if (owner.getOwnedPropertyCategories().contains(this.getCategory()) && !this.isMortgaged()) {
			this.actualrent=this.rent * 2;
			
		}
		if (this.isMortgaged()||owner.isInPrison()) {
			this.actualrent=0;
			
		}
		notifyChange();	
	}
		
		
	
	
	/**
	 * Gets the actual rent.
	 *@author emil_, Simone
	 * @return the actual rent
	 */
	public int getActualRent() {
		return this.actualrent;
	}
}
