package com.airport.model;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name="airplane.findAll", query="select a from Airplane a order by a.name")

@Entity
public class Airplane implements Serializable{
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@Enumerated(EnumType.STRING)
	private AirplaneState state;
	
	@OneToOne(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id")
	private Parkinglot parkedAt;
	
	@OneToOne(fetch=FetchType.EAGER, cascade = {CascadeType.ALL})
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

    public void setParkedAt(Parkinglot parkedAt) {
        this.parkedAt = parkedAt;
    }

    public long getTimestampLanding() {
        return timestampLanding;
    }

    public void setTimestampLanding(long timestampLanding) {
        this.timestampLanding = timestampLanding;
    }

    public long getTimestampParking() {
        return timestampParking;
    }

    public void setTimestampParking(long timestampParking) {
        this.timestampParking = timestampParking;
    }

    private long timestampLanding;
	private long timestampParking;
}
