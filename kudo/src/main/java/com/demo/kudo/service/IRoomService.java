package com.demo.kudo.service;

import java.util.List;

import com.demo.kudo.entity.Room;

public interface IRoomService {

	public List<Room> getRooms();

	public Room saveRoom(Room theRoom);

	public Room getRoom(int theId);
	
	public Room getRoom(String roomname);

	public void deleteRoom(int theId);
}
