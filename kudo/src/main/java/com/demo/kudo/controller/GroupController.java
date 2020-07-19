package com.demo.kudo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import com.demo.kudo.models.GroupAuthenticationRequest;
import com.demo.kudo.service.GroupService;

@RestController
@RequestMapping("/api")
public class GroupController {

	@Autowired
	private GroupService groupService;
	
	@GetMapping("/groups")
	public List<Group> getGroups() {
		
		return groupService.getGroups();
	}
	
	@GetMapping("/groups/{groupId}")
	public Group getGroup(@PathVariable int groupId) {
		
		Group group = groupService.getGroup(groupId);
		
		if(group == null) {
			throw new UserNotFoundException("Group ID not found - " + groupId);
		}
		
		return group;
	}
	
	@PostMapping("/groups")
	public ResponseEntity<Group> addGroup(@RequestBody Group group) {
		try {
			Group groupToInsert = groupService.getGroup(group.getGroupname());
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		} catch(EmptyResultDataAccessException e) {
			group.setId(0);
			return ResponseEntity.ok(groupService.saveGroup(group));
		}
		

	}

	@PutMapping("/groups")
	public Group updateGroup(@RequestBody Group group) {
		
		return groupService.saveGroup(group);

	}
	
	@DeleteMapping("/groups/{groupId}")
	public String deleteGroup(@PathVariable int groupId) {
		
		Group group = groupService.getGroup(groupId);
		if(group == null) {
			throw new UserNotFoundException("Group ID not found - " + groupId);
		}
		
		groupService.deleteGroup(groupId);
		
		return "Deleted group "+groupId;
	}
	
	@PutMapping("/groups/groupname/{userId}")
	public ResponseEntity<Group> updateUserInGroup(@PathVariable int userId, @RequestBody GroupAuthenticationRequest request) {
		Group group;
		try {
			group = groupService.getGroup(request.getGroupname());
		}
		catch(EmptyResultDataAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
		if(!group.getPassword().equals(request.getPassword())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
		}
		for(User user : group.getUsers()) {
			if(user.getId() == userId) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
		}
		//User user = userService.saveUserWithGroup(userId, group);
		Group groupToInsert = groupService.saveGroupWithUser(userId, group);
		return ResponseEntity.ok(groupToInsert);
	}
}