package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.example.demo.exceptions.MechanicException;
import com.example.demo.exceptions.MechanicNotFoundException;
import com.example.demo.services.MechanicService;
/*
 * This controller handles deletion of mechanic records via a REST 
 * API. It maps to the /api/mechanic route and provides one endpoint:
 * */
@RestController
@RequestMapping("/api/mechanic")// Base route for all mechanic-related API calls
public class MechanicController {

    @Autowired
    private MechanicService ms;// Inject the service layer to handle mechanic logic
    
    @CrossOrigin(origins = "http://localhost:4200") // Allow frontend Angular app (dev server) to call this endpoint
    @DeleteMapping("/{mid}") // DELETE endpoint: /api/mechanic/{mid}

    public ResponseEntity<?> deleteMechanic(@PathVariable String mid) {
        try { // Try to delete mechanic.
            ms.deleteMechanic(mid); // Attempt to delete mechanic by ID
            return ResponseEntity.ok().build(); // Return HTTP 200 OK on success
        } catch (MechanicNotFoundException e) { // Not found.
        	// Mechanic not found in database — return 404 Not Found
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, 
                e.getMessage()
            );
        } catch (MechanicException e) { // Mechanic is still associated with one or more vehicles — return 400 Bad Request
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST, 
                e.getMessage()
            );
        }
    }
}