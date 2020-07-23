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

import com.demo.kudo.entity.*;
import com.demo.kudo.entity.User;
import com.demo.kudo.models.RoomAuthenticationRequest;
import com.demo.kudo.service.RoomService;
import com.demo.kudo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoomService roomService;
	
	@GetMapping("/users")
	public List<User> getUsers() {
		
		return userService.getUsers();
	}
	
	@GetMapping("/users/{userId}")
	public User getUser(@PathVariable int userId) {
		
		User user = userService.getUser(userId);
		
		if(user == null) {
			throw new UserNotFoundException("User id not found - " + userId);
		}
		
		return user;
	}
	@GetMapping("/users/userid/{userId}")
	public List<Kudos> getKudosOfUser(@PathVariable int userId) {
		
		User user = userService.getUser(userId);
		
		if(user == null) {
			throw new UserNotFoundException("User id not found - " + userId);
		}
		
		return userService.getKudosOfUser(userId);
	}
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		
		user.setId(0);
		return userService.saveUser(user);
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User user) {
				
		return userService.saveUser(user);
	}
	
	@PutMapping("/users/roomid/{userId}/{roomId}")
	public User updateRoomtoUser(@PathVariable int userId, @PathVariable int roomId) {
				
		Room room = roomService.getRoom(roomId);
		if(room == null) {
			throw new RoomNotFoundException("Room ID not found - " + roomId);
		}
		
		User user=userService.saveUserWithRoom(userId,room);
		
		return user;
	}
	
	@PutMapping("/users/roomname/{userId}")
	public ResponseEntity<User> updateRoomtoUser(@PathVariable int userId, @RequestBody RoomAuthenticationRequest request) {
		Room room;
		try {
			System.out.println(request);
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
		User user = userService.saveUserWithRoom(userId, room);
		System.out.println(user);
		System.out.println(ResponseEntity.ok(user));
		return ResponseEntity.ok(user);
	}
	
	@DeleteMapping("/users/{userId}")
	public String deleteUser(@PathVariable int userId) {
		
		User user = userService.getUser(userId);
		if(user == null) {
			throw new UserNotFoundException("User ID not found - " + userId);
		}
		
		userService.deleteUser(userId);
		
		return "Deleted user "+userId;
	}
	
	@GetMapping("/users/username/{username}")
	public User getUser(@PathVariable String username) {
		User user = userService.getUser(username);
		if(user == null) {
			throw new UserNotFoundException("User not found - " + username);
		}
		
		return user;
	}
}