package online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import replay.Rolled;

public class GameServer {
	
	private Server gameServer;
	private List<Connection> clientConnections;
	private List<SendData> rollHistory = new ArrayList<>();
	
	public GameServer() throws IOException {
		gameServer = new Server();
		
		clientConnections = new ArrayList<Connection>();
		gameServer.bind(54333);
		gameServer.addListener(new GameServerListener());
		
		gameServer.start();

		System.out.println("Server Start");
	}
	
	class GameServerListener extends Listener {
		
		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			clientConnections.add(connection);
			System.out.println("Player Connected");
		}
		
		@Override
		public void disconnected(Connection connection) {
			super.disconnected(connection);
			clientConnections.remove(connection);
			System.out.println("Client Disconnected");
		}
		
		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if(o instanceof SendData) {
				SendData receive = (SendData)o;
				rollHistory.add(receive);
				for(Connection c: clientConnections) {
					c.sendTCP(receive);
				}
			}
		}
		
	}
	
	public static void main(String[] args) throws IOException {
		GameServer server = new GameServer();
	}
	
}
