package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.model.User;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {

        this.userService = userService;
    }

    @RequestMapping("/getUser")
    public User getUser(@RequestParam String userName){

        log.info("##Controller /getUser ==> Request for user {} : ", userName);
        return userService.getUser(userName);

    }

    @RequestMapping("getAllUsers")
    public String getAllUsers(){

        return JsonStream.serialize(userService.getAllUsers());

    }

    @PutMapping("/updateUserPreferences")
    public String updateUserPreferences(@Valid @RequestBody final UserPreferencesRequest userPreferencesRequest, @RequestParam @NotNull final String userName){


        return JsonStream.serialize(userService.updateUserPreferences(userName,userPreferencesRequest));
    }
}
