package online;

import java.util.ArrayList;
import java.util.List;

import replay.Rolled;
import snakeandladder.Board;
import snakeandladder.Die;
import snakeandladder.Player;
import square.Square;

public class OnlineGame {
	
	private Die die;
	private int count;

	public OnlineGame(int numPlayer) {
		die = new Die();
	}
	
	public int roll() {
		die.roll();
		return die.getFace();
	}
	
	public Die getDie() {
		return die;
	}

	public void setDie(Die die) {
		this.die = die;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}
