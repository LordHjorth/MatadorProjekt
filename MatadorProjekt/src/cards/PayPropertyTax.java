package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class PayPropertyTax extends Card {

	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
	//TODO hvordan gør man med at betale pr. hus og hotel?
		
		try {
			controller.paymentToBank(player, player.getBalance());
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
}
