package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.example.demo.models.Mechanic;
import com.example.demo.models.Vehicle;
import com.example.demo.data.VehicleData;
import com.example.demo.exceptions.MechanicNotFoundException;
import com.example.demo.exceptions.VehicleException;
import com.example.demo.exceptions.VehicleNotFoundException;
import com.example.demo.repositories.MechanicRepository;
import com.example.demo.repositories.VehicleRepository;
import com.example.demo.views.VehicleViews;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.transaction.Transactional;

/*
 * This service handles all business logic related to vehicles, such as:
 * Fetching vehicles
 * Creating new vehicles from a DTO
 * Assigning mechanics to vehicles
 * Error handling for duplicates or missing data
 * It sits between the controllers (REST API layer) and repositories (data access layer).*/
@Service // Marks this class as a Spring service component—eligible for auto-detection and dependency injection.
public class VehicleService {
	
	@Autowired
    private VehicleRepository vr; //Access to vehicle data (via JPA).
	@Autowired
	private MechanicRepository mr; //Needed when assigning mechanics to vehicles.
    
	//Fetches all vehicles using a public JSON view (to control field exposure).
    @JsonView(VehicleViews.Public.class)
    public Iterable<Vehicle> getAllVehicles() {
        return vr.findAll();
    }
    
    //Returns a filtered list of vehicles based on make (e.g. "Toyota", "Honda").
    @JsonView(VehicleViews.Public.class)
    public List<Vehicle> getVehiclesByMake(String make) {
        return vr.findByMake(make);
    }
    
    //Returns a single vehicle (with extended details) by its registration number.
    @JsonView(VehicleViews.ExtendedPublic.class) // Returns JsonView objects.
    public Optional<Vehicle> getVehicleByReg(String reg) {
        return vr.findByReg(reg);
    }
    
    //Persists a vehicle object directly.
    //Handles duplicate registration numbers using DataIntegrityViolationException.
    public void save(Vehicle v) throws VehicleException { // Save vehicle to database.
    	 try {
    	 vr.save(v);
    	 } catch (DataIntegrityViolationException ex) {
    	 throw new VehicleException("Vehicle " + v.getReg() + " already exists"); // For correct HttpResponseStatus and Message.
    	 }
    }
    
    
    // Creates a Vehicle from DTO for POST implementation.
    public Vehicle createVehicleFromDTO(VehicleData dto) throws VehicleException {
    	//Validates required fields: reg, make, model.
        if (dto.getReg() == null || dto.getMake() == null || dto.getModel() == null) {
            throw new IllegalArgumentException("save.vehicle.reg: reg must be provided");
        }
        
        //Ensures no disallowed fields (e.g., id, owner, mechanic) are set.
        if (dto.getMake() == null) {
            throw new IllegalArgumentException("save.vehicle.make: make must be provided");
        }
        if (dto.getModel() == null) {
            throw new IllegalArgumentException("save.vehicle.model: model must be provided");
        }
        
        if (dto.getId() != null ) {
            throw new IllegalArgumentException("save.vehicle.id: id must not be provided");
        }
        
        if (!dto.getDisallowedFields().isEmpty()) {
            throw new IllegalArgumentException("save.vehicle."+ dto.getDisallowedFields() +": "+ dto.getDisallowedFields() + " must not be provided");
        }
        
        //Prevents creating duplicates by checking if reg already exists.
        if (vr.existsByReg(dto.getReg())) {
            throw new IllegalArgumentException("Vehicle " + dto.getReg() + " already exists");
        }
    	
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setReg(dto.getReg());

        return vr.save(vehicle);
    }
    
    @Transactional // PUT method is atomic. All or nothing.
    public Vehicle updateMechanic(String reg, String mid) { // For POST method: Update mechanic associated with car reg.
        Vehicle vehicle = vr.findByReg(reg)
            .orElseThrow(() -> new IllegalArgumentException("Vehicle " + reg + " doesn't exist")); // Error handling.
        
        Mechanic mechanic = mr.findByMid(mid)
                .orElseThrow(() -> new IllegalArgumentException("Mechanic " + mid + " doesn't exist")); // Error handling.
            
        vehicle.setMechanic(mechanic); // Update mechanic.
        return vr.save(vehicle); // Save mechanic.
    }


}