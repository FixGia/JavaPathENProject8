package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.model.VisitedLocation;
import tourGuide.service.GpsService;
import tourGuide.service.UserService;

@RestController
public class UserController {

    private final GpsService gpsService;
    private final UserService userService;

    public UserController(GpsService gpsService, UserService userService) {
        this.gpsService = gpsService;
        this.userService = userService;
    }

    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) {

        	VisitedLocation visitedLocation = gpsService.getUserLocation(userService.getUser(userName));
		return JsonStream.serialize(visitedLocation.location);

    }

}
