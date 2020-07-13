package com.demo.kudo.service;

import java.util.List;

import com.demo.kudo.entity.Group;

public interface IGroupService {

	public List<Group> getGroups();

	public Group saveGroup(Group theGroup);

	public Group getGroup(int theId);

	public void deleteGroup(int theId);
}
