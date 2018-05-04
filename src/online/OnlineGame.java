package online;

import java.util.ArrayList;
import java.util.List;

import replay.Rolled;
import snakeandladder.Board;
import snakeandladder.Die;
import snakeandladder.Player;
import square.Square;

public class OnlineGame {

	private List<Player> players;
	private Die die;
	private Board board;

	private int currentPlayerIndex;
	private boolean ended;

	private List<Rolled> histories;
	private boolean isReplayMode;

	public OnlineGame() {
		System.out.println("INIT ONLINE GAME");
		currentPlayerIndex = 0;
		// Specify 4 for online
		players = new ArrayList<Player>();
		die = new Die();
		board = new Board();
		ended = false;
		histories = new ArrayList<>();

	}

	public void addPlayer(String name) {
		System.out.println("Add Player");
		Player player = new Player(name);
		board.addPiece(player.getPiece(), 0);
		players.add(player);
		System.out.println("Add Player : " + name);
	}

	public void reset() {
		currentPlayerIndex = 0;
		die = new Die();
		board = new Board();
		ended = false;
		// Later
		// for (int i = 0; i < players.length; i++) {
		// players[i] = new Player("P" + (i + 1));
		// board.addPiece(players[i].getPiece(), 0);
		// }
	}

	public boolean isEnd() {
		return ended;
	}

	public void end() {
		ended = true;
	}

	public Player currentPlayer() {
		return players.get(currentPlayerIndex);
	}

	public int currentPlayerIndex() {
		return currentPlayerIndex;
	}

	public void switchPlayer() {
		currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
		if (currentPlayer().isFreeze()) {
			currentPlayer().setFreeze(false);
			switchPlayer();
		}
	}

	public void currentPlayerMove(int steps) {
		this.board.movePiece(currentPlayer().getPiece(), steps);
		if (!isReplayMode)
			histories.add(new Rolled(currentPlayer(), steps));
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
		return players.size();
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
