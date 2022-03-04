package tourGuide.controller;


import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class LocationController {

    private final LocationService gpsService;
    private final UserService userService;

    public LocationController(LocationService gpsService, UserService userService) {
        this.gpsService = gpsService;
        this.userService = userService;
    }

    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam @Valid String userName) {

        Location userLocation = gpsService.getUserLocation(userName);

        return JsonStream.serialize(userLocation);

    }

    @RequestMapping("/getAllUsersLocation")
    public String getAllUsersLocation(){

        Map<String, Location> allUsersLocation = gpsService.getCurrentLocationForAllUsers();

        return JsonStream.serialize(allUsersLocation);
    }

    @RequestMapping("/track")
    public String getTrack(@RequestParam @Valid String userName) {

        User user = userService.getUser(userName);
        gpsService.trackUserLocation(user);
        return "Success" + gpsService.trackUserLocation(user);
    }

    @GetMapping("/nearbyAttractions")
    public String getNearbyAttractions(@RequestParam @Valid String userName) {


        return JsonStream.serialize(gpsService.AttractionRecommendedForUser(userName));

    }
}
