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

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		// TODO note that tax concerns all assets an not just cash - should be fixed now
		// andre værdi genstande end penge og grunde?
		int payment = player.getBalance();
		
		for(Property p : player.getOwnedProperties()) {
			payment += p.getCost();
		}
		payment = payment / 10;
		controller.paymentToBank(player, payment);
	}

}
