package com.demo.kudo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.User;
import com.demo.kudo.models.RoomAuthenticationRequest;
import com.demo.kudo.service.RoomService;

@RestController
@RequestMapping("/api")
public class RoomController {

	@Autowired
	private RoomService roomService;
	
	@GetMapping("/rooms")
	public List<Room> getRooms() {
		
		return roomService.getRooms();
	}
	
	@GetMapping("/rooms/{roomId}")
	public Room getRoom(@PathVariable int roomId) {
		
		Room room = roomService.getRoom(roomId);
		
		if(room == null) {
			throw new RoomNotFoundException("Room ID not found - " + roomId);
		}
		
		return room;
	}
	
	@PostMapping("/rooms")
	public ResponseEntity<Room> addRoom(@RequestBody Room room) {
		try {
			Room roomToInsert = roomService.getRoom(room.getRoomname());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch(EmptyResultDataAccessException e) {
			room.setId(0);
			return ResponseEntity.ok(roomService.saveRoom(room));
		}
		

	}

	@PutMapping("/rooms")
	public Room updateRoom(@RequestBody Room room) {
		
		return roomService.saveRoom(room);

	}
	
	@DeleteMapping("/rooms/{roomId}")
	public String deleteRoom(@PathVariable int roomId) {
		
		Room room = roomService.getRoom(roomId);
		if(room == null) {
			throw new RoomNotFoundException("Room ID not found - " + roomId);
		}
		
		roomService.deleteRoom(roomId);
		
		return "Deleted room "+roomId;
	}
	
	@PutMapping("/rooms/roomname/{userId}")
	public ResponseEntity<Room> updateUserInRoom(@PathVariable int userId, @RequestBody RoomAuthenticationRequest request) {
		Room room;
		try {
			room = roomService.getRoom(request.getRoomname());
		}
		catch(EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		if(!room.getPassword().equals(request.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		for(User user : room.getUsers()) {
			if(user.getId() == userId) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}
		//User user = userService.saveUserWithRoom(userId, room);
		Room roomToInsert = roomService.saveRoomWithUser(userId, room);
		return ResponseEntity.ok(roomToInsert);
	}
}