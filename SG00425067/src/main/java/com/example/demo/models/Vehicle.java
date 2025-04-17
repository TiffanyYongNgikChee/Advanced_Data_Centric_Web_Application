package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;


@Entity
public class Vehicle {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String reg;
	private String make;
	private String model;
	@ManyToOne
	private Customer owner;
	 
	@ManyToOne
	private Mechanic mechanic;
	
	
	public Vehicle() {
		super();
	}


	public Vehicle(String reg, String make, String model, Customer owner, Mechanic mechanic) {
		super();
		this.reg = reg;
		this.make = make;
		this.model = model;
		this.owner = owner;
		this.mechanic = mechanic;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getReg() {
		return reg;
	}


	public void setReg(String reg) {
		this.reg = reg;
	}


	public String getMake() {
		return make;
	}


	public void setMake(String make) {
		this.make = make;
	}


	public String getModel() {
		return model;
	}


	public void setModel(String model) {
		this.model = model;
	}


	public Customer getOwner() {
		return owner;
	}


	public void setOwner(Customer owner) {
		this.owner = owner;
	}


	public Mechanic getMechanic() {
		return mechanic;
	}


	public void setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}
	
}
