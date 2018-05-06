package spaces;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import gameContent.Space;

/**
 * Represents a space, where the user has to draw a chance card.
 * 
 * @author Ekkart Kindler, ekki@dtu.dk
 *
 */
public class Chance extends Space {

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		controller.takeChanceCard(player);
	}
	
	private String Icon;

	/**
	 * Gets the icon.
	 * @author emil
	 * @return the icon
	 */
	public String getIcon(){
		Icon="?";
		return Icon;
		
	}
}
