package com.airport.session;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.airport.model.Airplane;

@Stateless
public class AirportEJB {

	@PersistenceContext(unitName="airport")
	private EntityManager entityManager;
	
	public List<Airplane> getAirplanes() {
		Query query = entityManager.createNamedQuery("airplane.findAll");
		
		@SuppressWarnings("unchecked")
		List<Airplane> airplanes = query.getResultList();
		return airplanes;
	}
	
	public void store(Airplane airplane) {
		entityManager.persist(airplane);
	}
}
