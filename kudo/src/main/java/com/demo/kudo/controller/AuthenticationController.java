package com.demo.kudo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.kudo.entity.User;
import com.demo.kudo.models.AuthenticationRequest;
import com.demo.kudo.models.AuthenticationResponse;
import com.demo.kudo.security.services.MyUserDetailsService;
import com.demo.kudo.security.util.JwtUtil;
import com.demo.kudo.service.UserService;

@RestController
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private MyUserDetailsService userDetailsService;

	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
			);
		} catch(BadCredentialsException e) {
			throw new Exception("Incorrect username or password ", e);
		}
		
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());
		final User user = userService.getUser(userDetails.getUsername());
		
		final String jwt = jwtTokenUtil.generateToken(userDetails, user.getId());	
		
		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}