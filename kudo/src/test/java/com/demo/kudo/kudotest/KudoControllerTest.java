package com.demo.kudo.kudotest;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyInt;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.env.Environment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.kudo.controller.KudosController;
import com.demo.kudo.controller.UserController;
import com.demo.kudo.entity.Kudos;
import com.demo.kudo.entity.Room;
import com.demo.kudo.entity.User;
import com.demo.kudo.models.RoomAuthenticationRequest;
import com.demo.kudo.service.KudosService;
import com.demo.kudo.service.RoomService;
import com.demo.kudo.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = KudosController.class)
@ActiveProfiles("test")
class KudoControllerTest {
	 
	@Autowired
	private MockMvc mvc;
	
	@Autowired ObjectMapper objectMapper;
	
	@MockBean                           
    private KudosService kudoService;
	
	/*
	 * Test Case: Get all the users successfully
	 * API: /api/users
	 */
	@Test
	public void shouldFetchAllKudos() throws Exception{

		List<Kudos> kudosList;
		kudosList = new ArrayList<>();
	    kudosList.add(new Kudos(1, "kudo1", new User(1, "user1", "pass1")));
	    kudosList.add(new Kudos(2, "kudo2",new User(2, "user2", "pass2")));
	    kudosList.add(new Kudos(3, "kudo3",new User(3, "user3", "pass3")));
	
		given(kudoService.getKudos()).willReturn(kudosList);
		
		mvc.perform(
				get("http://localhost:8080/api/kudos/")
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(kudosList.size())));
				
	}
	
	@Test
	public void shouldFetchOneKudo() throws Exception{

		final int kudoId = 1;
		final Kudos kudo = new Kudos(1, "kudo1", new User(2, "user2", "pass2"));
		
		given(kudoService.getKudos(kudoId)).willReturn(kudo);
		
		mvc.perform(
				get("http://localhost:8080/api/kudos/{kudoId}", kudoId)
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(kudo.getId())))
				.andExpect(jsonPath("$.content", is(kudo.getContent())))
				.andExpect(jsonPath("$.author.id", is((kudo.getAuthor().getId()))))
				.andExpect(jsonPath("$.author.username", is((kudo.getAuthor().getUsername()))))
				.andExpect(jsonPath("$.author.password", is((kudo.getAuthor().getPassword()))));
				
	}
	
	@Test
	public void shouldReturn404OnFetchOneKudoById() throws Exception{

		final int kudoId = 1;
		
		given(kudoService.getKudos(kudoId)).willReturn(null);
		
		mvc.perform(
				get("http://localhost:8080/api/kudos/{kudosId}", kudoId)
				.accept(MediaType.APPLICATION_JSON))
				//.andDo(print())
				.andExpect(status().isNotFound());
				
	}
	@Test
	public void shouldReturn404OnFetchOneKudo() throws Exception{
		final int id = 50;
		
		given(kudoService.getKudos(id)).willReturn(null);
		
		mvc.perform(
				get("http://localhost:8080/api/kudos/{id}", id)
				.accept(MediaType.APPLICATION_JSON))
		//.andDo(print())
		.andExpect(status().isNotFound());
	}
	
	@Test
	public void shouldCreateNewKudo() throws Exception{
		
		
		Kudos kudo =  new Kudos(10, "kudo1", new User(1, "user1", "pass1"));
		Kudos kudoReturn =  new Kudos(10, "kudo1", new User(1, "user1", "pass1"));
		
		given(kudoService.saveKudos(any(Kudos.class))).willReturn(kudoReturn);
		
		mvc.perform(
				post("/api/kudos")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kudo)))
				//.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(kudoReturn.getId())))
				.andExpect(jsonPath("$.content", is(kudoReturn.getContent())))
				.andExpect(jsonPath("$.author.id", is((kudoReturn.getAuthor().getId()))))
				.andExpect(jsonPath("$.author.username", is((kudoReturn.getAuthor().getUsername()))))
				.andExpect(jsonPath("$.author.password", is((kudoReturn.getAuthor().getPassword()))));
	}
	
	@Test
	public void shouldThrowExceptionOnCreateNewKudo() throws Exception{
		
		
		Kudos kudo =  new Kudos(10, "kudo1", null);
		
		given(kudoService.saveKudos(any(Kudos.class))).willReturn(null);
		
		mvc.perform(
				post("/api/kudos")
				.accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(kudo)))
				//.andDo(print())
				.andExpect(status().isNotFound());
	}
	

	@Test
	public void shouldDeletekudo() throws Exception{
		int kudoId = 1;
		Kudos kudo =  new Kudos(1, "kudo1", new User(1, "user1", "pass1"));
		given(kudoService.getKudos(kudoId)).willReturn(kudo);
		doNothing().when(kudoService).deleteKudos(kudoId);
		
		mvc.perform(
				delete("/api/kudos/{kudoId}", kudoId))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(jsonPath("$", is("Deleted kudos " + kudoId)));
	}
	@Test
	public void shouldThrowExceptionOnDeletekudo() throws Exception{
		int kudoId = 50;
		
		given(kudoService.getKudos(kudoId)).willReturn(null);
		
		mvc.perform(
				delete("/api/kudos/{kudoId}", kudoId))
		.andDo(print())
		.andExpect(status().isNotFound());
	}
	@Test
	public void shouldUpdateOneKudo() throws Exception{

		final int kudoId = 1;
		final Kudos kudo = new Kudos(1, "kudo1", new User(1, "user1", "pass1"));
		
		given(kudoService.saveKudos(kudo)).willReturn(kudo);
		
		mvc.perform(
				put("http://localhost:8080/api/kudos/", kudo)
				.accept(MediaType.APPLICATION_JSON)
		      	.contentType(MediaType.APPLICATION_JSON)
		      	.content(objectMapper.writeValueAsString(kudo)))
				//.andDo(print())
				.andExpect(status().isOk());
				
	}
	
	
}
