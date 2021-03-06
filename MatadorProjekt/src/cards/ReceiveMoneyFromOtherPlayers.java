package cards;
/**
 * @author emil_
 */

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

/**
 * The Class ReceiveMoneyFromOtherPlayers.
 * @author emil_
 */
public class ReceiveMoneyFromOtherPlayers extends Card {

	private int amount;
	
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
	
	public void doAction(GameController controller, Player player) throws PlayerBrokeException 
	{
		try {
			for(Player p:controller.getListOfPlayers()) {
				if(!p.equals(player)) {
					controller.payment(p, amount, player);
				}
				
			}
			
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}	
	}
}
