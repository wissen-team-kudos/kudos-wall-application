package com.demo.kudo.service;

import java.util.List;

import com.demo.kudo.entity.User;

public interface IUserService {
	
	public List<User> getUsers();

	public void saveUser(User theUser);

	public User getUser(int theId);

	public void deleteUser(int theId);
}
