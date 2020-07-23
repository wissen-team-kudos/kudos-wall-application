package com.demo.kudo.roomservice;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.demo.kudo.dao.RoomDAO;
import com.demo.kudo.entity.Room;
import com.demo.kudo.service.RoomService;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@RunWith(MockitoJUnitRunner.class)
public class RoomServiceTest {

	@InjectMocks
	private RoomService roomService;
	
	@Mock
	private RoomDAO roomDAO;
	
	@Test
	public void getRoomsTest() {
		
	   List<Room> roomList = new ArrayList<>();
	   roomList.add(new Room(1, "room1", "room1"));
	   roomList.add(new Room(2, "room2", "room2"));
	   roomList.add(new Room(3, "room3", "room3"));
	   
	   when(roomDAO.getRooms()).thenReturn(roomList);
	   
	   List<Room> roomListReturned = roomService.getRooms();
	   
	   assertEquals(3, roomListReturned.size());
	   verify(roomDAO, times(1)).getRooms();
	}
	
	@Test
	public void getRoomWithIdTest() {
		
	   Room room = new Room(1, "room1", "room1");
	   int roomId = room.getId();
	   
	   when(roomDAO.getRoom(roomId)).thenReturn(room);
	   
	   Room roomReturned = roomService.getRoom(roomId);
	   
	   assertEquals(room.getId(), roomReturned.getId());
       assertEquals(room.getRoomname(), roomReturned.getRoomname());
       assertEquals(room.getPassword(), roomReturned.getPassword());
	}
	
	@Test
	public void getRoomWithNameTest() {
		
	   Room room = new Room(1, "room1", "room1");
	   String roomname = room.getRoomname();
	   
	   when(roomDAO.getRoom(roomname)).thenReturn(room);
	   
	   Room roomReturned = roomService.getRoom(roomname);
	   
	   assertEquals(room.getId(), roomReturned.getId());
       assertEquals(room.getRoomname(), roomReturned.getRoomname());
       assertEquals(room.getPassword(), roomReturned.getPassword());
	}
	
	@Test
	public void saveRoomTest() {
	   
	   Room room = new Room(1, "room1", "room1");
	   
	   roomService.saveRoom(room);
	   
	   verify(roomDAO, times(1)).saveRoom(room);
	}	
	
	@Test
	public void saveRoomWithUserTest() {
	   
	   Room room = new Room(1, "room1", "room1");
	   int userId = 1;
	   
	   roomService.saveRoomWithUser(userId,room);
	   
	   verify(roomDAO, times(1)).saveRoomWithUser(userId,room);

	}	
	
	@Test
	public void deleteRoomTest() {
		
	   Room room = new Room(1, "room1", "room1");
	   int roomId = room.getId();
	   
	   doNothing().when(roomDAO).deleteRoom(roomId);
	   
	   roomService.deleteRoom(roomId);
	   
	   verify(roomDAO, times(1)).deleteRoom(roomId);
	}
}
