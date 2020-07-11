package com.demo.kudo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kudo.dao.KudosDAO;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.User;
import com.demo.kudo.service.KudosService;

@RestController
@RequestMapping("/api")
public class KudosController {
	
	@Autowired
	private KudosService kudosService;
	
	@GetMapping("/kudos")
	public List<Kudos> getKudos(){
		return kudosService.getKudos();
	}
	
	@GetMapping("/kudos/{kudosId}")
	public Kudos getKudos(@PathVariable int kudosId) {
		Kudos kudos = kudosService.getKudos(kudosId);
		if(kudos == null) {
			throw new KudosNotFoundException("Kudos id not found - " + kudosId);
		}
		return kudos;
	}
	
	@PostMapping("/kudos")
	public Kudos addKudos(@RequestBody Kudos kudos) {
		kudos.setId(0);
		Kudos returnKudo = kudosService.saveKudos(kudos);
		if(returnKudo == null) {
			throw new RuntimeException("Author id is invalid");
		}
		return kudos;
	}
	
	@PutMapping("/kudos")
	public Kudos updateKudos(@RequestBody Kudos kudos) {
		kudosService.saveKudos(kudos);
		return kudos;
	}
	
	@DeleteMapping("/kudos/{kudosId}")
	public String deleteKudos(@PathVariable int kudosId) {
		Kudos kudos = kudosService.getKudos(kudosId);
		if(kudos == null) {
			throw new KudosNotFoundException("Kudos id not found - " + kudosId);
		}
		kudosService.deleteKudos(kudosId);
		return "Deleted kudos " + kudosId;
	}
}
