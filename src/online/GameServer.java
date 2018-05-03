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
	private List<GameRoom> gameList;
	private List<Connection> clientConnections;
	private List<SendData> rollHistory = new ArrayList<>();
	private int roomCount;

	public GameServer() throws IOException {
		gameList = new ArrayList<>();
		gameServer = new Server();
		roomCount = 1;
		clientConnections = new ArrayList<Connection>();
		gameServer.bind(54333);
		gameServer.addListener(new GameServerListener());
		gameServer.getKryo().register(SendData.class);
		gameServer.start();

		System.out.println("Server Start");
	}

	class GameServerListener extends Listener {

		@Override
		public void connected(Connection connection) {
			super.connected(connection);
			GameRoom game = findAvailableRoom();
			game.addConnection(connection);				
			game.setRoomID(roomCount+"");
			SendData data = new SendData();
			data.roomId = game.getRoomID();
			data.status = "sendRoomId";
			connection.sendTCP(data);
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
				if(receive.status.equals("Connecting")){
					System.out.println(receive.playerName + " Connected to RoomID:" + receive.roomId);
					GameRoom game = findRoomById(receive.roomId);
					if(game.isFull()) {
						for(Connection playerConnection : game.getPlayerConnection()) {
							SendData data = new SendData();
							data.status = "Ready";
							playerConnection.sendTCP(data);
						}
					}
				}
			}
		}

	}

	public GameRoom findAvailableRoom() {
		if(gameList.size()==0) {
			System.out.println("No Room Available! Create New One");
			GameRoom room = new GameRoom();
			gameList.add(room);
			return room;
		}
		else {
			for(GameRoom room : gameList) {
				if(room.isFull() == false) {
					return room;
				}
			}
			GameRoom room = new GameRoom();
			roomCount++;
			room.setRoomID(roomCount + "");
			gameList.add(room);
			return room;
		}
	}

	public GameRoom findRoomById(String id) {
		for(GameRoom game : gameList) {
			if(game.getRoomID().equals(id))
				return game;
		}
		System.out.println("Do not find Room with ID = "+id);
		return null;
	}

	public static void main(String[] args) throws IOException {
		GameServer server = new GameServer();
	}

}
