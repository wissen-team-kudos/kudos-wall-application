package com.demo.kudo.usertest;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.kudo.controller.UserController;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.User;
import com.demo.kudo.models.RoomAuthenticationRequest;
import com.demo.kudo.service.RoomService;
import com.demo.kudo.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
	 
	@Autowired
	private MockMvc mvc;
	
	@Autowired ObjectMapper objectMapper;
	
	@MockBean                           
    private UserService userService;
	
	@MockBean
	private RoomService roomService;
	
	/*
	 * Test Case: Get all the users successfully
	 * API: /api/users
	 */
	@Test
	public void shouldFetchAllUsers() throws Exception{

		List<User> userList;
		userList = new ArrayList<>();
	    userList.add(new User(1, "user1", "pass1"));
	    userList.add(new User(2, "user2", "pass2"));
	    userList.add(new User(3, "user3", "pass3"));
	
		given(userService.getUsers()).willReturn(userList);
		
		mvc.perform(
				get("http://localhost:8080/api/users/")
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(userList.size())));
				
	}
	
	@Test
	public void shouldFetchOneUserById() throws Exception{

		final int userId = 1;
		final User user = new User(userId, "user1", "pass1");
		
		given(userService.getUser(userId)).willReturn(user);
		
		mvc.perform(
				get("http://localhost:8080/api/users/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(userId)));
				
	}
	
	@Test
	public void shouldReturn404OnFetchOneUserById() throws Exception{

		final int userId = 1;
		
		given(userService.getUser(userId)).willReturn(null);
		
		mvc.perform(
				get("http://localhost:8080/api/users/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isNotFound());
				
	}
	
	@Test
	public void shouldFetchKudosOfUserById() throws Exception{

		final int userId = 1;
		final User user = new User(userId, "user1", "pass1");
		user.addRoom(new Room(1, "room1", "pass1"));
		
		List<Kudos> kudosList = new ArrayList<Kudos>();
		Kudos kudos = new Kudos();
		
		kudos.setId(1);
		kudos.setContent("This is random content");
		kudos.setAuthor(new User(1, "user1", "pass1"));
		kudos.addUser(new User(2, "user2", "pass2"));
		kudos.addUser(new User(3, "user3", "pass3"));
		kudos.addRoom(new Room(1, "room1", "pass1"));
		kudosList.add(kudos);
		
		kudos=new Kudos();
		kudos.setId(2);
		kudos.setContent("This is random content 2");
		kudos.setAuthor(new User(1, "user1", "pass1"));
		kudos.addUser(new User(2, "user2", "pass2"));
		kudos.addUser(new User(3, "user3", "pass3"));
		kudos.addRoom(new Room(1, "room1", "pass1"));
		kudosList.add(kudos);
		
		kudos=new Kudos();
		kudos.setId(3);
		kudos.setContent("This is random content 3");
		kudos.setAuthor(new User(2, "user2", "pass2"));
		kudos.addUser(new User(2, "user2", "pass2"));
		kudos.addUser(new User(5, "user5", "pass5"));
		kudos.addRoom(new Room(1, "room1", "pass1"));
		kudosList.add(kudos);
		
		user.setKudos(kudosList);
		
		given(userService.getUser(userId)).willReturn(user);
		given(userService.getKudosOfUser(userId)).willReturn(kudosList);
		
		mvc.perform(
				get("http://localhost:8080/api/users/userid/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(kudosList.size())))
				.andExpect(jsonPath("$[0].rooms[0].id", is(user.getRooms().get(0).getId())))
				.andExpect(jsonPath("$[1].rooms[0].id", is(user.getRooms().get(0).getId())))
				.andExpect(jsonPath("$[2].rooms[0].id", is(user.getRooms().get(0).getId())));
	}
	
	@Test
	public void shouldReturn404OnFetchKudosOfUserById() throws Exception{

		final int userId = 1;
		
		given(userService.getUser(userId)).willReturn(null);
		
		mvc.perform(
				get("http://localhost:8080/api/users/userid/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isNotFound());
				
	}
	
	@Test
	public void shouldFetchOneUserByUsername() throws Exception{
		final String username = "user3";
		User user = new User(3, "user3", "pass3");
		
		given(userService.getUser(username)).willReturn(user);
		
		mvc.perform(
				get("http://localhost:8080/api/users/username/{username}", username)
				.accept(MediaType.APPLICATION_JSON))
		//.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.username", is(username)));
	}
	
	
	@Test
	public void shouldReturn404OnFetchOneUserByUsername() throws Exception{
		final String username = "user10";
		
		given(userService.getUser(username)).willReturn(null);
		
		mvc.perform(
				get("http://localhost:8080/api/users/username/{username}", username)
				.accept(MediaType.APPLICATION_JSON))
		//.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldCreateNewUser() throws Exception{
		
		
		User user = new User(0, "user10", "pass10");
		User userToReturn = new User(11, "user10", "pass10");
		
		given(userService.saveUser(any(User.class))).willReturn(userToReturn);
		
		mvc.perform(
				post("/api/users")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		//.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(userToReturn.getId())))
		.andExpect(jsonPath("$.username", is(userToReturn.getUsername())))
		.andExpect(jsonPath("$.password", is(userToReturn.getPassword())));
	}
	
//	Need to add exception handler if time permits 	
//	@Test
//	public void shouldReturn400OnCreateNewUser() throws Exception{
//		
//		
//		User user = new User(0, "user10", null);
//		
//		
//		mvc.perform(
//				post("/api/users")
//				.accept(MediaType.APPLICATION_JSON)
//				.contentType(MediaType.APPLICATION_JSON)
//				.content(objectMapper.writeValueAsString(user)))
//		.andDo(print())
//		.andExpect(status().isBadRequest());
//	}
	
	@Test
	public void shouldDeleteUser() throws Exception{
		int userId = 1;
		User user = new User(1, "user1", "pass1");
		given(userService.getUser(userId)).willReturn(user);
		doNothing().when(userService).deleteUser(userId);
		
		mvc.perform(
				delete("/api/users/{userId}", userId))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("Deleted user " + userId)));
	}
	
	@Test
	public void shouldReturn404OnDeleteUser() throws Exception{
		int userId = 1;
		given(userService.getUser(userId)).willReturn(null);
		
		mvc.perform(
				delete("/api/users/{userId}", userId))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldUpdateUser() throws Exception{
		User user = new User(11, "user10", "pass10");
		User userToReturn = new User(11, "user11", "pass10");
		given(userService.saveUser(any(User.class))).willReturn(userToReturn);
		
		mvc.perform(
				put("/api/users")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(user)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(userToReturn.getId())))
		.andExpect(jsonPath("$.username", is(userToReturn.getUsername())))
		.andExpect(jsonPath("$.password", is(userToReturn.getPassword())));
	}
	
	@Test
	public void shouldUpdateRoomToUserById() throws Exception{
//		User user = new User(11, "user11", "pass11");
//		user.addRoom(new Room(1, "room1", "pass1"));
//		user.addRoom(new Room(4, "room4", "pass4"));
		
		int roomId = 3;
		Room roomToInsert = new Room(3, "room3", "pass3");
		
		int userId = 11;
		User updatedUser = new User(11, "user11", "pass11");
		updatedUser.addRoom(new Room(1, "room1", "pass1"));
		updatedUser.addRoom(new Room(4, "room4", "pass4"));
		updatedUser.addRoom(roomToInsert);
		
		given(roomService.getRoom(roomId)).willReturn(roomToInsert);
		given(userService.saveUserWithRoom(userId, roomToInsert)).willReturn(updatedUser);
		
		mvc.perform(
				put("/api/users/roomid/{userId}/{roomId}", userId, roomId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(updatedUser.getId())))
		.andExpect(jsonPath("$.username", is(updatedUser.getUsername())))
		.andExpect(jsonPath("$.password", is(updatedUser.getPassword())))
		.andExpect(jsonPath("$.rooms[2].id", is(roomId)));
	}
	
	@Test
	public void shouldReturn404OnUpdateRoomToUser() throws Exception{
		
		int roomId = 100;
		
		int userId = 11;
		
		given(roomService.getRoom(roomId)).willReturn(null);
		
		mvc.perform(
				put("/api/users/roomid/{userId}/{roomId}", userId, roomId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldUpdateRoomToUserByRoomname() throws Exception{
		
		String roomname = "room3";
		Room roomToInsert = new Room(3, "room3", "pass3");
		
		Room roomObjectToReturn = new Room(3, "room3", "pass3");
		roomObjectToReturn.addUser(new User(1, "user1", "pass1"));
		roomObjectToReturn.addUser(new User(2, "user2", "pass2"));
		
		int userId = 11;
		User updatedUser = new User(11, "user11", "pass11");
		updatedUser.addRoom(new Room(1, "room1", "pass1"));
		updatedUser.addRoom(new Room(4, "room4", "pass4"));
		updatedUser.addRoom(roomToInsert);
		
		given(roomService.getRoom(roomname)).willReturn(roomObjectToReturn);
		given(userService.saveUserWithRoom(userId, roomObjectToReturn)).willReturn(updatedUser);
		
		RoomAuthenticationRequest request = new RoomAuthenticationRequest(roomToInsert.getRoomname(), roomToInsert.getPassword());
		
		mvc.perform(
				put("/api/users/roomname/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.id", is(updatedUser.getId())))
		.andExpect(jsonPath("$.username", is(updatedUser.getUsername())))
		.andExpect(jsonPath("$.password", is(updatedUser.getPassword())))
		.andExpect(jsonPath("$.rooms[2].roomname", is(roomname)));
	}
	
	@Test
	public void shouldReturn404OnUpdateRoomToUserByRoomname() throws Exception{
		
		String roomname = "room3";
		int userId = 11;
		Room roomToInsert = new Room(3, "room3", "pass3");
		
		given(roomService.getRoom(roomname)).willThrow(EmptyResultDataAccessException.class);
		
		RoomAuthenticationRequest request = new RoomAuthenticationRequest(roomToInsert.getRoomname(), roomToInsert.getPassword());
		
		mvc.perform(
				put("/api/users/roomname/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldReturn401OnUpdateRoomToUserByRoomname() throws Exception{
		
		String roomname = "room3";
		int userId = 11;
		Room roomToInsert = new Room(3, "room3", "passNot3");
		
		Room roomObjectToReturn = new Room(3, "room3", "pass3");
		roomObjectToReturn.addUser(new User(1, "user1", "pass1"));
		roomObjectToReturn.addUser(new User(2, "user2", "pass2"));
		
		given(roomService.getRoom(roomname)).willReturn(roomObjectToReturn);
		
		RoomAuthenticationRequest request = new RoomAuthenticationRequest(roomToInsert.getRoomname(), roomToInsert.getPassword());
		
		mvc.perform(
				put("/api/users/roomname/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andDo(print())
		.andExpect(status().isUnauthorized());
	}
	
	@Test
	public void shouldReturn400OnUpdateRoomToUserByRoomname() throws Exception{
		
		String roomname = "room3";
		int userId = 11;
		Room roomToInsert = new Room(3, "room3", "pass3");
		
		Room roomObjectToReturn = new Room(3, "room3", "pass3");
		roomObjectToReturn.addUser(new User(1, "user1", "pass1"));
		roomObjectToReturn.addUser(new User(2, "user2", "pass2"));
		roomObjectToReturn.addUser(new User(11, "user11", "pass11"));
		
		given(roomService.getRoom(roomname)).willReturn(roomObjectToReturn);
		
		RoomAuthenticationRequest request = new RoomAuthenticationRequest(roomToInsert.getRoomname(), roomToInsert.getPassword());
		
		mvc.perform(
				put("/api/users/roomname/{userId}", userId)
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andDo(print())
		.andExpect(status().isBadRequest());
	}
}
