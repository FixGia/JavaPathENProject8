package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
public class LocationServiceImpl implements LocationService {


    private final UserService userService;

    public LocationServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Location getUserLocation(final String userName) {

        User user = userService.getUser(userName);

      Location userLocation =  user.getLastVisitedLocation().getLocation();

      return userLocation;
    }

    public Map<String, Location> getCurrentLocationForAllUsers() {

        return Collections.unmodifiableMap(userService.getAllUsers()
                .stream()
                .parallel()
                .collect(Collectors.toMap(user -> user.getUserId().toString(),
                user -> getUserLocation(user.getUserName()))));

    }



    @Override
    public VisitedLocation trackUserLocation(User user) {
        return null;
    }

}
