package tourGuide.IntegrationTest;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuide.Dto.AttractionRecommendationRequest;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.LocationService;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("IT - Service - LocationService")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class LocationServiceTest {


    @Autowired
    private LocationService locationService;

    @Autowired
    UserService userService;

    @Autowired
    RewardService rewardService;


    @Test
    public void testGetUserLocation() {

        Locale.setDefault(Locale.US);

        User user = userService
                .getUser("internalUser1");

        Location result = locationService
                .getUserLocation("internalUser1");

        assertNotNull(result);
        assertEquals(user.getLastVisitedLocation().getLocation().getLatitude(), result.getLatitude());
        assertEquals(user.getLastVisitedLocation().getLocation().getLongitude(), result.getLongitude());


    }


    @Test
    public void testUserLocationWithVisitedLocation() {

        User user = userService.getUser("internalUser1");

        Location location = new Location(33.881866, -115.90065);

        user.getVisitedLocations().clear();

        VisitedLocation visitedLocation = new VisitedLocation(
                user.getUserId(),
                location,
                new Date());
        user.addToVisitedLocations(visitedLocation);

        Location result = locationService
                .getUserLocation("internalUser1");

        assertNotNull(result);
        assertEquals(result.toString(), location.toString());
    }

    @Test
    public void testTrackUserLocation(){

        UUID userID = UUID.randomUUID();
        User user = new User(
                userID,
                "testUser1",
                "000",
                "testUser@gmail.com");

        locationService.trackUserLocation(user).join();

        assertEquals(1, user.getVisitedLocations().size());

    }

    @Test
    public void testGetAttractionRecommendedForUser() {

        User user = userService.getUser("internalUser1");
        Location location = new Location(33.881866, -115.90065);
        VisitedLocation visitedLocation = new VisitedLocation(
                user.getUserId(),
                location,
                new Date());
        user.addToVisitedLocations(visitedLocation);


       AttractionRecommendationRequest result =  locationService.AttractionRecommendedForUser("internalUser1");


        assertNotNull(result);
        assertEquals(5, result.getNearAttraction().size());
        assertEquals(33.881866, result.getUserIsHere().getLongitude());
    }






}
