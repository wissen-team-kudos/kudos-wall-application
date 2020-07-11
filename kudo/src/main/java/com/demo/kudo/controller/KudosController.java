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

import com.demo.kudo.entity.Kudos;
import com.demo.kudo.service.KudosService;

@RestController
@RequestMapping("/api")
public class KudosController {
	
	@Autowired
	private KudosService kudosService;
	
	@GetMapping("/kudos")
	public List<Kudos> getKudos() {
		
		return kudosService.getKudos();
	}
	
	@GetMapping("/kudos/{kudoId}")
	public Kudos getKudo(@PathVariable int KudoId) {
		
		return kudosService.getKudo(KudoId);
	}
	
	@PostMapping("/kudos")
	public Kudos addKudos(@RequestBody Kudos kudo) {
		
		kudo.setId(0);
		kudosService.saveKudo(kudo);
		
		return kudo;
	}

	@PutMapping("/kudos")
	public Kudos updateGroup(@RequestBody Kudos kudo) {
		
		kudosService.saveKudo(kudo);
		
		return kudo;
	}
	
	@DeleteMapping("/kudos/{kudoId}")
	public String deleteKudo(@PathVariable int kudoId) {
		
		kudosService.deleteKudo(kudoId);
		
		return "Deleted kudo "+kudoId;
	}
}
