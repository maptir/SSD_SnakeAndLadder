package snakeandladder;

import java.util.ArrayList;
import java.util.List;

import replay.Rolled;
import square.Square;

public class Game {
	private Player[] players;
	private Die die;
	private Board board;

	private int currentPlayerIndex;
	private boolean ended;

	private List<Rolled> histories;
	private boolean isReplayMode;

	public Game(int numPlayer) {
		currentPlayerIndex = 0;
		players = new Player[numPlayer];
		die = new Die();
		board = new Board();
		ended = false;
		histories = new ArrayList<>();

		for (int i = 0; i < players.length; i++) {
			players[i] = new Player("P" + (i + 1));
			board.addPiece(players[i].getPiece(), 0);
		}
	}

	public void reset() {
		currentPlayerIndex = 0;
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

	public int currentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
		if (currentPlayer().isFreeze()) {
			currentPlayer().setFreeze(false);
			switchPlayer();
		}
	}

	public void currentPlayerMove(int steps) {
		this.board.movePiece(currentPlayer().getPiece(), steps);
		if (!isReplayMode)
			histories.add(new Rolled(currentPlayer(), steps, currentPlayerPosition()));
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

	public List<Rolled> getHistories() {
		return histories;
	}

	public Square getCurrentSquare(int pos) {
		return board.getSquare(pos);
	}

	public int getBoardSize() {
		return board.getBoardSize();
	}
	
	public boolean isReplayMode() {
		return isReplayMode;
	}
	
	public void setReplayMode(boolean isReplayMode) {
		this.isReplayMode = isReplayMode;
	}
}
