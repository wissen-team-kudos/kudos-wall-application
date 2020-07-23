package com.demo.kudo.roomtest;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.kudo.controller.RoomController;
import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.User;
import com.demo.kudo.models.RoomAuthenticationRequest;
import com.demo.kudo.service.RoomService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = RoomController.class)
public class RoomControllerTest {

	@Autowired
	private MockMvc mvc;
	
	@MockBean
	private RoomService roomService;
	
	@Autowired
	private ObjectMapper objectMapper; 
	
	@Test
	public void shouldFetchAllRooms() throws Exception{
		
	   List<Room> roomList = new ArrayList<>();
	   roomList.add(new Room(1, "room1", "room1"));
	   roomList.add(new Room(2, "room2", "room2"));
	   roomList.add(new Room(3, "room3", "room3"));
	   
		given(roomService.getRooms()).willReturn(roomList);
		
		mvc.perform(
					get("/api/rooms/")
					.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(roomList.size())));				
	}
	
	@Test
	public void shouldFetchRoomWithId() throws Exception{

		int roomId = 2;
		Room room = new Room(roomId,"room1","room1");
		
		given(roomService.getRoom(roomId)).willReturn(room);
		
		mvc.perform(
					get("/api/rooms/"+roomId)
					.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(room.getId())))
				.andExpect(jsonPath("$.roomname", is(room.getRoomname())))
				.andExpect(jsonPath("$.password", is(room.getPassword())));	
	}
	
	@Test
	public void shouldReturn404OnFetchRoomWithId() throws Exception{

		int roomId = 2;
		
		// Room does not exist
		given(roomService.getRoom(roomId)).willReturn(null);
		
		mvc.perform(
					get("/api/rooms/"+roomId)
					.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldCreateNewRoom() throws Exception{

		Room newRoom = new Room();
		newRoom.setRoomname("room4");
		newRoom.setPassword("room4");
		
		Room returnRoom = new Room(4,"room4","room4");
		
		given(roomService.getRoom(newRoom.getRoomname())).willThrow(EmptyResultDataAccessException.class);
		given(roomService.saveRoom(any(Room.class))).willReturn(returnRoom);
		
		mvc.perform(
					post("/api/rooms/")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newRoom)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(returnRoom.getId())))
				.andExpect(jsonPath("$.roomname", is(returnRoom.getRoomname())))
				.andExpect(jsonPath("$.password", is(returnRoom.getPassword())));
	}

	@Test
	public void shouldReturn400OnCreateNewRoom() throws Exception{

		Room newRoom = new Room();
		newRoom.setRoomname("room4");
		newRoom.setPassword("room4");
		
		Room returnRoom = new Room(4,"room4","room4");
		
		// Room already exists
		given(roomService.getRoom(newRoom.getRoomname())).willReturn(returnRoom);
		
		mvc.perform(
					post("/api/rooms/")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(newRoom)))
				.andExpect(status().isBadRequest());
	}
	
	@Test
	public void shouldUpdateRoom() throws Exception{

		Room theRoom = new Room(1,"room1","room1");
		
		Room theRoomUpdated = new Room(1,"room1","pass1");
		
		given(roomService.saveRoom(any(Room.class))).willReturn(theRoomUpdated);
		
		mvc.perform(
					put("/api/rooms/")
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(theRoom)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(theRoomUpdated.getId())))
				.andExpect(jsonPath("$.roomname", is(theRoomUpdated.getRoomname())))
				.andExpect(jsonPath("$.password", is(theRoomUpdated.getPassword())));		

	}
	
	@Test
	public void shouldDeleteRoomWithId() throws Exception{
		
		int roomId = 2;
		Room room = new Room(roomId,"room1","room1");
		
		given(roomService.getRoom(roomId)).willReturn(room);

		doNothing().when(roomService).deleteRoom(roomId);

		
		mvc.perform(
					delete("/api/rooms/"+roomId)
					.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", is("Deleted room " + roomId)));	
	}
	
	@Test
	public void shouldReturn404OnDeleteRoomWithId() throws Exception{

		int roomId = 2;
		
		// Room does not exist
		given(roomService.getRoom(roomId)).willReturn(null);
		
		mvc.perform(
					delete("/api/rooms/"+roomId)
					.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldUpdateUserInRoom() throws Exception{

		int userId = 1;
		User user = new User(1,"user1","pass1");
		
		RoomAuthenticationRequest request = new RoomAuthenticationRequest("room1", "pass1");
		
		Room theRoom = new Room(1,"room1","pass1");
		theRoom.addUser(new User(2,"user2","pass2"));
		theRoom.addUser(new User(3,"user3","pass3"));
	
		Room theRoomUpdated = new Room(1,"room1","pass1");
		theRoom.addUser(new User(2,"user2","pass2"));
		theRoom.addUser(new User(3,"user3","pass3"));;
		theRoomUpdated.addUser(user);
		
		given(roomService.getRoom(request.getRoomname())).willReturn(theRoom);
		given(roomService.saveRoomWithUser(userId, theRoom)).willReturn(theRoomUpdated);
		
		mvc.perform(
					put("/api/rooms/roomname/"+userId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(theRoomUpdated.getId())))
				.andExpect(jsonPath("$.roomname", is(theRoomUpdated.getRoomname())))
				.andExpect(jsonPath("$.password", is(theRoomUpdated.getPassword())))
				.andExpect(jsonPath("$.users[0].username", is(user.getUsername())));		
	}
	
	@Test
	public void shouldReturn404UpdateUserInRoom() throws Exception{

		int userId = 1;
		User user = new User(1,"user1","pass1");
		
		// Room does not exist
		RoomAuthenticationRequest request = new RoomAuthenticationRequest("room1", "pass1");
		
		given(roomService.getRoom(request.getRoomname())).willThrow(EmptyResultDataAccessException.class);
		
		mvc.perform(
					put("/api/rooms/roomname/"+userId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isNotFound());		
	}
	
	@Test
	public void shouldReturn401UpdateUserInRoom() throws Exception{

		int userId = 1;
		User user = new User(1,"user1","pass1");
		
		Room theRoom = new Room(1,"room1","pass1");

		// Wrong credentials
		RoomAuthenticationRequest request = new RoomAuthenticationRequest("room1", "room1");
		
		given(roomService.getRoom(request.getRoomname())).willReturn(theRoom);
		
		mvc.perform(
					put("/api/rooms/roomname/"+userId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isUnauthorized());		
	}
	
	@Test
	public void shouldReturn400UpdateUserInRoom() throws Exception{

		int userId = 1;
		User user = new User(1,"user1","pass1");
		
		Room theRoom = new Room(1,"room1","pass1"); 
		theRoom.addUser(user);	// User already in Room
		theRoom.addUser(new User(2,"user2","pass2"));
		theRoom.addUser(new User(3,"user3","pass3"));


		RoomAuthenticationRequest request = new RoomAuthenticationRequest("room1", "pass1");
		
		given(roomService.getRoom(request.getRoomname())).willReturn(theRoom);

		mvc.perform(
					put("/api/rooms/roomname/"+userId)
					.accept(MediaType.APPLICATION_JSON)
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectMapper.writeValueAsString(request)))
				.andDo(print())
				.andExpect(status().isBadRequest());		
	}
}
