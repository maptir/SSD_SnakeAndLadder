package square;

public class FreezeSquare extends Square {
	
	private int goTo;
	private boolean freeze;
	

	public FreezeSquare(int number) {
		super(number);
		this.goTo = number;
		this.freeze = true;
	}
	
	public int goTo() {
		return goTo;
	}

	public boolean isFreeze() {
		return freeze;
	}

	public void setFreeze(boolean freeze) {
		this.freeze = freeze;
	}
	
}
