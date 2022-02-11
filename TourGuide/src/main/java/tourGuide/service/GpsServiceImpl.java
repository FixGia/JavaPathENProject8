package tourGuide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.tracker.Tracker;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.List;


@Service
public class GpsServiceImpl {


    private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
    private final GpsUtil gpsUtil;
    private final RewardsServiceImpl rewardsService;
    private final TripPricer tripPricer = new TripPricer();
    public final Tracker tracker;
    boolean testMode = true;

    public GpsServiceImpl(GpsUtil gpsUtil, RewardsServiceImpl rewardsService, Tracker tracker) {
        this.gpsUtil = gpsUtil;
        this.rewardsService = rewardsService;
        this.tracker = tracker;
    }

    public VisitedLocation getUserLocation(User user) {
        return (user.getVisitedLocations().size() > 0) ?
                user.getLastVisitedLocation() :
                trackUserLocation(user);
    }

    public List<Provider> getTripDeals(User user) {
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
        List<Provider> providers = tripPricer.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
        user.setTripDeals(providers);
        return providers;
    }

    public VisitedLocation trackUserLocation(User user) {
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
        user.addToVisitedLocations(visitedLocation);
        rewardsService.calculateRewards(user);
        return visitedLocation;
    }

    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
        List<Attraction> nearbyAttractions = new ArrayList<>();
        for(Attraction attraction : gpsUtil.getAttractions()) {
            if(rewardsService.isWithinAttractionProximity(attraction, visitedLocation.location)) {
                nearbyAttractions.add(attraction);
            }
        }
        return nearbyAttractions;
    }


    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tracker.stopTracking();
            }
        });
    }
}
