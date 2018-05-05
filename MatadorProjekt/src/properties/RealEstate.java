package properties;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import spaces.Property;

/**
 * A specific property, which represents real estate on which houses and hotels
 * can be built. Note that has not details yet and needs to be implemented.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class RealEstate extends Property {
	
	private int houses = 0;
	private int houseCost;
	private int rent;

	public int getHouses() {
		return houses;
	}

	/**
	 * @param houses,
	 *            the houses to add
	 */
	public void addHouses(int houses) {
		this.houses += houses;
		notifyChange();
	}

	/**
	 * @param houses,
	 *            the houses to remove
	 */
	public void removeHouses(int houses) {
		this.houses -= houses;
		notifyChange();
	}

	/**
	 * Method to remove all Houses on RealEstate.
	 */
	public void removeAllHouses() {
		this.houses = 0;
		notifyChange();
	}

	/**
	 * @return the houseCost
	 */
	public int getHouseCost() {
		return houseCost;
	}

	public void setHouseCost() {
		this.houseCost = this.cost / 10;

	}

	@Override
	public void setRent(int rent) {

		this.rent = rent;

		notifyChange();
	}

	
	
	@Override
	public void setActualRent() {
		if (owner.getOwnedPropertyCategories().contains(this.getCategory()) && !this.isMortaged() && !(this.getHouses() > 0)) {
			this.actualrent=this.rent * 2;
		}
		if (this.isMortaged()) {
			this.actualrent=0;
		}
		if (owner.getOwnedPropertyCategories().contains(this.getCategory()) && !this.isMortaged() && (this.getHouses() > 0)) {

		 this.actualrent= this.rent +(this.rent*this.getHouses()) ;
		} else {
			this.actualrent=this.rent;
		}
		
		notifyChange();	
	}
	
	
	
	
	
	
	
	
	


	@Override
	public int getRent() {
		return rent;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		if (this.getOwner() == null) {
			controller.offerToBuy(this, player);
			

		}

		else if (!this.getOwner().equals(player)) {
			controller.payment(player, this.getActualRent(), this.getOwner());
		}

	}
}