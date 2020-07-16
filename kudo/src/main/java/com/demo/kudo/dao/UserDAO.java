package com.demo.kudo.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.kudo.entity.Group;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;

@Repository
public class UserDAO implements IUserDAO {

	@Autowired
	private EntityManager entityManager;

	@Override
	public List<User> getUsers() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = 
				currentSession.createQuery("from User order by id",
											User.class);
		
		List<User> users = theQuery.getResultList();

		return users;
	}

	@Override
	public User getUser(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);
			
		System.out.println("Session gained");
		User theUser = currentSession.get(User.class, theId);
		System.out.println(theUser);
		return theUser;
	}
	
	@Override
	public User saveUser(User theUser) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		User userToInsert = new User();

		if(theUser.getKudos() != null) {
			for(Kudos kudos : theUser.getKudos()) {
				Kudos viewer = currentSession.get(Kudos.class, kudos.getId());
				userToInsert.addKudo(viewer);
			}
		}
		if(theUser.getGroups()!=null) {
			for(Group group : theUser.getGroups()) {
				Group persistentGroup = currentSession.get(Group.class, group.getId());
				userToInsert.addGroup(persistentGroup);
			}
		}
		
		userToInsert.setId(theUser.getId());
		userToInsert.setUsername(theUser.getUsername());
		userToInsert.setPassword(theUser.getPassword());
		
		currentSession.saveOrUpdate(userToInsert);
		return userToInsert;
	}

	public User saveUserWithGroup(int theId,Group group) {

		Session currentSession = entityManager.unwrap(Session.class);

		User theUser = currentSession.get(User.class, theId);
		theUser.addGroup(group);

		currentSession.saveOrUpdate(theUser);	
		
		return theUser;
	}
	
	@Override
	public void deleteUser(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = 
				currentSession.createQuery("delete from User where id=:userId");
		
		theQuery.setParameter("userId", theId);
		theQuery.executeUpdate();	
	}

	@Override
	public User getUser(String username) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("FROM User WHERE username=:paramUsername");
		query.setParameter("paramUsername", username);
		return (User)query.getSingleResult();
	}
	
	
	@Override
	public List<Kudos> getKudosOfUser(int theID) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		User theUser= currentSession.get(User.class, theID);
		
		Set<Kudos> kudos = new HashSet<Kudos>();
		List<Group> groups = theUser.getGroups();
		List<User> usersInGroup= new ArrayList<User>();
		List<Kudos> kudosOfUser= new ArrayList<Kudos>();
		
		for(Group group:groups) {
			kudos.addAll((group.getKudos()).stream().collect(Collectors.toSet()));
			usersInGroup = group.getUsers();
			
			for(User user: usersInGroup) {
				kudosOfUser = user.getKudos();				
				kudos.addAll(kudosOfUser.stream().collect(Collectors.toSet()));

			}
		}
		


		
		return kudos.stream().collect(Collectors.toList());
	}
	


	

}
