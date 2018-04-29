package replay;

import snakeandladder.Player;

public class Rolled {

	private Player player;
	private int rolled;
	private int currentPos;

	public Rolled(Player player, int rolled, int currentPos) {
		this.player = player;
		this.rolled = rolled;
		this.currentPos = currentPos;
	}

	public Player getPlayer() {
		return player;
	}

	public int getRolled() {
		return rolled;
	}
	
	public int getCurrentPos() {
		return currentPos;
	}
}
