package snakeandladder;

public class Board {
	public static final int SIZE = 64;
	private Square[] squares;

	public Board() {
		this.squares = new Square[SIZE];
		for (int i = 0; i < squares.length; i++)
			squares[i] = new Square(i);
		squares[squares.length - 1].setGoal(true);
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void movePiece(Piece piece, int steps) {
		int pos = getPiecePos(piece);
		squares[pos].removePiece(piece);
		int newPos = pos + steps;
		if (newPos >= squares.length)
			newPos = 2 * (squares.length - 1) - newPos;
		addPiece(piece, newPos);
	}

	public int getPiecePos(Piece piece) {
		for (Square s : squares)
			if (s.hasPiece(piece))
				return s.getNumber();
		return -1;
	}

	public boolean pieceIsAtGoal(Piece piece) {
		return squares[getPiecePos(piece)].isGoal();
	}

}
