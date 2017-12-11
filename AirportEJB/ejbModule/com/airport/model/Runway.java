package com.airport.model;

import javax.persistence.*;

@NamedQuery(name="runway.findAll", query="select r from Runway r")

@Entity
public class Runway {

	@OneToOne(fetch = FetchType.EAGER)
	private Airplane plane;

	public Airplane getPlane() {
		return plane;
	}

	public void setPlane(Airplane plane) {
		this.plane = plane;
	}

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

	@Override
	public String toString() {
		return "Runway{" + "id=" + id + ", free=" + free + '}';
	}
}
