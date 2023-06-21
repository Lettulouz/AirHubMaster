package com.airhubmaster.airhubmaster.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Airport {
    private List<Plane> planeList = new ArrayList<>();
    private List<Flight> flightList = new ArrayList<>();
    private UserProfile userProfile = new UserProfile();

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void planeDeparture(Plane plane, Flight flight){
       plane.setAvailableAt(LocalDateTime.now().plusHours(flight.getFlightLength()));
    }

    private void possibleFlights(Plane plane){
        for(int i=0; i<3; i++){
            Flight flight = new Flight(plane, userProfile);
            flight.generateFlight();
            flightList.add(flight);
        }
    }
}
