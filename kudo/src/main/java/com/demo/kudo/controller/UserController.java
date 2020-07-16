package com.demo.kudo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kudo.entity.Group;
import com.demo.kudo.entity.User;
import com.demo.kudo.service.GroupService;
import com.demo.kudo.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
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
	
	@PostMapping("/users")
	public User addUser(@RequestBody User user) {
		
		user.setId(0);
		return userService.saveUser(user);
	}

	@PutMapping("/users")
	public User updateUser(@RequestBody User user) {
				
		return userService.saveUser(user);
	}
	
	@PutMapping("/users/groupid/{userId}/{groupId}")
	public User updateGrouptoUser(@PathVariable int userId, @PathVariable int groupId) {
				
		Group group = groupService.getGroup(groupId);
		if(group == null) {
			throw new GroupNotFoundException("Group ID not found - " + groupId);
		}
		
		User user=userService.saveUserWithGroup(userId,group);
		
		return user;
	}
	
	@PutMapping("/users/groupname/{userId}/{groupname}")
	public User updateGrouptoUser(@PathVariable int userId, @PathVariable String groupname) {
		Group group = groupService.getGroup(groupname);
		if(group == null) {
			throw new GroupNotFoundException("Group with groupname :" + groupname + "not found.");
		}
		User user = userService.saveUserWithGroup(userId, group);
		return user;
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
}
