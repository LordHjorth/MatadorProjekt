package connection;

/**
 * The Class Constants.
 * 
 * @author Rasmus
 */
public enum Constants {

	PLAYER_ID("id"),
	NAME("name"),
	BALANCE("balance"),
	POSITION("position"),
	POS_INDEX("PosIndex"),
	IN_PRISON("inPrison"),
	COLOR("color"),
	CAR_COLOR("carColor"),
	PARDON("pardon"),
	GAME_ID("gameID"),
	PROPERTY_ID("PropID"),
	PROPERTY_NAME("PropertyName"),
	HOUSES("houses"),
	MORTGAGE("isMortaged"),
	HOTEL("Hotel");
	
	private final String s;
	
	/**
	 * Instantiates a new constants.
	 *
	 * @param s the s
	 */
	Constants(String s){
		this.s = s;
	}

	/**
	 * Overrides toString() and return the String value of the enum.
	 */
	@Override
	public String toString() {
		return s;
	}
}

