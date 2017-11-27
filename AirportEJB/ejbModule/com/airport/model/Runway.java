package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;

@NamedQuery(name="runway.findAll", query="select r from Runway r")

@Entity
public class Runway {

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	@Id
	@GeneratedValue
	private int id;
	
	private boolean free;
	
	public Runway() {
		free = true;
	}

	public boolean isFree() {
		return free;
	}

	public void setFree(boolean isFree) {
		this.free = isFree;
	}

}
