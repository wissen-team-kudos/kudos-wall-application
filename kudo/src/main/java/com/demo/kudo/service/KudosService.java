package com.demo.kudo.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.demo.kudo.dao.KudosDAO;
import com.demo.kudo.entity.Kudos;

@Service
public class KudosService implements IKudosService {

	@Autowired
	private KudosDAO kudosDAO;
	
	@Override
	@Transactional
	public List<Kudos> getKudos() {
		
		return kudosDAO.getKudos();
	}

	@Override
	@Transactional
	public Kudos getKudo(int theId) {
		
		return kudosDAO.getKudo(theId);
	}
	
	@Override
	@Transactional
	public void saveKudo(Kudos theKudo) {
		
		kudosDAO.saveKudo(theKudo);
	}

	@Override
	@Transactional
	public void deleteKudo(int theId) {

		kudosDAO.deleteKudo(theId);
	}

}

