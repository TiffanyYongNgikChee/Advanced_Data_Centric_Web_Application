package com.example.demo.repositories;

/*
 * Handles all CRUD operations and queries for Mechanic entities. It extends JpaRepository, so you automatically get:
 * save(), delete(), findById(), etc.
 * Plus, you define some custom queries using method names and @EntityGraph for performance.*/
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.demo.models.Mechanic;

public interface MechanicRepository extends JpaRepository<Mechanic, Integer> {
	@EntityGraph(attributePaths = {"garage"})
    List<Mechanic> findAll();
	
	@EntityGraph(attributePaths = {"garage"})
    Optional<Mechanic> findByMid(String mid);
}