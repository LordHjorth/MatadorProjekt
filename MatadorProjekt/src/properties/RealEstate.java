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
	private int rent;
	private boolean hotel;

	/**
	 * Gets the houses.
	 * @author emil_, Simone, Monica
	 * @return the houses
	 */
	public int getHouses() {
		return houses;
	}

	/**
	 * @param houses,
	 *            the houses to add
	 *   @author emil_, Simone, Monica
	 */
	public void addHouses(int houses) {
		this.houses += houses;
		notifyChange();
	}

	/**
	 * @param houses,
	 *            the houses to remove
	 *  @author emil_, Simone, Monica
	 */
	public void removeHouses(int houses) {
		this.houses -= houses;
		notifyChange();
	}

	/**
	 * @author emil_, Simone, Monica
	 * Method to remove all Houses on RealEstate.
	 */
	public void removeAllHouses() {
		this.houses = 0;
		notifyChange();
	}

	/**
	 * @author emil_, Simone, Monica
	 * @return the houseCost
	 */
	public int getHouseCost() {
		return this.cost/10;
	}
	
	/**
	 * Gets the hotel cost.
	 *@author emil_, Simone, Monica
	 * @return the hotel cost
	 */
	public int getHotelCost(){
		return this.cost/2;
	}
	
	/**
	 * @author emil_, Simone, Monica
	 * Adds the hotel.
	 */
	public void addHotel() {
		this.hotel=true;
		
	}
	
	/**
	 * @author emil_, Simone, Monica
	 * Removes the hotel.
	 */
	public void removeHotel() {
		this.hotel=false;
	}
	
	/**
	 * Checks for hotel.
	 *@author emil_, Simone, Monica
	 * @return true, if successful
	 */
	public boolean hasHotel() {
		return this.hotel;
	}
	@Override
	public void setRent(int rent) {
		this.rent = rent;
		notifyChange();
	}

	@Override
	public void setActualRent() {
		this.actualrent=this.rent;
		if (owner.getOwnedPropertyCategories().contains(this.getCategory()) && !this.isMortgaged() && (this.getHouses()==0)&&hotel) {
			this.actualrent=this.rent * 10;
		}
		if (owner.getOwnedPropertyCategories().contains(this.getCategory()) && !this.isMortgaged() && (this.getHouses()==0)&&!hotel) {
			this.actualrent=this.rent * 2;
		}
		if (this.isMortgaged()||owner.isInPrison()) {
			this.actualrent=0;
		}
		if (owner.getOwnedPropertyCategories().contains(this.getCategory()) && !this.isMortgaged() && (this.getHouses() > 0)) {

		 this.actualrent= this.rent +(this.rent*this.getHouses()) ;
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