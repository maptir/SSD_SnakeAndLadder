package snakeandladder;

import java.util.Scanner;

public class ConsoleUI {
	public void start(Game game) {
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
			Scanner sc = new Scanner(System.in);
			sc.nextLine();
		}
	}

	public static void main(String[] args) {
		ConsoleUI ui = new ConsoleUI();
		Game game = new Game();
		ui.start(game);
	}
}
