package com.openclassrooms.chatop.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatop.service.JWTService;

@RestController
@RequestMapping("/api")
public class LoginController {

	public JWTService jwtService; 
	
	public LoginController(JWTService jwtService) {
		this.jwtService = jwtService;
	}
	
	@PostMapping("/auth/login") 
	public String getToken(Authentication authentication) {
		String token = jwtService.generateToken(authentication);
		return token;
	}
	
	
}
