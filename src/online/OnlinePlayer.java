package online;

import snakeandladder.Board;
import snakeandladder.Die;
import snakeandladder.Piece;

public class OnlinePlayer {
	
	private String name;
	private boolean freeze;

	public OnlinePlayer(String name) {
		this.name = name;
		this.freeze = false;
	}

	public String getName() {
		return name;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}

	public String toString() {
		return getName();
	}
	
}
