package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class CardPayMoney extends Card {
	
	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
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
