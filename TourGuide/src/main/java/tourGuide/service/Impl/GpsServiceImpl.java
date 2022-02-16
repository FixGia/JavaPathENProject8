package tourGuide.service.Impl;

import gpsUtil.GpsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.model.Attraction;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.GpsService;

import java.util.List;


@Service
@Slf4j
public class GpsServiceImpl implements GpsService {


    @Override
    public VisitedLocation getUserLocation(User user) {

        return (user.getVisitedLocations().size() > 0) ?
                user.getLastVisitedLocation() :
                trackUserLocation(user);
    }

    @Override
    public VisitedLocation trackUserLocation(User user) {
        return null;
    }

    @Override
    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
        return null;
    }

    @Override
    public double getDistance(Location loc1, Location loc2) {
        return 0;
    }

    @Override
    public boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        return false;
    }
}
