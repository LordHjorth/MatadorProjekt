package cards;
/**
 * @author emil_
 */

import java.util.List;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Card;
import gameContent.Player;
import gameContent.Space;

/**
 * The Class CardMoveToShipping.
 * 
 * @author emil_
 */
public class CardMoveToShipping extends Card{


	private Space target;
	

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		try {
			List<Space> newspacelist = controller.getSpaces();
			
			int temp = player.getCurrentPosition().getIndex();
			if ((4>=temp&&temp>=0)||(39>=temp&&temp>=36)) {
				target = newspacelist.get(5);	
			}
			if ((14>=temp&&temp>=5)) {
			     target = newspacelist.get(15);	
			}
			if ((24>=temp&&temp>=15)) {
			     target = newspacelist.get(25);	
			}
			if ((34>=temp&&temp>=25)) {
			     target = newspacelist.get(35);	
			}
			player.setChanceCardShipping(true);
			controller.moveToSpace(player, target);
			

		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}
	
	
}
