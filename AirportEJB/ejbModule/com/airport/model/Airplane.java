package com.airport.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@NamedQuery(name="airplane.findAll", query="select a from Airplane a order by a.name")

@Entity
public class Airplane {
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private AirplaneState state;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id")
	private Parkinglot parkedAt;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="id")
	private Runway runway;
	
	public Airplane() {
		state = AirplaneState.Flying;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public AirplaneState getState() {
		return state;
	}
	
	public void setState(AirplaneState state) {
		this.state = state;
	}
	
	public void setParked(Parkinglot parkedAt) {
		this.parkedAt = parkedAt;
	}
	
	public Parkinglot getParkedAt() {
		return parkedAt;
	}
	
	public void setRunway(Runway runway) {
		this.runway = runway;
	}
	
	public Runway getRunway() {
		return runway;
	}
}
