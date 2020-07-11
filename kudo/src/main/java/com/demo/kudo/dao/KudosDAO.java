package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.demo.kudo.entity.Kudos;

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
	public void saveKudos(Kudos theKudos) {
		
		Session currentSession = entityManager.unwrap(Session.class);
		currentSession.saveOrUpdate(theKudos);
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
