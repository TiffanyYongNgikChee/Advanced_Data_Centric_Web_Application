package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*This class provides a centralized mechanism to handle specific exceptions that occur anywhere in 
 * your Spring Boot application—specifically, custom VehicleException*/
@ControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(VehicleException.class)
    public ResponseEntity<?> handleVehicleException(VehicleException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
	}
}