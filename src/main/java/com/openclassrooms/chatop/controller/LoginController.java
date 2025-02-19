package com.openclassrooms.chatop.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.chatop.dto.LoginRequest;
import com.openclassrooms.chatop.dto.RegisterRequest;
import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.service.JWTService;
import com.openclassrooms.chatop.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("api/auth")
public class LoginController {
    private JWTService jwtService;

    private UserService userService;




    public LoginController(JWTService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> getToken(@Valid @RequestBody LoginRequest loginRequest) {
        if (userService.existsByEmail(loginRequest.getEmail())) {
            String token = jwtService.generateToken( loginRequest.getEmail());
            return ResponseEntity.ok(Map.of("token", token));
        } else {
            return ResponseEntity.badRequest().body(Map.of("message", "User does not exist"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegisterRequest registerRequest) {

        if (userService.saveUser(registerRequest) == null) {
            return ResponseEntity.badRequest().body(Map.of("message", "User already exists"));
        }

        String token = jwtService.generateToken(registerRequest.getEmail());


        return ResponseEntity.ok(Map.of("token", token));
    }
    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(401).build();
        }


        Jwt jwt = (Jwt) authentication.getPrincipal();

        String email = jwt.getClaim("email");

        UserDto user = userService.getUserByEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).build();
        }

        return ResponseEntity.ok(user);
    }


}
