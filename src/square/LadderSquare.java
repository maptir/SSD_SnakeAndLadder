package square;

public class LadderSquare extends Square {

	private int goTo;

	public LadderSquare(int number, int goTo) {
		super(number);
		this.goTo = goTo;
	}

	public int goTo() {
		return goTo;
	}

}
