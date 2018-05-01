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

public class Relativemove extends Card {

	private Space target;
	private int amount;

	public void setAmountTomove(int amount) {
		this.amount = amount;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		try {

			int temp = player.getCurrentPosition().getIndex() + amount;
			if (temp < 0) {
				temp = temp + 40;
			}
			List<Space> newspacelist = controller.getSpaces();
			target = newspacelist.get(temp);
			controller.moveToSpace(player, target);

		} finally {
			// Make sure that the card is returned to the deck even when
			// an Exception should occur!
			super.doAction(controller, player);
		}
	}

}
