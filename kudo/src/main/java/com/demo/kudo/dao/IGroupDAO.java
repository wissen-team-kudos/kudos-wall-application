package com.demo.kudo.dao;

import java.util.List;

import com.demo.kudo.entity.Group;


public interface IGroupDAO {

	public List<Group> getGroups();

	public void saveGroup(Group theGroup);

	public Group getGroup(int theId);

	public void deleteGroup(int theId);
}
