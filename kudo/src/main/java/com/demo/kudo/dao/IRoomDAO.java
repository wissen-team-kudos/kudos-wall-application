package com.demo.kudo.dao;

import java.util.List;

import com.demo.kudo.entity.Room;


public interface IRoomDAO {

	public List<Room> getRooms();

	public Room saveRoom(Room theRoom);

	public Room getRoom(int theId);
	
	public Room getRoom(String roomname);

	public void deleteRoom(int theId);
}
