package properties;

import java.awt.Color;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import gameContent.Property;

public class Brewery extends Property{
	private Color color= new Color(154,205,50);
	public Color getColor() {
		return color;
	}
	
	
	
	
	@Override
	public void doAction(GameController controller, Player player) throws PlayerBrokeException {
	
		if (owner == null) {
			controller.offerToBuy(this, player);
			
		}

		else if (!owner.equals(player)) {
			controller.payment(player, this.getRent()*controller.getDieThrow()*controller.checkOwnershipAmount(owner, this), owner);
		}

	}


	}
	
	
	
	
	
	

