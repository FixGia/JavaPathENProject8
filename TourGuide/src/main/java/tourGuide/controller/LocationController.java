package tourGuide.controller;


import com.jsoniter.output.JsonStream;
import feign.FeignException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.config.GpsMicroService;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class LocationController {

    private final LocationService locationService;
    private final GpsMicroService gpsMicroService;
    private final UserService userService;

    public LocationController(LocationService locationService, GpsMicroService gpsMicroService, UserService userService) {
        this.locationService=locationService;
        this.gpsMicroService = gpsMicroService;
        this.userService = userService;
    }

    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam @Valid String userName) {


       return JsonStream.serialize(locationService.getUserLocation(userName));

    }


    @RequestMapping("/getAllUsersLocation")
    public String getAllUsersLocation(){

        Map<String, Location> allUsersLocation = locationService.getLastLocationForAllUsers();
        return JsonStream.serialize(allUsersLocation);

    }




    @GetMapping("/nearbyAttractions")
    public String getNearbyAttractions(@RequestParam @Valid String userName) {


        return JsonStream.serialize(locationService.AttractionRecommendedForUser(userName));

    }
}
