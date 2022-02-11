package tourGuide.service;


import gpsUtil.location.VisitedLocation;
import tourGuide.model.User;

public interface GpsService {
    public VisitedLocation trackUserLocation(User user);

}
