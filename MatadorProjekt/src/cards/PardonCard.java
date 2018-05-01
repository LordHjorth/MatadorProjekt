package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;

public class PardonCard extends Card {
	
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		player.setOwnedCard(this);

	}

}
