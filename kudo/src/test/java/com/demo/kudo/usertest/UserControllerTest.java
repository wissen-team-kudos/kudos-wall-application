package com.demo.kudo.usertest;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.kudo.controller.UserController;
import com.demo.kudo.entity.User;
import com.demo.kudo.service.RoomService;
import com.demo.kudo.service.UserService;

@RunWith(SpringRunner.class)
@WebMvcTest(controllers = UserController.class)
@ActiveProfiles("test")
class UserControllerTest {
	 
	@Autowired
	private Environment environment;
	
	@Autowired
	private MockMvc mvc;
	
	@MockBean                           
    private UserService userService;
	
	@MockBean
	private RoomService roomService;
	
	
	private List<User> userList; 

	@BeforeEach                           
    void setUp() {                               
       this.userList = new ArrayList<>();
       this.userList.add(new User(1, "user1", "pass1"));
       this.userList.add(new User(2, "user2", "pass2"));
       this.userList.add(new User(3, "user3", "pass3"));
	}
	
	@BeforeEach
	public void temp() {
		for (String profileName : environment.getActiveProfiles()) {
			System.out.println("Currently active profile - " + profileName);
		}
	}
	
	/*
	 * Test Case: Get all the users successfully
	 * API: /api/users
	 */
	@Test
	public void shouldFetchAllUsers() throws Exception{

		
		given(userService.getUsers()).willReturn(userList);
		
		mvc.perform(
				get("http://localhost:8080/api/users/")
				.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(userList.size())));
				
	}
}
