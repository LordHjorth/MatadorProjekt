package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

/**
 * A card that directs the player to pay tax to the bank. The tax amounts
 * 10% of the balance of the player's account.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class PayTax extends Card {

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		// TODO note that tax concerns all assets an not just cash
		//      this is just a simple  way of implementing tax
		try {
			controller.paymentToBank(player, player.getBalance() / 10);
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
}
