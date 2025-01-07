package com.openclassrooms.chatop.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatop.dto.request.LoginRequest;
import com.openclassrooms.chatop.dto.request.RegisterRequest;
import com.openclassrooms.chatop.dto.response.JwtResponse;
import com.openclassrooms.chatop.dto.response.RegisterResponse;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.service.JWTService;
import com.openclassrooms.chatop.service.UserService;

@RestController
@RequestMapping("/api")
public class AuthController {


	public JWTService jwtService;
	private UserService userService; 
	private AuthenticationManager authenticationManager;
	
	@Autowired
	public AuthController(AuthenticationManager authenticationManager, UserService userService, JWTService jwtService) {
		this.jwtService = jwtService;
		this.userService = userService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping("/auth/login") 
	public JwtResponse authenticateUser(@RequestBody LoginRequest loginRequest) {
        // Authentifie l'utilisateur avec les identifiants fournis
        Authentication authentication = authenticationManager.authenticate(
        		new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        		
        // Récupère les détails de l'utilisateur après authentification
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String token = jwtService.generateJwtToken(userDetails.getUsername());

        // Renvoie le token dans une réponse structurée
        return new JwtResponse(token);
    }
	
	@PostMapping("/auth/register")
	public RegisterResponse registerUser(@RequestBody RegisterRequest registerRequest) {
		 User createdUser = userService.saveUser(registerRequest.getUsername(), registerRequest.getEmail(), registerRequest.getPassword());
		 return new RegisterResponse(createdUser.getId(), createdUser.getUsername());
	}
	
	
}
