package com.airport.web.bean;

import com.airport.model.Airplane;
import com.airport.model.AirplaneState;
import com.airport.model.Parkinglot;
import com.airport.model.Runway;
import com.airport.session.AirportEJB;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.io.Serializable;
import java.util.*;

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
    private boolean noFreeRunways = false;

    public void setFreeParkingLots(List<Parkinglot> freeParkingLots) {
        this.freeParkingLots = freeParkingLots;
    }

    public List<Parkinglot> getFreeParkingLots() {
        return freeParkingLots;
    }

    private List<Parkinglot> freeParkingLots;

    public List<Integer> getFreeParkingLotIDs() {
        return freeParkingLotIDs;
    }

    public void setFreeParkingLotIDs(List<Integer> freeParkingLotIDs) {
        this.freeParkingLotIDs = freeParkingLotIDs;
    }

    private List<Integer> freeParkingLotIDs;

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

        updateFreeParkingLots();
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

            Date d = new Date();

            airplane.setTimestampLanding(d.getTime());
            Random rand = new Random();
            airplane.setTimestampParking(d.getTime()+(long)3000); //(rand.nextInt(15)*1000+1000)

            System.out.println(runway.getId());

            System.out.println(runway);
            runway.setFree(false);
            System.out.println(runway.isFree());
            parkinglot.setFree(false);
            airplane.setRunway(runway);
            airplane.setState(AirplaneState.Landing);
            runway.setPlane(airplane);
            airportEJB.merge(runway);
            System.out.println(airplane.getRunway());
            airportEJB.merge(airplane);
            System.out.println("XXC");
            System.out.println(airplane.toString());


            airplane = new Airplane();
        }
    }

    public void updateFreeParkingLots() {
        List<Parkinglot> park = new ArrayList<>();
        for (Parkinglot lot : getParkinglots()) {
            if (lot.isFree()) {
                //One Free, continue Parking
                park.add(lot);
            }
        }
        //No free Parkinglots
        freeParkingLots = park;

        List<Integer> freeParkingLIs = new ArrayList<>();
        for (Parkinglot p : park) {
            freeParkingLIs.add(p.getId());
        }

        freeParkingLotIDs = freeParkingLIs;
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
        if (freeParkingLots.size() > 1) {
            noFreeParkinglots = false;
            parkinglot = getParkinglots().get(0);
        } else if (freeParkingLots.size() == 1) {
            noFreeParkinglots = true;
            parkinglot = getParkinglots().get(0);
        } else {
            noFreeParkinglots = true;
            parkinglot = null;
        }
        updateFreeParkingLots();
    }

    private void freeRunway() {
        if (getFreeRunways().size() > 1) {
            noFreeRunways = false;
            runway = getFreeRunways().get(0);

        } else {
            runway = getFreeRunways().get(0);
            noFreeRunways = true;
        }
    }

    public void update(){
        freeRunway();
        freeParkingLot();

    }


}
