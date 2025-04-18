package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import com.example.demo.models.Vehicle;
import com.example.demo.data.VehicleData;
import com.example.demo.exceptions.VehicleException;
import com.example.demo.repositories.VehicleRepository;
import com.example.demo.views.VehicleViews;
import com.fasterxml.jackson.annotation.JsonView;

@Service
public class VehicleService {
	
	@Autowired
    private VehicleRepository vr;
    
    @JsonView(VehicleViews.Public.class)
    public Iterable<Vehicle> getAllVehicles() {
        return vr.findAll();
    }
    
    @JsonView(VehicleViews.Public.class)
    public List<Vehicle> getVehiclesByMake(String make) {
        return vr.findByMake(make);
    }
    
    
    // Creates a Vehicle from DTO for POST implementation.
    public Vehicle createVehicleFromDTO(VehicleData dto) throws VehicleException {
        if (dto.getReg() == null || dto.getMake() == null || dto.getModel() == null) {
            throw new VehicleException("reg, make, and model are required fields");
        }

        if (vr.existsByReg(dto.getReg())) {
            throw new VehicleException("Vehicle with reg " + dto.getReg() + " already exists");
        }
    	
        Vehicle vehicle = new Vehicle();
        vehicle.setMake(dto.getMake());
        vehicle.setModel(dto.getModel());
        vehicle.setReg(dto.getReg());

        return vr.save(vehicle);
    }


}