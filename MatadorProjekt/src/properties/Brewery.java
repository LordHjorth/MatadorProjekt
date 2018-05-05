package properties;

import java.awt.Color;

import controllers.GameController;
import exceptions.PlayerBrokeException;
import gameContent.Player;
import spaces.Property;
/**
 * 
 * @author emil_
 *Brewery class, used in order to calculate rent, depending on amount of breweries owned and the die throw used to land on the brewery.
 */
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

		else if (!owner.equals(player)&&!this.isMortaged()==true) {
			controller.payment(player, this.getActualRent()*controller.getDieThrow(), owner);
		}

	}


	}
	
	
	
	
	
	

