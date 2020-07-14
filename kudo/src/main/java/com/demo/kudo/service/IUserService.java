package com.demo.kudo.service;

import java.util.List;

import com.demo.kudo.entity.User;

public interface IUserService {

	public List<User> getUsers();

	public User saveUser(User theUser);

	public User getUser(int theId);
	
	public User getUser(String username);

	public void deleteUser(int theId);
}
