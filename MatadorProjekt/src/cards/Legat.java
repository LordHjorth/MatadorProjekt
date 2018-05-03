package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class Legat extends Card {

	private int amount;
	private int value;

	/**
	 * Returns the amount this card directs the bank to pay to the player.
	 *  
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * Sets the amount this card directs the bank to pay to the player.
	 * 
	 * @param amount the new amount
	 */
	public void setPaymentAmount(int amount) {
		this.amount = amount;
	}
	public void setValueLimit(int value) {
		this.value = value;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			
			
			if(player.getPlayerValue()<=value) {
			controller.paymentFromBank(player, amount);
			}
			
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
		

}
