package com.openclassrooms.chatop.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.service.UserService;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
public class UserController {

	@Autowired
    private UserService userService;
    
    @GetMapping("/user/{id}")
    public Optional<User> getUser(final long id) {
    	return userService.getUser(id);
    }
    
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable long id) {
    	userService.deleteUser(id);
    }
    
    @GetMapping("/auth/me")
    public String getMethodName(@RequestParam String param) {
        return new String();
    }
    
}




