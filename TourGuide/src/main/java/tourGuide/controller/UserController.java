package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
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

    @GetMapping("/getUser")
    public User getUser(@RequestParam String userName){

        log.info("##Controller /getUser ==> Request for user {} : ", userName);
        return userService.getUser(userName);

    }

    @GetMapping("getAllUsers")
    public String getAllUsers(){

        return JsonStream.serialize(userService.getAllUsers());

    }

    @PutMapping("/updateUserPreferences")
    public ResponseEntity<UserPreferences> updateUserPreferences( @RequestParam @NotNull final String userName, @Valid @RequestBody final UserPreferencesRequest userPreferencesRequest){

        if (userName.length() == 0 ){

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
       UserPreferences userPreferences = userService.updateUserPreferences(userName,userPreferencesRequest);

        return ResponseEntity.ok(userPreferences);
    }
}
