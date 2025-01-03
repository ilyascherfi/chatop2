package com.openclassrooms.chatop.service;



import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;

import lombok.Data;

@Data
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    public Optional<User> getUser(final long id) {
    	return userRepository.findById(id); 
    }
    
    public Iterable<User> getUsers() {
    	return userRepository.findAll();
    }
    
    public void deleteUser(final long id) {
    	userRepository.deleteById(id);
    }
    
    public User saveUser(String username, String email, String password) {
        User createdUser = new User();
        createdUser.setUsername(username);
        createdUser.setEmail(email);
        createdUser.setPassword(email);

      
        return userRepository.save(createdUser);
    }
    
    public User getCurrentUser() {
    	String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username);
    }

}