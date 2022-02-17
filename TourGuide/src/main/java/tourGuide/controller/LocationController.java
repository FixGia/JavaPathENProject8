package tourGuide.controller;


import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.model.Location;
import tourGuide.service.LocationService;

import javax.validation.Valid;
import java.util.Map;

@RestController
public class LocationController {

    private final LocationService gpsService;

    public LocationController(LocationService gpsService) {
        this.gpsService = gpsService;
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


}
