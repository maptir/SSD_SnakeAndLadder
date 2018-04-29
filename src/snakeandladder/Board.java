package snakeandladder;

import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class Board {
	public static final int SIZE = 100;
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
		squares[2] = new LadderSquare(squares[2].getNumber(), 38);
		squares[7] = new LadderSquare(squares[6].getNumber(), 14);
		squares[8] = new LadderSquare(squares[7].getNumber(), 31);
		squares[15] = new LadderSquare(squares[14].getNumber(), 26);
		squares[21] = new LadderSquare(squares[20].getNumber(), 42);
		squares[28] = new LadderSquare(squares[27].getNumber(), 84);
		squares[36] = new LadderSquare(squares[35].getNumber(), 44);
		squares[51] = new LadderSquare(squares[50].getNumber(), 67);
		squares[71] = new LadderSquare(squares[70].getNumber(), 91);
		squares[78] = new LadderSquare(squares[77].getNumber(), 98);
		squares[87] = new LadderSquare(squares[86].getNumber(), 94);
		squares[16] = new SnakeSquare(squares[15].getNumber(), 6);
		squares[49] = new SnakeSquare(squares[48].getNumber(), 11);
		squares[62] = new SnakeSquare(squares[63].getNumber(), 19);
		squares[46] = new SnakeSquare(squares[45].getNumber(), 25);
		squares[64] = new SnakeSquare(squares[63].getNumber(), 60);
		squares[74] = new SnakeSquare(squares[73].getNumber(), 53);
		squares[89] = new SnakeSquare(squares[88].getNumber(), 68);
		squares[95] = new SnakeSquare(squares[94].getNumber(), 75);
		squares[92] = new SnakeSquare(squares[91].getNumber(), 88);
		squares[99] = new SnakeSquare(squares[98].getNumber(), 80);
		squares[17] = new FreezeSquare(squares[16].getNumber());
		squares[56] = new FreezeSquare(squares[55].getNumber());
		squares[61] = new FreezeSquare(squares[60].getNumber());
		squares[70] = new FreezeSquare(squares[69].getNumber());
		squares[97] = new FreezeSquare(squares[96].getNumber());
		squares[9] = new BackwardSquare(squares[8].getNumber());
		squares[20] = new BackwardSquare(squares[21].getNumber());
		squares[45] = new BackwardSquare(squares[44].getNumber());
		squares[77] = new BackwardSquare(squares[76].getNumber());
		squares[86] = new BackwardSquare(squares[85].getNumber());
	}

	public void addPiece(Piece piece, int pos) {
		squares[pos].addPiece(piece);
	}

	public void movePiece(Player player, Piece piece, int steps) {
		int pos = getPiecePos(piece);

		if (squares[pos] instanceof BackwardSquare)
			steps *= -1;
		int newPos = pos + steps;
		if (newPos >= squares.length)
			newPos = 2 * (squares.length - 1) - newPos;
		bView.movePlayer(steps);
		squares[pos].removePiece(piece);
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
