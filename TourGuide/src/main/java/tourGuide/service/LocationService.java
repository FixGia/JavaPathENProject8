package tourGuide.service;

import tourGuide.Dto.AttractionRecommendationRequest;
import tourGuide.Dto.VisitedLocationRequest;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;


import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface LocationService {

    Location getUserLocation(String username);

    Map<String, Location> getCurrentLocationForAllUsers();

    AttractionRecommendationRequest AttractionRecommendedForUser(String userName);




}
