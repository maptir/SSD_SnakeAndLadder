package snakeandladder;

import java.util.Scanner;

public class ConsoleUI {
	public void start(Game game) {
		Scanner sc = new Scanner(System.in);
		while (!game.isEnd()) {
			Player currentPlayer = game.currentPlayer();
			System.out.println("Current Player is " + currentPlayer);
			int face = game.currentPlayerRollDice();
			System.out.println("The die is roll FACE = " + face);
			game.currentPlayerMove(face);
			System.out.println(currentPlayer + " is at " + game.currentPlayerPosition());
			if (game.currentPlayerWin()) {
				System.out.println("Player " + currentPlayer.getName() + " WINS!");
				game.end();
			} else {
				game.switchPlayer();
				System.out.println("Please [ENTER] to roll dice.");
			}
			sc.nextLine();
		}
		System.out.println("--- Game is END ---");
		System.out.println("RESTART(1) or CLOSE(2): ");
		switch (sc.nextInt()) {
		case 1:
			restart(game);
			break;
		default:
			break;
		}
	}

	public void restart(Game game) {
		game = new Game(game.getNumPlayers());
		start(game);
	}

	public static void main(String[] args) {
		ConsoleUI ui = new ConsoleUI();
		Game game = new Game(4);
		ui.start(game);
	}
}
