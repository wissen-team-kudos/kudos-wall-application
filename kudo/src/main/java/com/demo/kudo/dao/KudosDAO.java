package com.demo.kudo.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.kudo.entity.Kudos;

@Repository
public class KudosDAO implements IKudosDAO {

	@Autowired
	private EntityManager entityManager;
	
	@Override
	public List<Kudos> getKudos() {
	
		Session currentSession = entityManager.unwrap(Session.class);
		
		Query theQuery = 
				currentSession.createQuery("from Kudos order by id",
											Kudos.class);
		
		List<Kudos> kudos = theQuery.getResultList();

		return kudos;
	}
	@Override
	public Kudos getKudo(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Kudos theKudo = currentSession.get(Kudos.class, theId);
		
		return theKudo;
	}
	
	@Override
	public void saveKudo(Kudos theKudo) {

		Session currentSession = entityManager.unwrap(Session.class);

		currentSession.saveOrUpdate(theKudo);	
	}

	@Override
	public void deleteKudo(int theId) {

		Session currentSession = entityManager.unwrap(Session.class);

		Query theQuery = 
				currentSession.createQuery("delete from Kudos where id=:Id");
		
		theQuery.setParameter("Id", theId);
		theQuery.executeUpdate();	
	}

}

