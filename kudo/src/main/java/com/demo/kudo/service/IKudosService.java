package com.demo.kudo.service;

import java.util.List;

import com.demo.kudo.entity.Kudos;

public interface IKudosService {
	
	public List<Kudos> getKudos();
	
	public void saveKudos(Kudos theKudos);
	
	public Kudos getKudos(int theId);
	
	public void deleteKudos(int theId);
}
