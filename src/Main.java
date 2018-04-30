import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ui.IndexUI;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException,
			IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
		IndexUI ui = new IndexUI();
		ui.run();
	}
}
