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
		
		return groupService.getGroup(groupId);
	}
	
	@PostMapping("/groups")
	public Group addGroup(@RequestBody Group group) {
		
		group.setId(0);
		groupService.saveGroup(group);
		
		return group;
	}

	@PutMapping("/groups")
	public Group updateGroup(@RequestBody Group group) {
		
		groupService.saveGroup(group);
		
		return group;
	}
	
	@DeleteMapping("/groups/{groupId}")
	public String deleteGroup(@PathVariable int groupId) {
		
		groupService.deleteGroup(groupId);
		
		return "Deleted group "+groupId;
	}
}
