package com.openclassrooms.chatop.service;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.dto.RegisterRequest;
import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.model.User;
import com.openclassrooms.chatop.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDate;

@Service
public class UserService {

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public User saveUser(RegisterRequest registerRequest) {
        if (userRepository.existsByEmail(registerRequest.getEmail())) {
            return null;
        }

        User user = new User();
        user.setName(registerRequest.getName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setCreated_at(LocalDate.now());
        user.setUpdated_at(LocalDate.now());
        return userRepository.save(user);
    }

    public UserDto getUserByEmail(String email) {

        User user = userRepository.findByEmail(email);
        if (user == null) {
            return null;
        }
        return modelMapper.map(user, UserDto.class);
    }


    public UserDto getUser(Long id) {
        User user = userRepository.findById(id).orElse(null);

        return modelMapper.map(user, UserDto.class);
    }
}