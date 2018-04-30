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
	private static final int FRAME_HIGHT = 840;
	private static JFrame frame;

	JPanel p = new JPanel();
	JPanel panel;
	JButton p2, p3, p4;

	int player = 0;

	public IndexUI() {
		intialize();
	}

	private void intialize() {
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					BufferedImage img = ImageIO.read(this.getClass().getResource("/res/index.png"));
					g.drawImage(img, 0, 0, 700, 840, null);
				} catch (IOException e) {

				}
			}
		};
		panel.setBounds(0, 0, 700, 840);
		panel.setLayout(null);

		ImageIcon img = new ImageIcon(getClass().getResource("/res/2player.png"));
		p2 = new JButton(img);
		p2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player = 2;
				setBoard();
				System.out.println(player);
			}
		});
		p2.setBounds(178, 390, 344, 110);
		panel.add(p2);

		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/3player.png"));
		p3 = new JButton(img2);
		p3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player = 3;
				setBoard();
				System.out.println(player);
			}
		});
		p3.setBounds(178, 530, 344, 110);
		panel.add(p3);

		ImageIcon img3 = new ImageIcon(getClass().getResource("/res/4player.png"));
		p4 = new JButton(img3);
		p4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				player = 4;
				setBoard();
				System.out.println(player);
			}
		});
		p4.setBounds(178, 670, 344, 110);
		panel.add(p4);
		p.add(panel);
	}

	public JPanel getPanel() {
		return panel;
	}

	public int getPlayer() {
		return player;
	}

	private static void init(JPanel panel) {
		System.out.println("start");
		frame = new JFrame("Snake and Ladder");
		frame.getContentPane().add(panel);
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void run() {
		intialize();
		init(getPanel());
	}

	public void setBoard() {
		BoardUI board = new BoardUI(getPlayer());
		init(board.getPanel());
	}

}
