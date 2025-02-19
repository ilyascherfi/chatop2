package com.openclassrooms.chatop.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.openclassrooms.chatop.dto.RentalRequest;
import com.openclassrooms.chatop.model.Rental;
import com.openclassrooms.chatop.service.RentalService;
import com.openclassrooms.chatop.service.CloudinaryService;
import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("api/rentals")
public class RentalController {

    RentalService rentalService;
    CloudinaryService cloudinaryService;

    public RentalController(RentalService rentalService, CloudinaryService cloudinaryService) {
        this.rentalService = rentalService;
        this.cloudinaryService = cloudinaryService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Map<String, String>> createRental(    @RequestParam("name") String name,
                                                                @RequestParam("surface") Double surface,
                                                                @RequestParam("price") Double price,
                                                                @RequestParam("description") String description,
                                                                @RequestParam("picture") MultipartFile picture) throws IOException {

        // Télécharger l'image sur Cloudinary
        Map result = cloudinaryService.uploadImage(picture);
        String imageUrl = (String) result.get("url");

        RentalRequest rental = new RentalRequest();
        rental.setName(name);
        rental.setSurface(surface);
        rental.setPrice(price);
        rental.setDescription(description);
        rental.setPicture(imageUrl);

        if (rentalService.saveRental(rental)==null) {
            return ResponseEntity.badRequest().body(Map.of("message", "Rental already exists"));
        }
        else {
            return ResponseEntity.ok(Map.of("message", "Rental created!"));
        }

    }

    @GetMapping()
    public Map<String, List<Rental>> getRentals() {
        List<Rental> rentals = rentalService.getRentals();
        return Map.of("rentals", rentals);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRental(@PathVariable Long id) {
        Rental rental = rentalService.getRental(id);
        if (rental == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rental);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRental(@PathVariable Long id, @Valid @ModelAttribute  RentalRequest rental) throws IOException {
        try {
            Rental updatedRental = rentalService.updateRental(id, rental);
            if (updatedRental == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "User not authorized or rental not found"));
            }
            return ResponseEntity.ok(Map.of("message", "Rental updated!"));
        } catch (AccessDeniedException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", ex.getMessage()));
        }
    }


}
