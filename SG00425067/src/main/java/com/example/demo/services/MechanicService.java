package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.MechanicException;
import com.example.demo.exceptions.MechanicNotFoundException;
import com.example.demo.models.Mechanic;
import com.example.demo.models.Vehicle;
import com.example.demo.repositories.MechanicRepository;
import com.example.demo.repositories.VehicleRepository;

import jakarta.transaction.Transactional;

// Provides a dedicated service for operations related to mechanics, acting as the middle layer between your controllers (API) and repositories (DB access).
@Service
@Transactional
public class MechanicService {
	
	//Injects the MechanicRepository and VehicleRepository to access mechanics and check if any vehicles are tied to them
    @Autowired
    private MechanicRepository mr;
    
    @Autowired
    private VehicleRepository vr;

 // Delete mechanic by mid.
    public void deleteMechanic(String mid) {
        // Check if mechanic exists.
        Mechanic mechanic = mr.findByMid(mid)
            .orElseThrow(() -> new MechanicNotFoundException("Mechanic " + mid + " doesn't exist"));

        // Check if mechanic is associated with any vehicles
        List<Vehicle> vehicles = vr.findByMechanicId(Integer.parseInt(mid.substring(1)));
        if (!vehicles.isEmpty()) {
            throw new MechanicException("Mechanic " + mid +" can't be deleted. He/She is servicing " + vehicles.size() + " vehicles");
        }

        // Delete mechanic.
        mr.delete(mechanic);
    }
}