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
	public Kudos saveKudos(Kudos theKudos) {
		return kudosDAO.saveKudos(theKudos);
	}

	@Override
	@Transactional
	public Kudos getKudos(int theId) {
		return kudosDAO.getKudos(theId);
	}

	@Override
	@Transactional
	public void deleteKudos(int theId) {
		kudosDAO.deleteKudos(theId);
	}

}
