package com.example.demo.repositories;


import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.models.Vehicle;

// Handles all operations related to Vehicle entities, including custom queries by registration, make, and mechanic.
public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {
    @EntityGraph(attributePaths = {"owner", "mechanic", "mechanic.garage"})
    List<Vehicle> findAll();
    //Retrieves vehicles filtered by their make, also including related entities as above.
    @EntityGraph(attributePaths = {"owner", "mechanic", "mechanic.garage"})
    List<Vehicle> findByMake(String make);
    
    //Looks up a single vehicle by registration number, including all related entities.
    @EntityGraph(attributePaths = {"owner", "mechanic", "mechanic.garage"})
    Optional<Vehicle> findByReg(String reg);
    
    //Finds vehicles assigned to a specific mechanic by their internal ID.
    @EntityGraph(attributePaths = {"owner", "mechanic", "mechanic.garage"})
    List<Vehicle> findByMechanicId(int mechanic);
    
    //Checks whether a vehicle exists with the given registration—great for validation and avoiding duplicates before inserts.
    boolean existsByReg(String reg);

}