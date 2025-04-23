package com.example.demo.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import com.example.demo.exceptions.MechanicNotFoundException;
import com.example.demo.exceptions.VehicleException;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.models.Vehicle;
import com.example.demo.data.VehicleData;

import com.example.demo.services.VehicleService;
import com.example.demo.views.VehicleViews;
import com.example.demo.validations.VehiclePValidation;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
/*
 * This is the REST controller for handling HTTP requests related to vehicles.
 * It maps requests to the /api/vehicle route and delegates logic to the
 *  VehicleService layer.*/
@RestController
@RequestMapping("/api/vehicle") // Base route for all vehicle-related endpoints
public class VehicleControllers {
	 @Autowired
     VehicleService vs; // Injecting the VehicleService to delegate business logic
	 
     @GetMapping("/all")
     @JsonView(VehicleViews.Public.class)
     public Iterable<Vehicle> getAllVehicles() {
         return vs.getAllVehicles(); // Get all vehicles from the service
     }
     
     @GetMapping // Endpoint: /api/vehicle?make=Toyota
     @JsonView(VehicleViews.Public.class)
     public List<Vehicle> getVehiclesByMake(@RequestParam String make) {
         return vs.getVehiclesByMake(make); // Get vehicles filtered by make
     }
	 
	 @GetMapping("/one") // Endpoint: /api/vehicle/one?reg=ABC123
	 @JsonView(VehicleViews.ExtendedPublic.class)
	 public Optional<Vehicle> getVehicleByReg(@RequestParam String reg) {
	    return vs.getVehicleByReg(reg); // Find vehicle by registration number
	  }

     @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, "application/json;charset=UTF-8"}) // POST "/api/vehicle"
     public ResponseEntity<?> addVehicle(@Valid @RequestBody VehicleData dto, BindingResult result) throws VehicleException { // Get vehicle from request body.
    	// Validate input and return field-specific error messages
     	if (result.hasErrors()) {
     		// Return first error message caught during validation.
     		String errorMessage = result.getFieldErrors().stream()
     	            .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
     	            .collect(Collectors.joining(", "));
     	        return ResponseEntity.badRequest().body(errorMessage);
     	}
         
     	// Create and return the new vehicle object
        Vehicle vehicle = vs.createVehicleFromDTO(dto);
             
         // Return 400 OK -> vehicle saved to database.
         return ResponseEntity.ok(vehicle);
     }
     
     @PutMapping("/{reg}")
     @JsonView(VehicleViews.Public.class)
     public ResponseEntity<?> updateVehicleMechanic(
         @PathVariable String reg,
         @RequestBody Map<String, Object> requestBody) {  // Use Map to inspect raw JSON.
         
    	 
    	 // Prevent updating restricted fields
         Set<String> disallowed = Set.of("id", "name", "salary", "garage", "vehicles");
         for (String key : requestBody.keySet()) {
             if (disallowed.contains(key)) {
                 throw new ResponseStatusException(
                     HttpStatus.INTERNAL_SERVER_ERROR,
                     "updateVehicle.mechanic." + key + ": " + key + " must not be provided, "
                 );
             }
         }

         // Check for the presence of required 'mid' (mechanic ID)
         if (!requestBody.containsKey("mid")) {
             throw new ResponseStatusException(
                 HttpStatus.BAD_REQUEST,
                 "updateVehicle.mechanic.mid: mid must be provided "
             );
         }
         
         // Try to update the mechanic assigned to the vehicle
         try {
         	// Get mid from JSON, and try to update vehicle mid.
             String mid = requestBody.get("mid").toString();
             Vehicle updatedVehicle = vs.updateMechanic(reg, mid);
             return ResponseEntity.ok(updatedVehicle);
         } catch (VehicleNotFoundException e) { // Not found.
             throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
         } catch (MechanicNotFoundException e) { // Bad request: Vehicle found but mechanic not found.
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
         }
     }
}