package replay;

import snakeandladder.Player;

public class Rolled {

	private Player player;
	private int rolled;

	public Rolled(Player player, int rolled) {
		this.player = player;
		this.rolled = rolled;
	}

	public Player getPlayer() {
		return player;
	}

	public int getRolled() {
		return rolled;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public void setRolled(int rolled) {
		this.rolled = rolled;
	}

	@Override
	public String toString() {
		return player.getName() + " roll " + rolled;
	}
}
