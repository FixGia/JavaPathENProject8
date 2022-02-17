package tourGuide.service;

import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;


import java.util.Map;

public interface LocationService {

    public Location getUserLocation(String userName);

    public VisitedLocation trackUserLocation(User user);

    public Map<String, Location> getCurrentLocationForAllUsers();


}
