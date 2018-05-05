package properties;

import java.awt.Color;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import spaces.Property;

/**
 * 
 * @author emil_ Shipping class used in order to calculate rent dependend on the
 *         amount of ferries owned.
 *
 */
public class Shipping extends Property {
	private Color color = new Color(173, 216, 230);

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public void setActualRent() {
		int modifier = 0;
		this.actualrent=this.rent;

		for (Property p : owner.getOwnedProperties()) {
			if (p instanceof Shipping) {
				modifier++;
			}

		}

		if (!this.isMortaged()) {
			this.actualrent = this.rent * modifier;

		}
		if (this.isMortaged()) {
			this.actualrent = 0;

		}

		notifyChange();
	}

	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {

		if (owner == null) {
			player.setChanceCardShipping(false);
			controller.offerToBuy(this, player);
			this.setActualRent();
			return;
		}

		if ((!owner.equals(player)) && player.isChanceCardShipping()) {
			controller.payment(player, 2 * this.getActualRent(), owner);
			player.setChanceCardShipping(false);
		}
		if (!owner.equals(player)) {
			controller.payment(player, this.getActualRent(), owner);
		}

	}

}