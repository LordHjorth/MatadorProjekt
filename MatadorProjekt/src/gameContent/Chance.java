package gameContent;

import controllers.GameController;
import exceptions.PlayerBrokeException;

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

	public String getIcon(){
		Icon="?";
		return Icon;
		
	}
}
