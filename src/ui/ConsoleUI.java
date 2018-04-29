package ui;

import java.util.Scanner;

import replay.Rolled;
import snakeandladder.Game;
import snakeandladder.Player;

public class ConsoleUI {
	public void start(Game game) {
		Scanner sc = new Scanner(System.in);
		while (!game.isEnd()) {
			Player currentPlayer = game.currentPlayer();
			System.out.println("Current Player is " + currentPlayer);
			if (currentPlayer.isFreeze()) {
				System.out.println(currentPlayer.getName() + " is FREEZE can't walk for 1 round.");
				currentPlayer.setFreeze(false);
				continue;
			}
			int face = game.currentPlayerRollDice();
			System.out.println("The die is roll FACE = " + face);
			game.currentPlayerMove(face);
			System.out.println(currentPlayer + " is at " + game.currentPlayerPosition());
			if (game.currentPlayerWin()) {
				System.out.println("Player " + currentPlayer.getName() + " WINS!");
				game.end();
			} else {
				game.switchPlayer();
				// System.out.println("Please [ENTER] to roll dice.");
			}
			sc.nextLine();
		}
		System.out.println("--- Game is END ---");
		System.out.println("RESTART(1), REPLAY(2) or CLOSE(else): ");
		switch (sc.nextInt()) {
		case 1:
			restart(game);
			break;
		case 2:
			replay(game);
			break;
		default:
			break;
		}
	}

	public void restart(Game game) {
//		game = new Game(game.getNumPlayers());
		start(game);
	}

	public void replay(Game game) {
		for (Rolled rolled : game.getHistories()) {
			System.out.println("Current Player is " + rolled.getPlayer());
			System.out.println("The die is roll FACE = " + rolled.getRolled());
			System.out.println(rolled.getPlayer() + " is at " + rolled.getCurrentPos());
		}
	}

	public static void main(String[] args) {
		ConsoleUI ui = new ConsoleUI();
//		Game game = new Game(2);
//		ui.start(game);
	}
}
