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
import javax.swing.JPanel;

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

		startButtons = new JButton[3];
		for (int i = 0; i < startButtons.length; i++) {
			img = new ImageIcon(getClass().getResource("/res/" + (i + 2) + "player.png"));
			startButtons[i] = new JButton(img);
			startButtons[i].setBorderPainted(false);
			startButtons[i].addActionListener(createBoard(i + 2));
			startButtons[i].setBounds(178, 390 + (140 * i), 344, 110);
			this.add(startButtons[i]);
		}
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
