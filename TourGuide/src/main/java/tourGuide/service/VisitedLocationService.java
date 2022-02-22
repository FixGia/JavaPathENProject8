package tourGuide.service;

import tourGuide.Dto.VisitedLocationRequest;
import tourGuide.model.VisitedLocation;

import java.util.List;

public interface VisitedLocationService {


    void addToVisitedLocations(VisitedLocationRequest visitedLocation);

    List<VisitedLocation> getVisitedLocations();

    void clearVisitedLocations();

    public VisitedLocation getLastVisitedLocation();
}
