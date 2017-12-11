package com.airport.model;

import javax.persistence.*;

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

	@OneToOne(fetch = FetchType.EAGER)
	private Airplane plane;

	public Airplane getPlane() {
		return plane;
	}

	public void setPlane(Airplane plane) {
		this.plane = plane;
	}
	
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

	@Override
	public String toString() {
		return "Parkinglot{" + "id=" + id + ", isFree=" + isFree + '}';
	}
}
