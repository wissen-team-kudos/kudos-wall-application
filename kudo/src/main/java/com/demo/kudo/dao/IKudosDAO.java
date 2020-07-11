package com.demo.kudo.dao;

import java.util.List;

import com.demo.kudo.entity.Kudos;


public interface IKudosDAO {

	public List<Kudos> getKudos();

	public void saveKudo(Kudos theKudo);

	public Kudos getKudo(int theId);

	public void deleteKudo(int theId);
}

