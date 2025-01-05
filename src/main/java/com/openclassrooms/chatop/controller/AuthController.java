package com.openclassrooms.chatop.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatop.dto.request.RegisterRequest;
import com.openclassrooms.chatop.dto.response.RegisterResponse;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.service.JWTService;
import com.openclassrooms.chatop.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {

	
	public JWTService jwtService;
	private UserService userService; 
	
	@Autowired
	public AuthController(UserService userService, JWTService jwtService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	@PostMapping("/auth/login") 
	public String getToken(Authentication authentication) {
		String token = jwtService.generateToken(authentication);
		return token;
	}
	
	@PostMapping("/auth/register")
	public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
		 User createdUser = userService.saveUser(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());
		 return new RegisterResponse(createdUser.getId(), createdUser.getUsername());
	}
	
	
}
