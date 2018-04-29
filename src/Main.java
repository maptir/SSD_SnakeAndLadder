import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

import snakeandladder.Game;

public class Main {
	private static final int FRAME_WIDTH = 700;
	private static final int FRAME_HIGHT = 840;
	private static JFrame frame;

	private static void initialize(JPanel panel) {
		System.out.println("start");
		frame = new JFrame("Snake and Ladder");
		frame.getContentPane().add(panel);
		frame.setSize(new Dimension(FRAME_WIDTH, FRAME_HIGHT));
		// frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public static void main(String[] args) {
		initialize(new ui.BoardUI().getPanel());
	}
}
