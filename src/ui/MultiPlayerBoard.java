package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import online.PlayerClient;
import snakeandladder.Game;
import snakeandladder.Player;
import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class MultiPlayerBoard extends JPanel {
	private JButton rollButton;
	private JTextArea textArea;
	private JLabel dice;
	private JButton restartButton, replayButton;
	private JPanel endLabel;
	private JScrollPane scroll;
	private ImageIcon[] diceImages;
	private JLabel[] players;
	private static JFrame frame;
	private int boardSize;

	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HIEGHT = 840;

	private Game game;
	private PlayerClient playerClient;

	public MultiPlayerBoard (int numPlayer,PlayerClient playerClient) {
		this.playerClient = playerClient;
		this.game = new Game(numPlayer);
		this.boardSize = game.getBoardSize();
		frame = new JFrame("Snake and Ladder");
		frame.getContentPane().add(this);
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HIEGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		initialize();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("/res/board.png"));
			g.drawImage(img, 0, 0, FRAME_WIDTH, FRAME_HIEGHT, null);
		} catch (IOException e) {

		}
	}

	public void run() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void initialize() {
		this.setBounds(0, 0, FRAME_WIDTH, FRAME_HIEGHT);
		this.setLayout(null);

		endLabel = new JPanel();
		endLabel.setBounds(0, 290, FRAME_WIDTH, FRAME_HIEGHT);

		ImageIcon image = new ImageIcon(getClass().getResource("/res/replay.png"));
		replayButton = new JButton(image);
		replayButton.setOpaque(false);
		replayButton.setContentAreaFilled(false);
		replayButton.setBorderPainted(false);
		replayButton.setBounds(70, 310, 256, 110);
		replayButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		endLabel.add(replayButton);

		ImageIcon image1 = new ImageIcon(getClass().getResource("/res/restart.png"));
		restartButton = new JButton(image1);
		restartButton.setOpaque(false);
		restartButton.setContentAreaFilled(false);
		restartButton.setBorderPainted(false);
		restartButton.setBounds(350, 410, 270, 110);
		restartButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				restart();
			}
		});
		endLabel.add(restartButton);
		endLabel.setVisible(false);
		endLabel.setOpaque(false);
		this.add(endLabel);

		ImageIcon img = new ImageIcon(getClass().getResource("/res/roll.png"));
		rollButton = new JButton(img);
		rollButton.setDisabledIcon(img);
		rollButton.setBounds(526, 697, 144, 111);
		rollButton.setBorderPainted(false);
		rollButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Player currentPlayer = game.currentPlayer();
				addPlayerMoveMsg("Current Player is " + currentPlayer);

				int face = game.currentPlayerRollDice();
				addPlayerMoveMsg("The die is roll FACE = " + face);
				dice.setIcon(diceImages[face - 1]);
				movePiece(face);
				game.currentPlayerMove(face);
				addPlayerMoveMsg(currentPlayer + " is at " + (game.currentPlayerPosition() + 1));
				if (game.currentPlayerWin()) {
					addPlayerMoveMsg("Player " + currentPlayer.getName() + " WINS!");
					endLabel.setVisible(true);
					rollButton.setVisible(false);
					game.end();
				}
			}
		});
		this.add(rollButton);

		dice = new JLabel();
		diceImages = new ImageIcon[6];
		for (int i = 0; i < 6; i++) {
			diceImages[i] = new ImageIcon(getClass().getResource("/res/dice" + (i + 1) + ".png"));
		}
		dice.setBounds(395, 697, 111, 111);
		this.add(dice);

		textArea = new JTextArea();
		textArea.setEditable(false);
		scroll = new JScrollPane(textArea);
		scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setBounds(30, 697, 350, 111);
		this.add(scroll);

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
			this.add(players[i]);
		}
	}

	public void restart() {
		int x = 0;
		int y = 0;
		game = new Game(game.getNumPlayers());
		endLabel.setVisible(false);
		rollButton.setVisible(true);
		for (int i = 0; i < players.length; i++) {
			if (i == 0 || i == 1)
				x = 50;
			if (i == 2 || i == 4)
				x = 18;
			if (i == 0 || i == 2)
				y = 630;
			if (i == 1 || i == 3)
				y = 600;
			players[i].setLocation(x, y);
		}
		textArea.setText("");
		dice.setIcon(null);
	}

	public void movePiece(int steps) {
		int pos = game.currentPlayerPosition();
		String curName = game.currentPlayerName();
		Square curSquare = game.getCurrentSquare(pos);

		if (curSquare instanceof BackwardSquare) {
			addPlayerMoveMsg(curName + " found a TRAP !! MOVE BACK for -> " + steps);
			steps *= -1;
		}
		int newPos = pos + steps;
		System.out.println(newPos);
		rollButton.setEnabled(false);
		// Reach Goal
		if (newPos >= boardSize) {
			movePlayer(boardSize - pos - 1, curName, curSquare, pos);
			addPlayerMoveMsg(
					curName + " roll a die exceed the goal MOVE BACK for -> " + (steps - (boardSize - pos - 1)));
		} else {
			movePlayer(steps, curName, curSquare, pos);
		}
	}

	public void movePlayer(int steps, String curName, Square curSquare, int pos) {
		Timer timer = new Timer(100, new ActionListener() {
			JLabel curPlayer = players[game.currentPlayerIndex()];
			int playerPos = game.currentPlayerPosition() + 1;
			int i = 0;
			int gap = 64;
			int newPos = pos + steps;

			@Override
			public void actionPerformed(ActionEvent event) {
				System.out.println(steps);
				if (i < Math.abs(steps)) {
					if (Integer.signum(steps) > 0)
						moveForward();
					else
						moveBackward();
					playerPos += Integer.signum(steps);
					i++;
				} else {
					((Timer) event.getSource()).stop();
					Square curSquare = game.getCurrentSquare(playerPos - 1);
					if (curSquare instanceof LadderSquare) {
						LadderSquare ladderSquare = (LadderSquare) curSquare;
						addPlayerMoveMsg(curName + " found a LADDER at " + (newPos + 1) + " !! GOTO -> "
								+ (ladderSquare.goTo() + 1));
						movePiece(ladderSquare.goTo() - newPos);
						game.currentPlayerMove(ladderSquare.goTo() - newPos);
					}
					if (curSquare instanceof SnakeSquare) {
						SnakeSquare snakeSquare = (SnakeSquare) curSquare;
						addPlayerMoveMsg(curName + " found a SNAKE at " + (newPos + 1) + " !! BACKTO -> "
								+ (snakeSquare.goTo() + 1));
						movePiece(snakeSquare.goTo() - newPos);
						game.currentPlayerMove(snakeSquare.goTo() - newPos);
					}
					if (curSquare instanceof FreezeSquare) {
						game.currentPlayer().setFreeze(true);
						addPlayerMoveMsg(curName + " found a TRAP !! FREEZE for 1 round.");
					}
					if (newPos + 1 >= boardSize) {
						System.out.println(steps - (boardSize - pos));
						movePlayer(steps - (boardSize - pos), curName, curSquare, pos);
					} else {
						addPlayerMoveMsg("----------------------------------------");
						rollButton.setEnabled(true);
						game.switchPlayer();
					}
				}
			}

			public void moveForward() {
				if (playerPos % 10 == 0) {
					curPlayer.setLocation(curPlayer.getX(), curPlayer.getY() - gap);
				} else if ((playerPos / 10) % 2 == 0) {
					curPlayer.setLocation(curPlayer.getX() + gap, curPlayer.getY());
				} else {
					curPlayer.setLocation(curPlayer.getX() - gap, curPlayer.getY());
				}
			}

			public void moveBackward() {
				if (playerPos % 10 == 1) {
					curPlayer.setLocation(curPlayer.getX(), curPlayer.getY() + gap);
				} else if (((playerPos - 1) / 10) % 2 == 0) {
					curPlayer.setLocation(curPlayer.getX() - gap, curPlayer.getY());
				} else {
					curPlayer.setLocation(curPlayer.getX() + gap, curPlayer.getY());
				}
			}
		});
		timer.start();
	}

	public void addPlayerMoveMsg(String msg) {
		textArea.append(msg + "\n");
	}
}
