package com.openclassrooms.chatop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatop.dto.request.LoginRequest;
import com.openclassrooms.chatop.dto.request.RegisterRequest;
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
	public void authenticateUser(@RequestBody LoginRequest loginRequest) {
        
    }
	
	@PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest registerRequest) {
        User createdUser = userService.saveUser(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());
        String token = jwtService.authenticate(registerRequest.getEmail(), registerRequest.getPassword());
        return ResponseEntity.ok(token);
    }
	
	
}
