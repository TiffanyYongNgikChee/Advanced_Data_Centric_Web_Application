package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

@Entity
public class Mechanic {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(unique = true)
	private String mid;
	private String name;
	private Double salary;
	@ManyToOne
	private Garage garage;
	@OneToMany(mappedBy = "mechanic")
	private List<Vehicle> vehicles;// = new ArrayList<Vehicle>();
	
	public Mechanic() {
		super();
	}
	public Mechanic(String mid, String name, Double salary, Garage garage) {
		super();
		this.mid = mid;
		this.name = name;
		this.salary = salary;
		this.garage = garage;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getSalary() {
		return salary;
	}
	public void setSalary(double salary) {
		this.salary = salary;
	}
	public Garage getGarage() {
		return garage;
	}
	public void setGarage(Garage garage) {
		this.garage = garage;
	}
	public List<Vehicle> getVehicles() {
		return vehicles;
	}
	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

}
