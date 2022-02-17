package tourGuide.service;

import tourGuide.model.VisitedLocation;

import java.util.List;

public interface VisitedLocationService {


    void addToVisitedLocations(VisitedLocation visitedLocation);

    List<VisitedLocation> getVisitedLocations();

    void clearVisitedLocations();

    public VisitedLocation getLastVisitedLocation();
}
