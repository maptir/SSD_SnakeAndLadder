package snakeandladder;

public class Die {
	public static final int MAX_FACE = 6;
	private int face;

	public void roll() {
		face = (int) (Math.random() * MAX_FACE + 1);
	}

	public int getFace() {
		return face;
	}
}
