package ui;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class IndexUI {
	
	JPanel panel;
	JButton p2,p3,p4;
	
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
		p2.setBounds(178,390,344,110);
		panel.add(p2);
		
		ImageIcon img2 = new ImageIcon(getClass().getResource("/res/3player.png"));
		p3 = new JButton(img2);
		p3.setBounds(178,530,344,110);
		panel.add(p3);
		
		ImageIcon img3 = new ImageIcon(getClass().getResource("/res/4player.png"));
		p4 = new JButton(img3);
		p4.setBounds(178,670,344,110);
		panel.add(p4);
	}
	public JPanel getPanel() {
		return panel;
	}

}
