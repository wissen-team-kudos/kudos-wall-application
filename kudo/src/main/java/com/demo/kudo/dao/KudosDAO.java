package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.kudo.entity.Group;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;

@Repository
public class KudosDAO implements IKudosDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Kudos> getKudos() {
		
		Session currentSession = entityManager.unwrap(Session.class);
		Query<Kudos> query = currentSession.createQuery("from Kudos", Kudos.class);
		List<Kudos> kudos = query.getResultList();
		
		return kudos;
	}

	@Override
	public Kudos saveKudos(Kudos theKudos) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		
		Kudos kudosToInsert = new Kudos();
		int authorId = theKudos.getAuthor().getId();
		User author = currentSession.get(User.class, authorId);
		if(author == null) {
			return null;
		}
		if(theKudos.getUsers() != null) {
			for(User user : theKudos.getUsers()) {
				User viewer = currentSession.get(User.class, user.getId());
				kudosToInsert.addUser(viewer);
			}
		}
		if(theKudos.getGroups()!=null) {
			for(Group group : theKudos.getGroups()) {
				Group persistentGroup = currentSession.get(Group.class, group.getId());
				kudosToInsert.addGroup(persistentGroup);
			}
		}
		
		kudosToInsert.setId(theKudos.getId());
		kudosToInsert.setContent(theKudos.getContent());
		kudosToInsert.setAuthor(author);
		
		
		currentSession.saveOrUpdate(kudosToInsert);
		return kudosToInsert;
	}

	@Override
	public Kudos getKudos(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Kudos kudos = currentSession.get(Kudos.class, theId);
		return kudos;
	}

	@Override
	public void deleteKudos(int theId) {
		Session currentSession = entityManager.unwrap(Session.class);
		Query query = currentSession.createQuery("delete from Kudos where id=:kudosId");
		query.setParameter("kudosId", theId);
		query.executeUpdate();
	}

}
