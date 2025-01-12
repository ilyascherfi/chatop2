package com.openclassrooms.chatop.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
	private PasswordEncoder passwordEncoder;
    
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    
    public User saveUser(String name, String email, String password) {
        User createdUser = new User();
        String encryptedPassword = passwordEncoder.encode(password);
        createdUser.setName(name);
        createdUser.setEmail(email);
        createdUser.setPassword(encryptedPassword);

      
        return userRepository.save(createdUser);
    }
    
    public User getCurrentUser() {
    	String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username);
    }

}