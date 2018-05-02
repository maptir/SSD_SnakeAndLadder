package online;

import java.io.IOException;
import java.util.Observable;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ui.BoardUI;
import ui.MultiplayerUI;

public class PlayerClient extends Observable {
	
	private BoardUI board;
	private Client client;
	private String PlayerName;
	private String status;
	private int currentPos;
	private int rolled;

	public PlayerClient() throws IOException {
		client = new Client();
		client.getKryo().register(SendData.class);
		client.addListener(new PlayerClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
	}
	
	class PlayerClientListener extends Listener {
		
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			System.out.println("Connected to Server");
		}
		
		@Override
		public void received(Connection connection, Object o) {
			if(o instanceof SendData) {
				SendData receive = (SendData)o;
				if(receive.status.equals("Ready")) {
					BoardUI board = new BoardUI(4);
					board.run();
					setStatus("Play");
				}
			}
		}

	}
	
	public void sendMessage() {
		SendData data = new SendData();
		data.playerName = this.PlayerName;
		data.status = this.status;
		data.currentPos = 0;
		data.rolled = 0;
		client.sendTCP(data);
		System.out.println("Message Sent");
	}
	 
	public String getPlayerName() {
		return PlayerName;
	}

	public void setPlayerName(String playerName) {
		PlayerName = playerName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		PlayerClient playerClient = new PlayerClient();
		MultiplayerUI ui = new MultiplayerUI(playerClient);
		playerClient.addObserver(ui);

	}
	
}

