package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import online.PlayerClient;

public class MultiplayerUI extends JFrame implements Observer {

	private PlayerClient playerClient;

	private JLabel label;
	private JPanel panel;
	private JTextField textfield;
	private JButton button;

	public MultiplayerUI(PlayerClient playerClient) {
		super("Multiplayer");
		this.playerClient = playerClient;
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initLayout();
		pack();
		setVisible(true);
	}
	
	private void initLayout() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(200, 100));
		panel.setLayout(new BorderLayout());
		label = new JLabel("Set Player Name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		textfield = new JTextField("Enter Name Here");
		textfield.setHorizontalAlignment(JTextField.CENTER);
		button = new JButton("Find Game");
		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				playerClient.setPlayerName(textfield.getText());
				playerClient.setStatus("connecting");
				close();
			}
		});
		panel.add(label, BorderLayout.NORTH);
		panel.add(textfield, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		this.add(panel);
	}

	private void close() {
		this.dispose();
	}
	
	private void change() {
		panel.removeAll();
		label.setText("waiting........");
		panel.add(label);
	}

	@Override
	public void update(Observable o, Object arg) {
		close();
	}

}
