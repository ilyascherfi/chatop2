package com.openclassrooms.chatop.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.openclassrooms.chatop.dto.RentalRequest;
import com.openclassrooms.chatop.dto.UserDto;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.repository.RentalRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private UserService userService;

    public Rental saveRental(RentalRequest rental) throws IOException {
        Rental rentalToSave = new Rental();
        rentalToSave.setName(rental.getName());
        rentalToSave.setPrice(rental.getPrice());
        rentalToSave.setDescription(rental.getDescription());
        rentalToSave.setSurface(rental.getSurface());

        rentalToSave.setPicture(rental.getPicture());
        rentalToSave.setCreated_at(LocalDate.now());
        rentalToSave.setUpdated_at(LocalDate.now());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }


        Jwt jwt = (Jwt) authentication.getPrincipal();

        String email = jwt.getClaim("email");

        UserDto user = userService.getUserByEmail(email);

        rentalToSave.setOwner_id(user.getId());

        rentalToSave.setCreated_at(LocalDate.now());
        rentalToSave.setUpdated_at(LocalDate.now());

        return rentalRepository.save(rentalToSave);
    }

    public List<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRental(Long id) {
        return rentalRepository.findById(id).orElse(null);
    }

    public Rental updateRental(Long id, RentalRequest rental) throws IOException {
        Rental rentalToUpdate = rentalRepository.findById(id).orElse(null);
        if (rentalToUpdate == null) {
            return null;
        }


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return null;
        }


        Jwt jwt = (Jwt) authentication.getPrincipal();

        String email = jwt.getClaim("email");

        UserDto user = userService.getUserByEmail(email);

        if (!rentalToUpdate.getOwner_id().equals(user.getId()) ) {
            throw new AccessDeniedException("User is not authorized");
        }

        rentalToUpdate.setName(rental.getName());
        rentalToUpdate.setPrice(rental.getPrice());
        rentalToUpdate.setDescription(rental.getDescription());
        rentalToUpdate.setSurface(rental.getSurface());
        rentalToUpdate.setUpdated_at(LocalDate.now());

        return rentalRepository.save(rentalToUpdate);
    }

}
