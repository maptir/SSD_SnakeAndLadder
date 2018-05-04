package online;

import java.util.ArrayList;
import java.util.List;

import com.esotericsoftware.kryonet.Connection;

import snakeandladder.Game;

public class GameRoom {

	private String roomID;
//	private OnlineGame game;
	private List<Connection> playerConnections;
	
	public GameRoom() {
		playerConnections = new ArrayList<>();
	}

	public String getRoomID() {
		return roomID;
	}
	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}
//	public OnlineGame getGame() {
//		return game;
//	}
//	public void setGame(OnlineGame game) {
//		this.game = game;
//	}
	public List<Connection> getPlayerConnection() {
		return playerConnections;
	}
	public void setPlayer(List<Connection> player) {
		this.playerConnections = player;
	}
	public boolean isFull() {
		return playerConnections.size() == 4;
	}
	public void addConnection(Connection connection) {
		if(!isFull())
			playerConnections.add(connection);
		else
			System.out.println("This room is already full!");
	}



}
