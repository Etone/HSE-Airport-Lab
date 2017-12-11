package com.airport.service;

import com.airport.model.Airplane;
import com.airport.model.AirplaneState;
import com.airport.model.Parkinglot;
import com.airport.model.Runway;
import com.airport.session.AirportEJB;

import javax.annotation.Resource;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Startup
@Singleton
public class TimerTimeService {

    @EJB
    AirportEJB airport;

    @Resource
    TimerService timerService;

    @Schedule(second = "*", minute = "*", hour = "*")
    private void parkPlane(){

        List<Airplane> planes = airport.getAirplanes();
        List<Airplane> landingPlanes = new ArrayList<>();
        List<Parkinglot> parkinglots = airport.getParkinglots();
        List<Parkinglot> freeParkinglots = new ArrayList<>();

        List<Runway> runways = airport.getRunways();
        System.out.println(runways);

        for (Airplane a : planes){
            if ( a.getState() == AirplaneState.Landing){
                System.out.println("qqq");
                System.out.println(a.toString());
                landingPlanes.add(a);
            }
        }

        for (Parkinglot p : parkinglots){
            if (p.isFree()){
                freeParkinglots.add(p);
            }
        }

        Runway tmpRunway = null;
        Parkinglot tmpParkinglot = null;

        for (Airplane a : landingPlanes){

            Date d = new Date();

            if (freeParkinglots.isEmpty()){
                return;
            }

            if (d.getTime()<a.getTimestampParking()){
                continue;
            }

            for (Runway r : runways){
               if (r.getId() == a.getRunway().getId()){
                    tmpRunway = r;
               }
            }

            tmpRunway.setFree(true);
            tmpRunway.setPlane(null);
            airport.merge(tmpRunway);
            a.setRunway(null);

            tmpParkinglot = freeParkinglots.get(0);
            freeParkinglots.remove(0);

            tmpParkinglot.setFree(false);
            tmpParkinglot.setPlane(a);
            a.setParked(tmpParkinglot);
            a.setState(AirplaneState.Parking);
            airport.merge(tmpParkinglot);

            airport.merge(a);
        }




    }
}
