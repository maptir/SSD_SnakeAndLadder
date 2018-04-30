package snakeandladder;

import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class Board {
	public static final int SIZE = 11;
	private Square[] squares;
	private BoardView bView;

	public Board(BoardView bView) {
		this.squares = new Square[SIZE];
		this.bView = bView;
		for (int i = 0; i < squares.length; i++)
			squares[i] = new Square(i);

//		initSpecialSquare();
		squares[squares.length - 1].setGoal(true);
	}

	public void initSpecialSquare() {
		squares[1] = new LadderSquare(squares[1].getNumber(), 37);
		squares[6] = new LadderSquare(squares[6].getNumber(), 13);
		squares[7] = new LadderSquare(squares[7].getNumber(), 30);
		squares[14] = new LadderSquare(squares[14].getNumber(), 25);
		squares[20] = new LadderSquare(squares[20].getNumber(), 41);
		squares[27] = new LadderSquare(squares[27].getNumber(), 83);
		squares[35] = new LadderSquare(squares[35].getNumber(), 43);
		squares[50] = new LadderSquare(squares[50].getNumber(), 66);
		squares[70] = new LadderSquare(squares[70].getNumber(), 90);
		squares[77] = new LadderSquare(squares[77].getNumber(), 97);
		squares[86] = new LadderSquare(squares[86].getNumber(), 93);

		squares[15] = new SnakeSquare(squares[15].getNumber(), 5);
		squares[48] = new SnakeSquare(squares[48].getNumber(), 10);
		squares[61] = new SnakeSquare(squares[61].getNumber(), 18);
		squares[45] = new SnakeSquare(squares[45].getNumber(), 24);
		squares[63] = new SnakeSquare(squares[63].getNumber(), 59);
		squares[73] = new SnakeSquare(squares[73].getNumber(), 52);
		squares[88] = new SnakeSquare(squares[88].getNumber(), 67);
		squares[91] = new SnakeSquare(squares[91].getNumber(), 87);
		squares[94] = new SnakeSquare(squares[94].getNumber(), 74);
		squares[98] = new SnakeSquare(squares[98].getNumber(), 79);

		squares[16] = new FreezeSquare(squares[16].getNumber());
		squares[55] = new FreezeSquare(squares[55].getNumber());
		squares[60] = new FreezeSquare(squares[60].getNumber());
		squares[69] = new FreezeSquare(squares[69].getNumber());
		squares[96] = new FreezeSquare(squares[96].getNumber());

		squares[8] = new BackwardSquare(squares[8].getNumber());
		squares[19] = new BackwardSquare(squares[19].getNumber());
		squares[44] = new BackwardSquare(squares[44].getNumber());
		squares[76] = new BackwardSquare(squares[76].getNumber());
		squares[85] = new BackwardSquare(squares[85].getNumber());
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void movePiece(Player player, Piece piece, int steps) {
		int pos = getPiecePos(piece);
		String curName = player.getName();

		if (squares[pos] instanceof BackwardSquare) {
			addPlayerMoveMsg(curName + " found a TRAP !! MOVE BACK for -> " + steps);
			steps *= -1;
		}
		int newPos = pos + steps;
		// Reach Goal
		if (newPos >= squares.length) {
			newPos = 2 * (squares.length - 1) - newPos;
			movePlayer(squares.length - pos - 1);
			movePlayer((squares.length - pos - 1) - steps);
			addPlayerMoveMsg(
					curName + " roll a die exceed the goal MOVE BACK for -> " + (steps - (squares.length - pos - 1)));
		} else {
			movePlayer(steps);
		}
		squares[pos].removePiece(piece);
		addPiece(piece, newPos);

		if (squares[newPos] instanceof LadderSquare) {
			LadderSquare ladderSquare = (LadderSquare) squares[newPos];
			steps = ladderSquare.goTo() - newPos;
			addPlayerMoveMsg(
					curName + " found a LADDER at " + (newPos + 1) + " !! GOTO -> " + (ladderSquare.goTo() + 1));
			movePiece(player, piece, steps);
		}

		if (squares[newPos] instanceof SnakeSquare) {
			SnakeSquare snakeSquare = (SnakeSquare) squares[newPos];
			steps = snakeSquare.goTo() - newPos;
			addPlayerMoveMsg(
					curName + " found a SNAKE at " + (newPos + 1) + " !! BACKTO -> " + (snakeSquare.goTo() + 1));
			movePiece(player, piece, steps);
		}

		if (squares[newPos] instanceof FreezeSquare) {
			player.setFreeze(true);
			addPlayerMoveMsg(curName + " found a TRAP !! FREEZE for 1 round.");
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

	public void addPlayerMoveMsg(String msg) {
		bView.addPlayerMoveMsg(msg);
	}

	public void movePlayer(int steps) {
		bView.movePlayer(steps);
	}

}
