package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="parkinglot.findAll", query="select p from Parkinglot p")

@Entity
public class Parkinglot {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Id
	@GeneratedValue
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
