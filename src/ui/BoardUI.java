package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class BoardUI {
	private JPanel board;
	private JButton rollButton;
	
	public BoardUI() {
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
		rollButton.setBounds(269,697,144,111);
		board.add(rollButton);
		
	}
	
	public JPanel getPanel() {
		return board;
	}

}
