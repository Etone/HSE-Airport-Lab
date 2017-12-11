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
System.out.println(query.toString());
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

    public void merge(Airplane airplane){
        
        if (airplane.getRunway()!= null){
            entityManager.merge(airplane.getRunway());
        }

        if (airplane.getParkedAt()!= null){
            entityManager.merge(airplane.getParkedAt());
        }

        entityManager.merge(airplane);
    }
    public void merge(Runway runway){
        entityManager.merge(runway);
    }
    public void merge(Parkinglot parkinglot){
        entityManager.merge(parkinglot);
    }


    public void store(Airplane airplane) {
            entityManager.persist(airplane);
    }

    public void store(Runway runway) {
        entityManager.merge(runway);
    }

    public void store(Parkinglot lot) {
        entityManager.merge(lot);
    }
}
