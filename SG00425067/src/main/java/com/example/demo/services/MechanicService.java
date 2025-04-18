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

@Service
@Transactional
public class MechanicService {

    @Autowired
    private MechanicRepository mr;
    
    @Autowired
    private VehicleRepository vr;


}