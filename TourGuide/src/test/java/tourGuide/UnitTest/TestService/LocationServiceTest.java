package tourGuide.UnitTest.TestService;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tourGuide.Dto.VisitedLocationRequest;
import tourGuide.config.GpsMicroService;
import tourGuide.config.RewardMicroService;
import tourGuide.config.TripDealMicroService;
import tourGuide.model.*;
import tourGuide.service.Impl.LocationServiceImpl;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;
import tourGuide.util.DistanceCalculator;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@DisplayName("## LocationService from TourGuide - Unit Test")
@ExtendWith(MockitoExtension.class)
public class LocationServiceTest {

    @Mock
    GpsMicroService gpsMicroService;
    @Mock
    RewardMicroService rewardMicroService;
    @Mock
    TripDealMicroService tripDealMicroService;


    @Mock
    RewardService rewardService;
    @Mock
    UserService userService;
    @Mock
    DistanceCalculator distanceCalculator;

    @InjectMocks
    LocationServiceImpl locationService;


    private static User userTest1;
    private static User userTest2;

    private static VisitedLocation visitedLocation;
    private static VisitedLocationRequest visitedLocationRequest;

    @BeforeEach
    public void setUp(){
        userTest1 = new User(UUID.randomUUID(), "UserTest1", "000", "User1Test@gmail.com");
        userTest2 = new User(UUID.randomUUID(), "UserTest2", "000", "User2Test@gmail.com");
        //     UserReward userReward = new UserReward(visitedLocation,new Attraction("blabla","city","state", UUID.randomUUID()),100);
        userTest1.setUserRewards(null);

        visitedLocation = new VisitedLocation(userTest1.getUserId(), new Location(33.817595, -117.922008),new Date());
        visitedLocationRequest= new VisitedLocationRequest(userTest1.getUserId(), new Location(33.817595, -117.922008), new Date());


    }

    @Test
    public void GetLocationWithMicroServiceTest(){


        lenient().when(userService.getUser(any(String.class))).thenReturn(userTest1);

        lenient().when(gpsMicroService.getLocation(any(UUID.class))).thenReturn(visitedLocationRequest);

        Location result = locationService.getUserLocation(userTest1.getUserName());

        assertEquals(0, userTest1.getVisitedLocations().size());
        assertEquals(visitedLocationRequest.getLocation(), result);


    }


    @Test
    public void GetLocationWithoutMicroServiceTest(){

       VisitedLocation anOtherVisitedLocation = new VisitedLocation(userTest1.getUserId(), new Location(89.887895, -179.122008),new Date());
       userTest1.getVisitedLocations().add(anOtherVisitedLocation);

        lenient().when(userService.getUser(any(String.class))).thenReturn(userTest1);

        lenient().when(gpsMicroService.getLocation(any(UUID.class))).thenReturn(visitedLocationRequest);

        Location result = locationService.getUserLocation(userTest1.getUserName());

        assertEquals(1, userTest1.getVisitedLocations().size());
        assertEquals(anOtherVisitedLocation.getLocation(), result);

    }

    @Test
    public void GetCurrentLocationForAllUsersTest(){


      Map<String, Location> resultMap = locationService.getCurrentLocationForAllUsers();
      verify(userService, times(1)).getAllUsers();
      assertNotNull(resultMap);

    }

    @Test
    public void TrackUserLocationTest(){

        when(gpsMicroService
                .getLocation(any(UUID.class)))
                .thenReturn(visitedLocationRequest);


        // WHEN
        locationService.trackUserLocation(userTest1).join();
        verify(rewardService, times(1)).calculateRewards(userTest1);
        assertNull(userTest1.getUserRewards());
        verify(gpsMicroService, times(1)).getLocation(userTest1.getUserId());
    }

}



