package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.Dto.AttractionRequest;
import tourGuide.proxy.GpsMicroService;
import tourGuide.proxy.RewardMicroService;
import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;
import tourGuide.util.DistanceCalculator;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
public class RewardServiceImpl implements RewardService {

    private final UserService userService;
    private final RewardMicroService rewardMicroService;
    private final DistanceCalculator distanceCalculator;
    private final GpsMicroService gpsMicroService;
    ExecutorService executorService = Executors.newFixedThreadPool(1000);


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

    public int getAttractionRewardPoints(User user, AttractionRequest attraction) {

       return rewardMicroService.getRewardPoint(attraction.getAttractionId(),user.getUserId());

    }



    private void getRewardsPointAfterVerify(final User user, VisitedLocation visitedLocation, AttractionRequest attraction) {

       double distance = distanceCalculator.getDistanceInMiles(attraction.getLocation(), visitedLocation.getLocation());
       int proximityBufferMiles = 10;

       if(distance <= proximityBufferMiles) {
            UserReward userReward = new UserReward(
                    visitedLocation,
                    new Attraction(attraction.getAttractionName(), attraction.getCity(), attraction.getState(), attraction.getLocation(), attraction.getAttractionId()), (int) distance);
            user.getUserRewards().add(userReward);

        }

    }

    private void calculateRewards(User user) {

        CopyOnWriteArrayList<VisitedLocation> visitedLocationsList = new CopyOnWriteArrayList<>(user.getVisitedLocations());

        CopyOnWriteArrayList<AttractionRequest> attractions = new CopyOnWriteArrayList<>(gpsMicroService.getAttractions());

        for(VisitedLocation visitedLocation : visitedLocationsList) {
            for(AttractionRequest attraction : attractions) {
                if(user.getUserRewards().stream().noneMatch(r -> r.attraction.attractionName.equals(attraction.getAttractionName()))) {
                  getRewardsPointAfterVerify(user,visitedLocation,attraction);

                }
            }
        }

    }

    public CompletableFuture<?> calculateRewardAsync(final User user ){
        return CompletableFuture.runAsync(() -> this.calculateRewards(user), executorService);
    }

}

