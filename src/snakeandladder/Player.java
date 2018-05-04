package snakeandladder;

public class Player {

	private String name;
	private Piece piece;
	private boolean freeze;

	public Player(String name) {
		this.name = name;
		this.piece = new Piece();
		this.freeze = false;
	}

	public int roll(Die die) {
		die.roll();
		return die.getFace();
	}

	public String getName() {
		return name;
	}

	public void movePiece(Board board, int steps) {
		board.movePiece(piece, steps);
	}

	public Piece getPiece() {
		return piece;
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
