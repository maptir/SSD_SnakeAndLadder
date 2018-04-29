package square;

public class SnakeSquare extends Square {
	
	private int goTo;
	
	public SnakeSquare(int number,int goTo) {
		super(number);
		this.goTo = goTo;
	}
	
	public int goTo() {
		return goTo;
	}

}
