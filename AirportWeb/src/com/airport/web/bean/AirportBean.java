package com.airport.web.bean;

import com.airport.model.Airplane;
import com.airport.model.AirplaneState;
import com.airport.model.Parkinglot;
import com.airport.model.Runway;
import com.airport.session.AirportEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ManagedBean(name = "airportBean")
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

    private boolean noFreeParkinglots = false;

    public boolean isNoFreeParkinglots() {
        return noFreeParkinglots;
    }

    public void setNoFreeParkinglots(boolean noFreeParkinglots) {
        this.noFreeParkinglots = noFreeParkinglots;
    }

    public boolean isNoFreeRunways() {
        return noFreeRunways;
    }

    public void setNoFreeRunways(boolean noFreeRunways) {
        this.noFreeRunways = noFreeRunways;
    }

    private boolean noFreeRunways = false;

    public AirportBean() {
        System.out.println("AIRPORT: " + UUID.randomUUID());
    }

    @PostConstruct
    private void init() {
        airplane = new Airplane();
        for (int i = 0; i < parkinglotCount; i++) {
            parkinglot = new Parkinglot();
            airportEJB.store(parkinglot);
        }

        for (int i = 0; i < runwayCount; i++) {
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
        airplane = new Airplane();
    }

    public void landPlane(int airplaneid) {
        System.out.println("NYI");


        List<Airplane> planes = airportEJB.getAirplanes();

        for (Airplane p : planes) {
            if (p.getId() == airplaneid) {
                airplane = p;
            }
        }

        if (airplane.getState() == AirplaneState.Flying) {
            freeRunway();

            System.out.println(runway.getId());

            runway.setFree(false);
            System.out.println(runway.isFree());
            parkinglot.setFree(false);
            airplane.setState(AirplaneState.Landing);
            airplane.setRunway(runway);
            airportEJB.store(airplane);

            airplane = new Airplane();
        }
    }

    private Parkinglot getFreeParkingLot() {
        for (Parkinglot lot : getParkinglots()) {
            if (lot.isFree()) {
                //One Free, continue Parking
                return lot;
            }
        }
        //No free Parkinglots
        return null;
    }

    private List<Runway> getFreeRunways() {
        List<Runway> runways = new ArrayList<>();
        for (Runway runway : getRunways()) {
            if (runway.isFree()) {
                //One Free, continue Parking
                runways.add(runway);
            }
        }
        //No free Parkinglots
        return runways;

    }

    private void freeParkingLot() {
        if ((parkinglot = getFreeParkingLot()) == null) {
            noFreeParkinglots = true;
            throw new ArrayIndexOutOfBoundsException("No free parkinglot");
        } else {
            noFreeParkinglots = false;
        }
    }

    private void freeRunway() {
        if (getFreeRunways().size() > 1) {
            noFreeRunways = false;
            runway = getFreeRunways().get(0);

        } else  {
            runway = getFreeRunways().get(0);
            noFreeRunways = true;
        }
    }

}
