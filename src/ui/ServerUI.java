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
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import online.GameServer;
import online.PlayerClient;

public class ServerUI extends JFrame implements Observer {

	private GameServer server;

	private JLabel label;
	private JPanel panel;
	private JTextArea textarea;
	private JButton button;
	private JScrollPane scroll;
	
	public ServerUI() throws IOException {
		super("Snake & Ladder Server");
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		initLayout();
		pack();
		setVisible(true);
	}
	
	private void initLayout() {
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(300, 500));
		panel.setLayout(new BorderLayout());
		label = new JLabel("Set Player Name");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		textarea = new JTextArea("-------------Server Status------------\n",300,300);
		scroll = new JScrollPane(textarea);
		button = new JButton("Open");
		button.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					openServer();
					button.setEnabled(false);
				} catch (IOException e1) {
					System.out.println("Can't open server!!!!");
				}
			}
		});
		panel.add(label, BorderLayout.NORTH);
		panel.add(scroll, BorderLayout.CENTER);
		panel.add(button, BorderLayout.SOUTH);
		this.add(panel);
	}
	
	private void openServer() throws IOException {
		this.server = new GameServer(this);
	}


	private void close() {
		this.dispose();
	}

	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String) {
			String s = (String)arg;
			textarea.append(s+"\n");
		}
	}
	public static void main(String[] args) throws IOException {
		ServerUI ui = new ServerUI();
	}

}
