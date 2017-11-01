package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Parkinglot {
	
	@GeneratedValue
	@Id
	private int id;
	
	private boolean isFree;

	
	public Parkinglot() {
		isFree = true;
	}
	
	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}
}
