package online;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import replay.Rolled;
import snakeandladder.Die;
import ui.ServerUI;

public class GameServer extends Observable {

	private Server gameServer;
	private List<GameRoom> gameList;
	private List<Connection> clientConnections;
	private List<SendData> rollHistory = new ArrayList<>();
	private int roomCount;
	private Die die;

	public GameServer(ServerUI ui) throws IOException {
		gameList = new ArrayList<>();
		gameServer = new Server();
		die = new Die();
		roomCount = 1;
		clientConnections = new ArrayList<Connection>();
		gameServer.bind(54333);
		gameServer.addListener(new GameServerListener());
		gameServer.getKryo().register(SendData.class);
		gameServer.start();
		addObserver(ui);
		setChanged();
		notifyObservers("Server Start");

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
			setChanged();
			notifyObservers("Client Disconnected");
		}

		@Override
		public void received(Connection connection, Object o) {
			super.received(connection, o);
			if(o instanceof SendData) {
				SendData receive = (SendData)o;
				if(receive.status.equals("Connecting")){
					System.out.println(receive.playerName + " Connected to RoomID: " + receive.roomId);
					setChanged();
					notifyObservers(receive.playerName + " Connected to RoomID: " + receive.roomId);
					GameRoom game = findRoomById(receive.roomId);
					game.addName(receive.playerName);
					if(game.isFull()) {
						for (Connection playerConnection : game.getPlayerConnection()) {
							SendData data = new SendData();
							data.status = "Ready";
							playerConnection.sendTCP(data);
						}
					} 
				}
				if(receive.status.equals("RequestName")) {
					GameRoom game = findRoomById(receive.roomId);
					for(String name : game.getNameList()) {
						SendData data = new SendData();
						data.playerName = name;
						data.status = "SendName";
						connection.sendTCP(data);
					}
					SendData playRequest = new SendData();
					playRequest.status = "Play";
					connection.sendTCP(playRequest);
				}
				if(receive.status.equals("Roll")) {
					GameRoom game = findRoomById(receive.roomId);
					SendData data = new SendData();
					die.roll();
					data.rolled = die.getFace();
					data.status = "SendRolled";
					for(Connection con :game.getPlayerConnection()) {
						con.sendTCP(data);
					}
				}
			}
		}

	}

	public GameRoom findAvailableRoom() {
		if(gameList.size()==0) {
			System.out.println("No Room Available! Create New One");
			setChanged();
			notifyObservers("No Room Available! Create New One");
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
		setChanged();
		notifyObservers("Do not find Room with ID = "+id);
		return null;
	}

}
