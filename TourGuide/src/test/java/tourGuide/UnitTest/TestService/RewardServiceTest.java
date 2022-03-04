package tourGuide.UnitTest.TestService;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tourGuide.Dto.AttractionRequest;
import tourGuide.config.GpsMicroService;
import tourGuide.config.RewardMicroService;
import tourGuide.model.*;
import tourGuide.service.Impl.RewardServiceImpl;
import tourGuide.service.UserService;
import tourGuide.util.DistanceCalculator;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@DisplayName("## RewardServiceTest from TourGuide - Unit Test")
@ExtendWith(MockitoExtension.class)
public class RewardServiceTest {



    @InjectMocks
    RewardServiceImpl rewardService;

    @Mock
    RewardMicroService rewardMicroService;

    @Mock
    DistanceCalculator distanceCalculator;

    @Mock
    GpsMicroService gpsMicroService;
    @Mock
    UserService userService;

    private static User userTest1;
    private static UserReward userReward;
    private static Attraction attraction;
    private static AttractionRequest attractionRequest;
    private static VisitedLocation visitedLocation;
    private static Location location;
    private static List<AttractionRequest> attractions;


    @BeforeEach
    public void setUp() {

        userTest1 = new User(UUID.randomUUID(), "testUser1", "000", "testUser1@gmail.com");
        userTest1.addToVisitedLocations(visitedLocation);

        attraction = new Attraction("attractionName",
                "City",
                "state",
                new Location(45.454545,56.565656),
                UUID.randomUUID());

        location = new Location(33.817595, -117.922008);

        userReward = new UserReward(visitedLocation, attraction, 300);

        visitedLocation = new VisitedLocation(UUID.randomUUID(),
                location,
                new Date());

        attractionRequest = new AttractionRequest("attractionName",
                "City",
                "state",
                attraction.getAttractionId(),
                new Location(45.454545,56.565656));

        attractions = new CopyOnWriteArrayList<>();
        attractions.add(attractionRequest);


    }



    @Test
    public void TestGetUserRewards() {

        lenient().when(userService.getUser(any(String.class))).thenReturn(userTest1);
        List<UserReward> userRewardList = rewardService.getUserRewards("userTest1");
        Assertions.assertNotNull(userRewardList);

    }

    @Test
    public void TestGetAttractionsRewardPoints() {

        lenient().when(rewardMicroService.getRewardPoint(attraction.getAttractionId(),userTest1.getUserId())).thenReturn(500);
        AttractionRequest attractionRequest = new AttractionRequest();
        attractionRequest.setAttractionId(attraction.getAttractionId());
        int result = rewardService.getAttractionsRewardPoints(userTest1, attractionRequest);
        assertEquals(500, result);

    }

    @Test
    public void TestGetRewardsPoint(){

        lenient().when(rewardMicroService.getRewardPoint(attraction.getAttractionId(),userTest1.getUserId())).thenReturn(500);
        UserReward resultReward = rewardService.getRewardsPoint(userTest1,visitedLocation,attractionRequest);

        assertNotNull(resultReward);
        assertEquals(500,resultReward.getRewardPoints());

    }


    @Test
    public void TestCalculateRewards(){
        lenient().when(gpsMicroService.getAttractions()).thenReturn(attractions);
        lenient().when(distanceCalculator.isWithinAttractionProximity(attractionRequest,location)).thenReturn(true);
        rewardService.calculateRewards(userTest1);
        assertNotNull(userTest1.getUserRewards());

    }

}
