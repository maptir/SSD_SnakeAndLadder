package online;

import java.io.IOException;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import ui.BoardUI;

public class PlayerClient {
	
	private BoardUI board;
	private Client client;

	PlayerClient() throws IOException {
		
		BoardUI board = new BoardUI(4);
		board.run();
		
		client = new Client();
		client.getKryo().register(SendData.class);
		client.addListener(new PlayerClientListener());
		client.start();
		client.connect(5000, "127.0.0.1", 54333);
		
		this.sendMessage();
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
				System.out.println(receive.PlayerName);
			}
		}

	}
	
	public void sendMessage() {
		SendData data = new SendData();
		data.PlayerName = "Yo";
		data.currentPos = 0;
		data.rolled = 0;
		client.sendTCP(data);
		System.out.println("Message Sent");
	}
	 
	public static void main(String[] args) throws IOException, InterruptedException {
		PlayerClient playerClient = new PlayerClient();
	}
	
}

