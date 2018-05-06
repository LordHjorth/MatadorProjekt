package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

/**
 * The Class Legat.
 * 
 * @author Simone
 */
public class Legat extends Card {

	/** The amount. */
	private int amount;
	
	/** The value. */
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
	
	/**
	 * Sets the value limit.
	 *
	 * @param value the new value limit
	 */
	public void setValueLimit(int value) {
		this.value = value;
	}

	/* (non-Javadoc)
	 * @see gameContent.Card#doAction(controllers.GameController, gameContent.Player)
	 */
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
