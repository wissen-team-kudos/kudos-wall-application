package com.demo.kudo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.kudo.dao.GroupDAO;
import com.demo.kudo.dao.UserDAO;
import com.demo.kudo.entity.Group;
import com.demo.kudo.entity.User;

@Service
public class GroupService implements IGroupService {

	@Autowired
	private GroupDAO groupDAO;
	
	@Override
	@Transactional
	public List<Group> getGroups() {
		
		return groupDAO.getGroups();
	}

	@Override
	@Transactional
	public Group getGroup(int theId) {
		
		return groupDAO.getGroup(theId);
	}
	
	@Override
	@Transactional
	public Group saveGroup(Group theGroup) {
		
		return groupDAO.saveGroup(theGroup);
	}

	@Override
	@Transactional
	public void deleteGroup(int theId) {

		groupDAO.deleteGroup(theId);
	}

}
