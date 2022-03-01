package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.Dto.AttractionRequest;
import tourGuide.config.GpsMicroService;
import tourGuide.config.RewardMicroService;
import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;
import tourGuide.util.DistanceCalculator;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@Service
public class RewardServiceImpl implements RewardService {

    private final UserService userService;
    private final RewardMicroService rewardMicroService;
    private final DistanceCalculator distanceCalculator;
    private final GpsMicroService gpsMicroService;

    public RewardServiceImpl(UserService userService, RewardMicroService rewardMicroService, DistanceCalculator distanceCalculator, GpsMicroService gpsMicroService) {
        this.userService = userService;
        this.rewardMicroService = rewardMicroService;
        this.distanceCalculator = distanceCalculator;

        this.gpsMicroService = gpsMicroService;
    }

    @Override
    public List<UserReward> getUserRewards(String userName) {

        User user = userService.getUser(userName);
        if (user != null){

            log.info("User's reward belong to {} was found", userName);
            return user.getUserRewards();
        }
        log.error("User's reward belong to {} wasn't found", userName);
        return Collections.emptyList();

    }

    public int getAttractionsRewardPoints(User user, Map.Entry<AttractionRequest,Double> attractionsMapEntry) {

        return rewardMicroService.getRewardPoint(attractionsMapEntry.getKey().getAttractionId(), user.getUserId());


    }


    public void calculateRewards(User user) {


        // Variant Thread-Safe of ArrayList
        CopyOnWriteArrayList<VisitedLocation> userLocations = new CopyOnWriteArrayList<>();
        userLocations.addAll(user.getVisitedLocations());

        CopyOnWriteArrayList<AttractionRequest> attractions= new CopyOnWriteArrayList<>();
        attractions.addAll(gpsMicroService.getAttractions());


        for(VisitedLocation visitedLocation : userLocations) {
            for(AttractionRequest attraction : attractions) {
              if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.getAttractionName())).count() == 0) {
                    if(distanceCalculator.nearAttraction(visitedLocation.getLocation(), attraction.getLocation())) {
                      user.addUserReward(new UserReward(visitedLocation,
                               new Attraction(attraction.getAttractionName(), attraction.getCity(), attraction.getState(), attraction.getAttractionId()),
                              rewardMicroService.getRewardPoint(attraction.getAttractionId(), user.getUserId())));

                    }
                }
            }
        }
    }


}
