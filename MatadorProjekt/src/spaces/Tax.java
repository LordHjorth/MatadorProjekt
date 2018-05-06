package spaces;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import gameContent.Space;

/**
 * Represents a space, where the player has to pay tax.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Tax extends Space {
	
	/**
	 * @author emil_ - added some changes to an existing method
	 */
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		if(player.getCurrentPosition()==controller.getSpaces().get(38)) {
			controller.paymentToBank(player, 2000);
		}
		else if(player.getCurrentPosition()==controller.getSpaces().get(4)) {
			 controller.paymentToBank(player,(player.getPlayerValue()/10));
		}
		
	}

}
