package properties;

import java.awt.Color;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import spaces.Property;
/**
 * 
 * @author emil_
 *Shipping class used in order to calculate rent dependend on the amount of ferries owned.
 *
 */
public class Shipping extends Property {
	private Color color = new Color(173, 216, 230);
	

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
		if (owner == null) {
			player.setChanceCardShipping(false);
			controller.offerToBuy(this, player);
		}

		else if ((!owner.equals(player))&&player.isChanceCardShipping()==true&&!this.isMortaged()==true) {
			controller.payment(player,2* this.getRent()*controller.checkOwnershipAmount(owner, this), owner);
			player.setChanceCardShipping(false);
		}
		else if (!owner.equals(player)&&!this.isMortaged()==true) {
			controller.payment(player, this.getRent()*controller.checkOwnershipAmount(owner, this), owner);
		}
	}

}