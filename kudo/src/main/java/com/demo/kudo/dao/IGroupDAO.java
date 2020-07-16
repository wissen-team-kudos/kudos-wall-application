package com.demo.kudo.dao;

import java.util.List;

import com.demo.kudo.entity.Group;


public interface IGroupDAO {

	public List<Group> getGroups();

	public Group saveGroup(Group theGroup);

	public Group getGroup(int theId);
	
	public Group getGroup(String groupname);

	public void deleteGroup(int theId);
}
