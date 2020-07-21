package com.demo.kudo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.kudo.dao.RoomDAO;
import com.demo.kudo.dao.UserDAO;
import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.User;

@Service
public class RoomService implements IRoomService {

	@Autowired
	private RoomDAO roomDAO;
	
	@Override
	@Transactional
	public List<Room> getRooms() {
		
		return roomDAO.getRooms();
	}

	@Override
	@Transactional
	public Room getRoom(int theId) {
		
		return roomDAO.getRoom(theId);
	}
	
	@Override
	@Transactional
	public Room saveRoom(Room theRoom) {
		
		return roomDAO.saveRoom(theRoom);
	}

	@Override
	@Transactional
	public void deleteRoom(int theId) {

		roomDAO.deleteRoom(theId);
	}

	@Override
	@Transactional
	public Room getRoom(String roomname) {
		return roomDAO.getRoom(roomname);
	}
	
	@Transactional
	public Room saveRoomWithUser(int userId,Room room) {
		
		 return roomDAO.saveRoomWithUser(userId,room);	
	}

}
