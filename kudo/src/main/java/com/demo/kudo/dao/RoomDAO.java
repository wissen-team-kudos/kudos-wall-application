package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;

@Repository
public class RoomDAO implements IRoomDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Room> getRooms() {
	
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = 
				currentSession.createQuery("from Room order by id",
											Room.class);
		
		List<Room> rooms = theQuery.getResultList();

		return rooms;
	}

	@Override
	public Room getRoom(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Room theRoom = currentSession.get(Room.class, theId);
		
		return theRoom;
	}

	@Override
	public Room saveRoom(Room theRoom) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		Room roomToInsert = new Room();
		
		if(theRoom.getUsers()!=null) {
			for(User user : theRoom.getUsers()) {
				User member = currentSession.get(User.class, user.getId());
				roomToInsert.addUser(member);
			}
		}
		if(theRoom.getKudos()!=null) {
			for(Kudos kudos : theRoom.getKudos()) {
				Kudos kudosToInsert = currentSession.get(Kudos.class, kudos.getId());
				roomToInsert.addKudos(kudosToInsert);
			}
		}

		roomToInsert.setId(theRoom.getId());
		roomToInsert.setRoomname(theRoom.getRoomname());
		roomToInsert.setPassword(theRoom.getPassword());
		
		currentSession.saveOrUpdate(roomToInsert);	
		return roomToInsert;
	}

	@Override
	public void deleteRoom(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = 
				currentSession.createQuery("delete from Room where id=:roomId");
		
		theQuery.setParameter("roomId", theId);
		theQuery.executeUpdate();	
	}

	@Override
	public Room getRoom(String roomname) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("FROM Room WHERE roomname=:paramRoomname");
		query.setParameter("paramRoomname", roomname);
		return (Room)query.getSingleResult();
	}

	
	public Room saveRoomWithUser(int theId,Room room) {

		Session currentSession = entityManager.unwrap(Session.class);
		Room roomToInsert = currentSession.get(Room.class, room.getId());
		User theUser = currentSession.get(User.class, theId);
		roomToInsert.addUser(theUser);
		currentSession.saveOrUpdate(roomToInsert);	
		
		return roomToInsert;
	}	
}
