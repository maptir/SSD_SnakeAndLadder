package ui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import snakeandladder.Game;
import snakeandladder.Player;
import snakeandladder.BoardView;
import square.BackwardSquare;

public class BoardUI implements BoardView {
	private JPanel board;
	private JButton rollButton;
	private JTextArea textArea;
	private JLabel dice;
	private ImageIcon[] diceImages;
	private JLabel[] players;

	private Game game;

	public BoardUI() {
		this.game = new Game(4, this);
		initialize();
	}

	private void initialize() {
		// TODO Auto-generated method stub
		board = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResource("/res/board.png"));
					g.drawImage(img, 0, 0, 700, 840, null);
				} catch (IOException e) {

				}
			}
		};
		board.setBounds(0, 0, 700, 840);
		board.setLayout(null);

		ImageIcon img = new ImageIcon(getClass().getResource("/res/roll.png"));
		rollButton = new JButton(img);
		rollButton.setBounds(526, 697, 144, 111);
		rollButton.setBorderPainted(false);
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = game.currentPlayer();
				textArea.append("Current Player is " + currentPlayer + "\n");

				if (currentPlayer.isFreeze()) {
					System.out.println(currentPlayer.getName() + " is FREEZE can't walk for 1 round.");
					currentPlayer.setFreeze(false);
					return;
				}

				int face = game.currentPlayerRollDice();
				System.out.println("The die is roll FACE = " + face);
				dice.setIcon(diceImages[face - 1]);
				game.currentPlayerMove(face);
				System.out.println(currentPlayer + " is at " + game.currentPlayerPosition());
				if (game.currentPlayerWin()) {
					System.out.println("Player " + currentPlayer.getName() + " WINS!");
					game.end();
				} else {
					game.switchPlayer();
				}
			}
		});
		board.add(rollButton);

		dice = new JLabel();
		diceImages = new ImageIcon[6];
		for (int i = 0; i < 6; i++) {
			diceImages[i] = new ImageIcon(getClass().getResource("/res/dice" + (i + 1) + ".png"));
		}
		dice.setBounds(395, 697, 111, 111);
		board.add(dice);

		textArea = new JTextArea();
		textArea.setBounds(30, 697, 350, 111);
		textArea.setEditable(false);
		board.add(textArea);

		int x = 0;
		int y = 0;
		players = new JLabel[game.getNumPlayers()];
		for (int i = 0; i < players.length; i++) {
			ImageIcon playerImage = new ImageIcon(getClass().getResource("/res/player" + (i + 1) + ".png"));
			players[i] = new JLabel(playerImage);
			if (i == 0 || i == 1) {
				x = 50;
			}
			if (i == 2 || i == 4) {
				x = 18;
			}
			if (i == 0 || i == 2) {
				y = 630;
			}
			if (i == 1 || i == 3) {
				y = 600;
			}
			players[i].setBounds(x, y, 51, 44);
			board.add(players[i]);
		}
	}

	public JPanel getPanel() {
		return board;
	}

	@Override
	public void movePlayer(int steps) {
		int playerPos = game.currentPlayerPosition() + 1;
		for (int i = 0; i < steps; i++) {
			try {
				JLabel curPlayer = players[game.currentPlayerIndex()];
				System.out.println(playerPos);
				if (playerPos % 10 == 0) {
					curPlayer.setBounds(curPlayer.getX(), curPlayer.getY() - 64, 51, 44);
				} else if ((playerPos / 10) % 2 == 0) {
					curPlayer.setBounds(curPlayer.getX() + 64, curPlayer.getY(), 51, 44);
				} else {
					curPlayer.setBounds(curPlayer.getX() - 64, curPlayer.getY(), 51, 44);
				}
//				Thread.sleep(50);
				playerPos++;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
