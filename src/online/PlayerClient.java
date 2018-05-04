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
	private String roomId;

	public PlayerClient() throws IOException {
		client = new Client();
		client.getKryo().register(SendData.class);
		client.addListener(new PlayerClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
		currentPos = 0;
		rolled = 0;
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
					System.out.println("Play!!!!");
					setChanged();
					notifyObservers();
				}
				if(receive.status.equals("sendRoomId")) {
					roomId = receive.roomId;
				}
			}
		}

	}
	
	public void sendMessage() {
		SendData data = new SendData();
		data.roomId = roomId;
		data.playerName = this.PlayerName;
		data.status = this.status;
		data.currentPos = currentPos;
		client.sendTCP(data);
		System.out.println("Message Sent(RoomID,Name,Status,CurrentPos) : " 
		+ roomId +"," + PlayerName +"," + status + "," + currentPos);
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	
	
}

