package snakeandladder;

import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class Board {
	public static final int SIZE = 64;
	private Square[] squares;
	private BoardView bView;

	public Board(BoardView bView) {
		this.squares = new Square[SIZE];
		this.bView = bView;
		for (int i = 0; i < squares.length; i++)
			squares[i] = new Square(i);

		initSpecialSquare();
		squares[squares.length - 1].setGoal(true);
	}

	public void initSpecialSquare() {
		squares[2] = new LadderSquare(squares[3].getNumber(), 38);
		squares[16] = new SnakeSquare(squares[16].getNumber(), 6);
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void movePiece(Player player, Piece piece, int steps) {
		int pos = getPiecePos(piece);
		squares[pos].removePiece(piece);

		if (squares[pos] instanceof BackwardSquare)
			steps *= -1;
		int newPos = pos + steps;
		if (newPos >= squares.length)
			newPos = 2 * (squares.length - 1) - newPos;
		bView.movePlayer(newPos);
		addPiece(piece, newPos);

		if (squares[newPos] instanceof LadderSquare) {
			LadderSquare ladderSquare = (LadderSquare) squares[newPos];
			steps = ladderSquare.goTo() - newPos;
			System.out.println("Found a ladder !! GOTO -> " + newPos);
			movePiece(player, piece, steps);
		}

		if (squares[newPos] instanceof SnakeSquare) {
			SnakeSquare snakeSquare = (SnakeSquare) squares[newPos];
			steps = newPos - snakeSquare.goTo();
			System.out.println("Found a snake !! GOTO -> " + newPos);
			movePiece(player, piece, steps);
		}

		if (squares[newPos] instanceof FreezeSquare) {
			player.setFreeze(true);
		}
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
