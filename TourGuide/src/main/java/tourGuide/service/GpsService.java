package tourGuide.service;

import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;

import java.util.List;

public interface GpsService {

    public VisitedLocation getUserLocation(User user);

    public VisitedLocation trackUserLocation(User user);

    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation);

    public double getDistance(Location loc1, Location loc2);

    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction);
}
