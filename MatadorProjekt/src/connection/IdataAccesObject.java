package connection;

import gameContent.Player;

public interface IdataAccesObject {
	
	public void createPlayerDB(Player p, int ID, String color);
	
	public Object[] updatePlayerView(Player player);
	
	public Object[][] updatePropertyView(int rowCount);
	
	public int getNumberOfPlayers();
	

}
