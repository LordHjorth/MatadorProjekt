package gameContent;

import controllers.GameController;
import exceptions.PlayerBrokeException;

public class Jail extends Space {

	private boolean jail;

	public void setJail(boolean jail) {
		this.jail = jail;
	}

	public boolean getJail() {
		return jail;

	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		if (getJail() == true) {
			controller.gotoJail(player);
		}

		else if (getJail() == false) {
		}
	}

}
