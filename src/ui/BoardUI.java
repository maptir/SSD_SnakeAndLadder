package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BoardUI {
	private JPanel board;
	private JButton rollButton;
	private JLabel dice;
	private JTextField textfield;
	
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
		rollButton.setBounds(526,697,144,111);
		board.add(rollButton);
		
		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/dice1.png"));
		dice = new JLabel(img2);
		dice.setBounds(395,697,111,111);
		board.add(dice);
		
		textfield = new JTextField();
		textfield.setBounds(30,697,350,111);
		board.add(textfield);
		
	}
	
	public JPanel getPanel() {
		return board;
	}

}
