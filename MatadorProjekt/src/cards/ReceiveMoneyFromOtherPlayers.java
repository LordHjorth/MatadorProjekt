package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class ReceiveMoneyFromOtherPlayers extends Card {

	private int amount;
	
	public int getAmount() {
		return amount;
	}
	
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
