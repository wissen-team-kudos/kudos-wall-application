package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.kudo.entity.Group;
import com.demo.kudo.entity.User;

@Repository
public class GroupDAO implements IGroupDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Group> getGroups() {
	
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = 
				currentSession.createQuery("from Group order by groupname",
											Group.class);
		
		List<Group> groups = theQuery.getResultList();

		return groups;
	}

	@Override
	public Group getGroup(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Group theGroup = currentSession.get(Group.class, theId);
		
		return theGroup;
	}

	@Override
	public void saveGroup(Group theGroup) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theGroup);	
	}

	@Override
	public void deleteGroup(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = 
				currentSession.createQuery("delete from Group where id=:groupId");
		
		theQuery.setParameter("groupId", theId);
		theQuery.executeUpdate();	
	}

}
