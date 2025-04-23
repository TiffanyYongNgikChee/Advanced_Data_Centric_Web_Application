package com.example.demo.views;

/*
 * used with the @JsonView annotation in models or controllers to selectively 
 * expose fields based on the context.*/
public class VehicleViews {
	
    public interface Public {}
    public interface ExtendedPublic extends Public {}
}