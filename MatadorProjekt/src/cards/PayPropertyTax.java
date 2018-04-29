package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;
import properties.RealEstate;

public class PayPropertyTax extends Card {

	public void doAction(GameController controller, Player player, RealEstate realestate) throws PlayerBrokeException {
	//TODO hvordan gør man med at betale pr. hus og hotel?
		
		try {
			controller.paymentToBank(player, 800*realestate.getHouses()*controller.checkOwnershipAmount(player, realestate)) ;
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
}
