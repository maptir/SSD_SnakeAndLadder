package ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import online.PlayerClient;

public class IndexUI extends JPanel {

	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HIEGHT = 840;
	private static JFrame frame;

	private JPanel p = new JPanel();
	private JButton[] startButtons;
	private ImageIcon img;

	public IndexUI() {
		frame = new JFrame("Snake and Ladder");
		frame.getContentPane().add(this);
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HIEGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		intialize();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			BufferedImage img = ImageIO.read(this.getClass().getResource("/res/index.png"));
			g.drawImage(img, 0, 0, FRAME_WIDTH, FRAME_HIEGHT, null);
		} catch (IOException e) {

		}
	}

	public void run() {
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void intialize() {
		this.setBounds(0, 0, FRAME_WIDTH, FRAME_HIEGHT);
		this.setLayout(null);

		startButtons = new JButton[4];
		for (int i = 0; i < startButtons.length; i++) {
			img = new ImageIcon(getClass().getResource("/res/" + (i + 2) + "player.png"));
			img.setImage(img.getImage().getScaledInstance(287, 92, Image.SCALE_SMOOTH));
			startButtons[i] = new JButton(img);
			startButtons[i].setBorderPainted(false);
			if(i==3) {
				startButtons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						PlayerClient playerClient;
						try {
							playerClient = new PlayerClient();
							MultiplayerUI ui = new MultiplayerUI(playerClient);
							playerClient.addObserver(ui);
							frame.dispose();
						} catch (IOException e1) {
							System.out.println("Not found Server!!!!!");
						}
					}
				});
			}
			else startButtons[i].addActionListener(createBoard(i + 2));
			startButtons[i].setBounds(0, 0, 287, 92);
			this.add(startButtons[i]);
		}
		int x = 50;
		int y = 470;
		int dx = 320;
		int dy = 140;
		startButtons[0].setLocation(x, y);
		startButtons[1].setLocation(x + dx, y);
		startButtons[2].setLocation(x, y + dy);
		startButtons[3].setLocation(x + dx, y + dy);

	}

	public ActionListener createBoard(int numPlayer) {
		return new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				BoardUI board = new BoardUI(numPlayer);
				frame.dispose();
				board.run();
			}
		};
	}

}
