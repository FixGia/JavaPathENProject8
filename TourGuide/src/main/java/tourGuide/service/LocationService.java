package tourGuide.service;

import tourGuide.Dto.AttractionRecommendationRequest;
import tourGuide.Dto.VisitedLocationRequest;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;


import java.util.Map;

public interface LocationService {

    Location getUserLocation(String userName);

    VisitedLocationRequest trackUserLocation(User user);

    Map<String, Location> getCurrentLocationForAllUsers();

    AttractionRecommendationRequest AttractionRecommendedForUser(String userName);




}
