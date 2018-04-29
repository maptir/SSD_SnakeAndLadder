package snakeandladder;

public class Game {
	private Player[] players;
	private Die die;
	private Board board;

	private int currentPlayerIndex;
	private boolean ended;

	public Game(int numPlayer) {
		currentPlayerIndex = 0;
		players = new Player[numPlayer];
		die = new Die();
		board = new Board();
		ended = false;

		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("P" + (i + 1));
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public boolean isEnd() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players[currentPlayerIndex];
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
	}

	public void currentPlayerMove(int steps) {
		this.board.movePiece(currentPlayer().getPiece(), steps);
	}

	public String currentPlayerName() {
		return currentPlayer().getName();
	}

	public int currentPlayerPosition() {
		return board.getPiecePos(currentPlayer().getPiece());
	}

	public int currentPlayerRollDice() {
		return currentPlayer().roll(die);
	}

	public boolean currentPlayerWin() {
		return board.pieceIsAtGoal(currentPlayer().getPiece());
	}

	public int getNumPlayers() {
		return players.length;
	}
}
