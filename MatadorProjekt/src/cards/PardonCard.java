package cards;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;


/**
 * The Class PardonCard.
 * 
 * @author Nicolas_
 * 
 */
public class PardonCard extends Card {
	
	
	/* (non-Javadoc)
	 * @see gameContent.Card#doAction(controllers.GameController, gameContent.Player)
	 */
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		player.setOwnedCard(this);

	}

}
