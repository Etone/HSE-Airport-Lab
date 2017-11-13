package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="runway.findAll", query="select r from Runway r")

@Entity
public class Runway {
	
	@GeneratedValue
	@Id
	private int id;
	
	private boolean isFree;
	
	public Runway() {
		isFree = true;
	}

	public boolean isFree() {
		return isFree;
	}

	public void setFree(boolean isFree) {
		this.isFree = isFree;
	}

}
