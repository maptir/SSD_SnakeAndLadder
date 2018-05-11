package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import replay.Rolled;
import snakeandladder.Game;
import snakeandladder.Player;
import square.BackwardSquare;
import square.FreezeSquare;
import square.LadderSquare;
import square.SnakeSquare;
import square.Square;

public class BoardUI extends JPanel {
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
	private int historiesIndex;

	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HIEGHT = 840;

	private Game game;

	public BoardUI(int numPlayer) {
		this.game = new Game(numPlayer);
		this.boardSize = game.getBoardSize();
		this.historiesIndex = 0;
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
				restart();
				game.reset();
				game.setReplayMode(true);
				replay(game.getHistories().get(historiesIndex++));
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
				game = new Game(game.getNumPlayers());
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
				rollButton.setEnabled(false);
				dieRoll(game.currentPlayerRollDice(), game.currentPlayer());
			}
		});
		this.add(rollButton);

		dice = new JLabel();
		diceImages = new ImageIcon[6];
		for (int i = 0; i < 6; i++)
			diceImages[i] = new ImageIcon(getClass().getResource("/res/dice" + (i + 1) + ".png"));
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
		historiesIndex = 0;
		endLabel.setVisible(false);
		rollButton.setVisible(true);
		rollButton.setEnabled(true);
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

	public void replay(Rolled rolled) {
		dieRoll(rolled.getRolled(), rolled.getPlayer());
	}

	public void dieRoll(int face, Player currentPlayer) {
		addPlayerMoveMsg("Current Player is " + currentPlayer);
		addPlayerMoveMsg("The die is roll FACE = " + face);
		if (face > 0 && face <= 6)
			dice.setIcon(diceImages[face - 1]);
		movePlayer(face);
		game.currentPlayerMove(face);
		addPlayerMoveMsg(currentPlayer + " is at " + (game.currentPlayerPosition() + 1));
		if (game.currentPlayerWin())
			playerWin(currentPlayer);
	}

	public void playerWin(Player currentPlayer) {
		addPlayerMoveMsg("Player " + currentPlayer.getName() + " WINS!");
		endLabel.setVisible(true);
		rollButton.setVisible(false);
		game.setReplayMode(false);
		game.end();
	}

	public void movePlayer(int steps) {
		int pos = game.currentPlayerPosition();
		if (game.getCurrentSquare(pos) instanceof BackwardSquare) {
			addPlayerMoveMsg(game.currentPlayerName() + " found a TRAP !! MOVE BACK for -> " + steps);
			steps *= -1;
		}
		int newPos = pos + steps;
		// Reach Goal
		if (newPos >= boardSize) {
			// Walk forward to goal and then backward if roll exceed boardSize.
			movePlayerHelper(boardSize - pos - 1, pos, newPos);
		} else {
			movePlayerHelper(steps, pos, newPos);
		}
	}

	public void movePlayerHelper(int steps, final int fPos, int newPos) {
		Timer timer = new Timer(50, new ActionListener() {
			JLabel curPlayer = players[game.currentPlayerIndex()];
			String curName = game.currentPlayerName();
			int i = 0;
			int gap = 64;
			int pos = fPos + 1;

			@Override
			public void actionPerformed(ActionEvent event) {
				if (i < Math.abs(steps)) {
					if (Integer.signum(steps) > 0)
						moveForward();
					else
						moveBackward();
					pos += Integer.signum(steps);
					i++;
				} else {
					((Timer) event.getSource()).stop();
					sleep(100);
					Square curSquare = game.getCurrentSquare(pos - 1);
					if (curSquare instanceof FreezeSquare) {
						game.currentPlayer().setFreeze(true);
						addPlayerMoveMsg(curName + " found a TRAP !! FREEZE for 1 round.");
					}
					if (curSquare instanceof LadderSquare) {
						LadderSquare ladderSquare = (LadderSquare) curSquare;
						addPlayerMoveMsg(curName + " found a LADDER at " + (newPos + 1) + " !! GOTO -> "
								+ (ladderSquare.goTo() + 1));
						movePlayer(ladderSquare.goTo() - newPos);
						game.currentPlayerMoveSpecial(ladderSquare.goTo() - newPos);
					} else if (curSquare instanceof SnakeSquare) {
						SnakeSquare snakeSquare = (SnakeSquare) curSquare;
						addPlayerMoveMsg(curName + " found a SNAKE at " + (newPos + 1) + " !! BACKTO -> "
								+ (snakeSquare.goTo() + 1));
						movePlayer(snakeSquare.goTo() - newPos);
						game.currentPlayerMoveSpecial(snakeSquare.goTo() - newPos);
					} else if (newPos >= boardSize) {
						// Some player win
						if (pos - 1 == boardSize)
							return;
						addPlayerMoveMsg(
								curName + " roll a die exceed the goal MOVE BACK for -> " + (newPos - (boardSize - 1)));
						movePlayerHelper((boardSize - 1) - newPos, boardSize - 1, 2 * (boardSize - 1) - newPos);
					} else {
						addPlayerMoveMsg("----------------------------------------");
						rollButton.setEnabled(true);
						game.switchPlayer();
					}
					// Replay
					if (game.isReplayMode() && !(curSquare instanceof LadderSquare)
							&& !(curSquare instanceof SnakeSquare) && newPos < boardSize) {
						rollButton.setEnabled(false);
						List<Rolled> histories = game.getHistories();
						if (historiesIndex < histories.size())
							replay(histories.get(historiesIndex++));
						sleep(150);
					}
				}
			}

			public void moveForward() {
				if (pos % 10 == 0) {
					curPlayer.setLocation(curPlayer.getX(), curPlayer.getY() - gap);
				} else if ((pos / 10) % 2 == 0) {
					curPlayer.setLocation(curPlayer.getX() + gap, curPlayer.getY());
				} else {
					curPlayer.setLocation(curPlayer.getX() - gap, curPlayer.getY());
				}
			}

			public void moveBackward() {
				if (pos % 10 == 1) {
					curPlayer.setLocation(curPlayer.getX(), curPlayer.getY() + gap);
				} else if (((pos - 1) / 10) % 2 == 0) {
					curPlayer.setLocation(curPlayer.getX() - gap, curPlayer.getY());
				} else {
					curPlayer.setLocation(curPlayer.getX() + gap, curPlayer.getY());
				}
			}
		});
		timer.start();
	}

	public void sleep(int mSecs) {
		try {
			Thread.sleep(mSecs);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void addPlayerMoveMsg(String msg) {
		textArea.append(msg + "\n");
	}

}
