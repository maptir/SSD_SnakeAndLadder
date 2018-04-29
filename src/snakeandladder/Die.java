package snakeandladder;

public class Die {
	public static final int MAX_FACE = 6;
	private int face;

	public void roll() {
		face = 3;
	}

	public int getFace() {
		return face;
	}
}
