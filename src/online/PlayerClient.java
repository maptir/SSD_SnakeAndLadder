package online;

import java.io.IOException;
import java.util.Observable;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ui.BoardUI;
import ui.MultiplayerBoard;
import ui.MultiplayerUI;

public class PlayerClient extends Observable {

	private BoardUI board;
	private Client client;
	private String PlayerName;
	private String status;
	private int rolled;
	private String roomId;
	private OnlineGame game;
	private String ip;
	private int binding;

	public PlayerClient(String ip,int binding) throws IOException {
		game = new OnlineGame();
		this.ip = ip;
		this.binding = binding;
		client = new Client();
		client.getKryo().register(SendData.class);
		client.addListener(new PlayerClientListener());
		client.start();
		client.connect(5000, ip, binding);
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
			if (o instanceof SendData) {
				SendData receive = (SendData) o;
				if (receive.status.equals("Ready")) {
					setStatus("RequestName");
					sendMessage();
				}
				if (receive.status.equals("sendRoomId")) {
					roomId = receive.roomId;
				}
				if (receive.status.equals("SendName")) {
					System.out.println("Receive send Name");
					game.addPlayer(receive.playerName);
				}
				if (receive.status.equals("Play")) {
					setStatus("Play");
					startGame();
				}
				if (receive.status.equals("SendRolled")) {
					rolled = receive.rolled;
					setChanged();
					notifyObservers();
				}
			}
		}
		
	}

	private void startGame() {
		System.out.println("Playing!!!!!");
		MultiplayerBoard board = new MultiplayerBoard(game, this);
		board.run();
		setChanged();
		notifyObservers();
		this.addObserver(board);
	}

	public void sendMessage() {
		SendData data = new SendData();
		data.roomId = roomId;
		data.playerName = this.PlayerName;
		data.status = this.status;
		data.rolled = rolled;
		client.sendTCP(data);
		System.out.println("Message Sent(RoomID,Name,Status,Rolled) : " + roomId + "," + PlayerName + "," + status + ","
				+ rolled + "\n");
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

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public int getRolled() {
		return rolled;
	}

	public String getIp() {
		return ip;
	}

	public int getBinding() {
		return binding;
	}
	
	

}
