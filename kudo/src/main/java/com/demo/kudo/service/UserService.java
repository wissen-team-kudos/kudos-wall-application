package com.demo.kudo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.kudo.dao.UserDAO;
import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserDAO userDAO;
	
	@Override
	@Transactional
	public List<User> getUsers() {
		
		return userDAO.getUsers();
	}

	@Override
	@Transactional
	public User getUser(int theId) {
		
		return userDAO.getUser(theId);
	}

	@Override
	@Transactional
	public User saveUser(User theUser) {
		
		 return userDAO.saveUser(theUser);	
	}

	//@Override
	@Transactional
	public User saveUserWithRoom(int userId,Room room) {
		
		 return userDAO.saveUserWithRoom(userId,room);	
	}
	
	@Override
	@Transactional
	public void deleteUser(int theId) {
		
		userDAO.deleteUser(theId);
	}

	@Override
	@Transactional
	public User getUser(String username) {
		return userDAO.getUser(username);
		
	}
	@Override
	@Transactional
	public List<Kudos> getKudosOfUser(int theId) {
		// TODO Auto-generated method stub
		return userDAO.getKudosOfUser(theId);
	}

}
