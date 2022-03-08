package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.Dto.AttractionRecommendationRequest;
import tourGuide.Dto.AttractionRequest;
import tourGuide.Dto.NearAttraction;

import tourGuide.Dto.VisitedLocationRequest;
import tourGuide.config.GpsMicroService;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.LocationService;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;
import tourGuide.util.DistanceCalculator;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


@Service
@Slf4j
public class LocationServiceImpl implements LocationService {


    private final UserService userService;
    private final GpsMicroService microServiceGps;
    private final DistanceCalculator distanceCalculator;
    private final RewardService rewardService;
    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);




    public LocationServiceImpl(UserService userService, GpsMicroService microServiceGps, DistanceCalculator distanceCalculator, RewardService rewardService) {
        this.userService = userService;
        this.microServiceGps = microServiceGps;
        this.distanceCalculator = distanceCalculator;
        this.rewardService = rewardService;

    }


    @Override
    public Location getUserLocation(final String userName) {

        User user = userService.getUser(userName);

        if (user.getVisitedLocations().size()> 0){
            log.info("{}", user.getLastVisitedLocation().getLocation());
            return user.getLastVisitedLocation().getLocation();
        }

        log.info("display location via GpsMicroService {}", microServiceGps.getLocation(user.getUserId()).getLocation());
        return microServiceGps.getLocation(user.getUserId()).getLocation();

    }

    public CompletableFuture<?> trackUserLocation(User user) {

        return CompletableFuture.supplyAsync(() -> {
                    VisitedLocationRequest visitedLocation = microServiceGps
                            .getLocation(user.getUserId());

                    VisitedLocation visitedLocationToAdd = new VisitedLocation(visitedLocation.getUserId(),visitedLocation.getLocation(),visitedLocation.getTimeVisited());

                    user.addToVisitedLocations(visitedLocationToAdd);

                   CompletableFuture.runAsync(() -> {
                       rewardService.calculateRewardAsync(user);
                    });

                    return visitedLocation;

                },
                executorService);
    }

    public Map<String, Location> getLastLocationForAllUsers() {

        return Collections.unmodifiableMap(userService.getAllUsers()
                .stream()
                .parallel()
                .collect(Collectors.toMap(user -> user.getUserId().toString(),
                user -> user.getLastVisitedLocation().getLocation())));

    }


    /** Get Map with Near Five Attractions
     * Key Attraction
     * Value Double for Location
     * @param attractions List of Attractions
     */
    private Map<AttractionRequest, Double> GetNearFiveAttractions (Map<AttractionRequest, Double> attractions) {

        return attractions
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (entry1, entry2) -> entry1, LinkedHashMap::new));
    }

    /**
     * Get a Map
     * Key = Attraction
     * Value = Distance between Attraction and User
     * @param userLocation location's user
     * @param attractions List of all attractions
     * @return
     */
    private Map<AttractionRequest, Double> getAttractionsMaps(Location userLocation, List<AttractionRequest> attractions){

        Map<AttractionRequest, Double> attractionsMap= new HashMap<>();
        attractions.forEach(a -> attractionsMap.put(a, distanceCalculator.getDistanceInMiles(userLocation,a.getLocation())));
        return attractionsMap;
    }

    /**
     * Get New AttractionRecommendation For An User
     *
     * @param userName String username
     *
     * @return new AttractionRecommendation
     */
    public AttractionRecommendationRequest AttractionRecommendedForUser(String userName) {

        User user = userService.getUser(userName);
       Location userLocation = user.getLastVisitedLocation().getLocation();
        List<AttractionRequest> attractions = microServiceGps.getAttractions();

        List<NearAttraction> nearAttractions = new ArrayList<>();




        Map<AttractionRequest, Double> nearFiveAttractions = GetNearFiveAttractions(getAttractionsMaps(userLocation, attractions));
        nearFiveAttractions
                .entrySet()
                .stream()
                .forEach(a -> nearAttractions
                        .add(new NearAttraction(a.getKey().getAttractionName(),
                                a.getKey().getLocation(),
                                a.getValue().intValue(),
                                rewardService.getAttractionRewardPoints(user,a.getKey()))));

        return new AttractionRecommendationRequest(userLocation, nearAttractions);
    }



}
