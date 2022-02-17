package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.model.User;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

@RestController
public class UserController {

    private final LocationService gpsService;
    private final UserService userService;

    public UserController(LocationService locationService, UserService userService) {
        this.gpsService = locationService;
        this.userService = userService;
    }

    @RequestMapping("/getUser")
    public String getUser(@RequestParam String username){

        User user = userService.getUser(username);

        return JsonStream.serialize(user);
    }

    @RequestMapping("getAllUsers")
    public String getAllUsers(){

        return JsonStream.serialize(userService.getAllUsers());

    }




}
