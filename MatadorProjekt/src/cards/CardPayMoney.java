package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

/**
 * The Class CardPayMoney.
 * 
 * @author emil_
 */
public class CardPayMoney extends Card {
	
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
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			controller.paymentToBank(player, amount);
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
			
		}
	}

}
