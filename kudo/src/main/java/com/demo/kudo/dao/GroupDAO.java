package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.kudo.entity.Group;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;

@Repository
public class GroupDAO implements IGroupDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Group> getGroups() {
	
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = 
				currentSession.createQuery("from Group order by id",
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
	public Group saveGroup(Group theGroup) {

		Session currentSession = entityManager.unwrap(Session.class);
		
		Group groupToInsert = new Group();
		
		if(theGroup.getUsers()!=null) {
			for(User user : theGroup.getUsers()) {
				User member = currentSession.get(User.class, user.getId());
				groupToInsert.addUser(member);
			}
		}
		if(theGroup.getKudos()!=null) {
			for(Kudos kudos : theGroup.getKudos()) {
				Kudos kudosToInsert = currentSession.get(Kudos.class, kudos.getId());
				groupToInsert.addKudos(kudosToInsert);
			}
		}

		groupToInsert.setId(theGroup.getId());
		groupToInsert.setGroupname(theGroup.getGroupname());
		groupToInsert.setPassword(theGroup.getPassword());
		
		currentSession.saveOrUpdate(groupToInsert);	
		return groupToInsert;
	}

	@Override
	public void deleteGroup(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = 
				currentSession.createQuery("delete from Group where id=:groupId");
		
		theQuery.setParameter("groupId", theId);
		theQuery.executeUpdate();	
	}

	@Override
	public Group getGroup(String groupname) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("FROM Group WHERE groupname=:paramGroupname");
		query.setParameter("paramGroupname", groupname);
		return (Group)query.getSingleResult();
	}

	
	public Group saveGroupWithUser(int theId,Group group) {

		Session currentSession = entityManager.unwrap(Session.class);
		Group groupToInsert = currentSession.get(Group.class, group.getId());
		User theUser = currentSession.get(User.class, theId);
		groupToInsert.addUser(theUser);
		currentSession.saveOrUpdate(groupToInsert);	
		
		return groupToInsert;
	}	
}
