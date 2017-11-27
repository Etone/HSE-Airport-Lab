package com.airport.session;

import com.airport.model.Airplane;
import com.airport.model.Parkinglot;
import com.airport.model.Runway;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Stateless
public class AirportEJB {

    @PersistenceContext(unitName = "airport")
    private EntityManager entityManager;

    public List<Airplane> getAirplanes() {
        Query query = entityManager.createNamedQuery("airplane.findAll");

        @SuppressWarnings("unchecked") List<Airplane> airplanes = query.getResultList();
        return airplanes;
    }

    public List<Runway> getRunways() {
        Query query = entityManager.createNamedQuery("runway.findAll");

        @SuppressWarnings("unchecked") List<Runway> runways = query.getResultList();
        return runways;
    }

    public List<Parkinglot> getParkinglots() {
        Query query = entityManager.createNamedQuery("parkinglot.findAll");

        @SuppressWarnings("unchecked") List<Parkinglot> lots = query.getResultList();
        return lots;
    }

    public void store(Airplane airplane) {
        Airplane newAirplane = entityManager.find(Airplane.class, airplane.getId());
        if (null != newAirplane) {

            newAirplane.setRunway(airplane.getRunway());
            newAirplane.setState(airplane.getState());
            newAirplane.setParked(airplane.getParkedAt());
            entityManager.merge(newAirplane);

        } else {
            entityManager.merge(airplane);

        }

    }

    public void store(Runway runway) {
        entityManager.persist(runway);
    }

    public void store(Parkinglot lot) {
        entityManager.persist(lot);
    }
}
