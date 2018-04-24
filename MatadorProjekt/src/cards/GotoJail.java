package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class GotoJail extends Card{

	
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		try {
			controller.gotoJail(player);
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
	
	
}
