package com.airport.web.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Entity;

import com.airport.model.Airplane;
import com.airport.model.AirplaneState;
import com.airport.model.Parkinglot;
import com.airport.model.Runway;
import com.airport.session.AirportEJB;

@ManagedBean(name="airportBean")
@SessionScoped
public class AirportBean implements Serializable {
	private static final long serialVersionUID = 1665363412715858198L;

	@EJB
	private AirportEJB airportEJB;
	
	private Airplane airplane;
	
	private int runwayCount = 4;
	private int parkinglotCount = 8;
	
	private Parkinglot parkinglot; 
	private Runway runway;
	
	public AirportBean() {
		System.out.println("AIRPORT: " + UUID.randomUUID());
	}
	
	@PostConstruct
	private void init() {
		airplane = new Airplane();
		for(int i = 0; i < parkinglotCount; i++) {			
			parkinglot = new Parkinglot();
			airportEJB.store(parkinglot);
		}
		
		for(int i = 0; i < runwayCount; i++) {
			runway = new Runway();
			airportEJB.store(runway);
		}
	}
	
	public List<Airplane> getAirplanes() {
		return airportEJB.getAirplanes();
	}

	public List<Runway> getRunways() {
		return airportEJB.getRunways();
	}
	
	public List<Parkinglot> getParkinglots() {
		return airportEJB.getParkinglots();
	}
	
	public Airplane getAirplane() {
		return airplane;
	}
	
	public void store() {
		airportEJB.store(airplane);
	}
	
	public void landPlane() {
		System.out.println("NYI");

		
		if((parkinglot = getFreeParkingLot()) == null) {
			throw new ArrayIndexOutOfBoundsException("No free parkinglot");
		}
		if((runway = getFreeRunway()) == null) {
			throw new ArrayIndexOutOfBoundsException("No free runway");
		}
		
		airplane.setRunway(runway);
		runway.setFree(false);
		parkinglot.setFree(false);
		airplane.setState(AirplaneState.Landing);
		
		airportEJB.store(airplane);
	}
	
	private Parkinglot getFreeParkingLot() {
		for(Parkinglot lot : getParkinglots()) {
			if(lot.isFree()) {
				//One Free, continue Parking
				return lot;
			}
		}
		//No free Parkinglots
		return null;
	}
	
	private Runway getFreeRunway() {
		for(Runway runway : getRunways()) {
			if(runway.isFree()) {
				//One Free, continue Parking
				return runway;
			}
		}
		//No free Parkinglots
		return null;
		
	}
}
