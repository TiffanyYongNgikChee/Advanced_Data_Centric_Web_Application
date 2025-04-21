package com.example.demo.data;

import java.util.ArrayList;
import java.util.List;

import com.example.demo.models.Customer;
import com.example.demo.models.Mechanic;
import com.example.demo.views.VehicleViews;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;

@JsonIgnoreProperties(ignoreUnknown = false)
public class VehicleData {

	private String reg;

	private String make;

	private String model;
	
	private Integer id; // Disallowed field - should not be set directly in the request
	
	private final List<String> disallowedFields = new ArrayList<>();

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        if ("owner".equals(key) || "mechanic".equals(key) || "id".equals(key)) {
            disallowedFields.add(key);
        }
    }

    public List<String> getDisallowedFields() {
        return disallowedFields;
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
	
	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
}