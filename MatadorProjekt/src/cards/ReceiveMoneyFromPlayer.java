package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class ReceiveMoneyFromPlayer extends Card {

	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public void doAction(GameController controller, Player player,Player receiver) throws PlayerBrokeException 
	{
		try {
			controller.payment(player, amount, receiver);
		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}	
	}
}
