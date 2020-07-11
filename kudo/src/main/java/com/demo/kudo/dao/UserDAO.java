package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.kudo.entity.Group;
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

		User theUser = currentSession.get(User.class, theId);
		
		return theUser;
	}
	
	@Override
	public void saveUser(User theUser) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theUser);	
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
	
	

}