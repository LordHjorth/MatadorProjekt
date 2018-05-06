package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;
import properties.RealEstate;
import spaces.Property;

/**
 * The Class PayPropertyTax.
 * @author Emil_
 */
public class PayPropertyTax extends Card {
	private int houseamount=0;
	private int amount;
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		houseamount =0;
		for(Property p:player.getOwnedProperties()) {
			if(p instanceof RealEstate) {
				houseamount+=((RealEstate) p).getHouses();
				
			}
			
		}
		
		
		try {
			controller.paymentToBank(player, amount*houseamount) ;
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}


	/**
	 * Gets the amount.
	 *
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}


	/**
	 * Sets the amount.
	 *
	 * @param amount the new amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
}
