package properties;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import gameContent.Property;

/**
 * A specific property, which represents real estate on which houses and hotels
 * can be built. Note that has not details yet and needs to be implemented.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class RealEstate extends Property {
	int cost = super.getCost();
	private int houses = 0;
	private int houseCost = cost / 10;
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
	 * @return the houseCost
	 */
	public int getHouseCost() {
		return houseCost;
	}

	@Override
	public void setRent(int rent) {

	}

	public void updateRent(int houses) {
		if (houses == 0) {
			this.rent = this.getRent();
		} else {
			this.rent = rent * houses;
		}
		notifyChange();

	}

	public int getRent() {
		return rent;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		if (this.getOwner() == null) {
			controller.offerToBuy(this, player);
		}

		else if (!this.getOwner().equals(player)) {
			// TODO also check whether the property is mortgaged
			// TODO the computation of the actual rent could be delegated
			// the subclasses of Property, which can take the specific
			// individual conditions into account. Note that the
			// groups of properties (which are not part of the model
			// yet also need to be taken into account).
			controller.payment(player, this.getRent(), this.getOwner());
		}
		
		controller.buyHouseIfPossible(player, this);
	}

}