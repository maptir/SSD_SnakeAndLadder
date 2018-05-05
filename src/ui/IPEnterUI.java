package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

public class IPEnterUI extends JFrame implements Observer {

	private PlayerClient playerClient;

	private JLabel label;
	private JPanel panel;
	private JTextField textfield;
	private JButton button;
	private JTextField textfield2;


	public IPEnterUI() {
		super("IPConfig");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initLayout();
		pack();
		setVisible(true);
	}

	private void initLayout() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 150));
		panel.setLayout(new BorderLayout());
		label = new JLabel("Set IP");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		textfield = new JTextField("Enter IP Here");
		textfield.setHorizontalAlignment(JTextField.CENTER);
		textfield2 = new JTextField("Enter Port Here");
		textfield2.setHorizontalAlignment(JTextField.CENTER);
		JPanel panelText = new JPanel(new BorderLayout());
		panelText.add(textfield,BorderLayout.CENTER);
		panelText.add(textfield2,BorderLayout.SOUTH);
		button = new JButton("Join IP");
		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					playerClient = new PlayerClient(textfield.getText(),Integer.parseInt(textfield2.getText()));
					MultiplayerUI ui = new MultiplayerUI(playerClient);
					playerClient.addObserver(ui);
					close();
				} catch (IOException e1) {
					textfield.setText("Wrong IP");
				}
			}
		});
		panel.add(label, BorderLayout.NORTH);
		panel.add(panelText, BorderLayout.CENTER);
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
			this.repaint();
		}

		@Override
		public void update(Observable o, Object arg) {
			close();
		}

	}
