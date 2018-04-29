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
import javax.swing.JTextField;

import snakeandladder.Game;
import snakeandladder.Player;

public class BoardUI {
	private JPanel board;
	private JButton rollButton;
	private JLabel dice;
	private JTextField textfield;
	private JLabel[] players;

	private Game game;

	public BoardUI(Game game) {
		this.game = game;
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
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = game.currentPlayer();
				System.out.println("Current Player is " + currentPlayer);
				if (currentPlayer.isFreeze()) {
					System.out.println(currentPlayer.getName() + " is FREEZE can't walk for 1 round.");
					currentPlayer.setFreeze(false);
					return;
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
				}
			}
		});
		board.add(rollButton);

		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/dice1.png"));
		dice = new JLabel(img2);
		dice.setBounds(395, 697, 111, 111);
		board.add(dice);

		textfield = new JTextField();
		textfield.setBounds(30, 697, 350, 111);
		board.add(textfield);

		players = new JLabel[game.getNumPlayers()];
		for (int i = 0; i < players.length; i++) {
			ImageIcon playerImage = new ImageIcon(getClass().getResource("/res/player" + (i + 1) + ".png"));
			players[i] = new JLabel(playerImage);
			players[i].setBounds(30, 500, 127, 189);
			board.add(players[i]);
		}
	}

	public JPanel getPanel() {
		return board;
	}

}
