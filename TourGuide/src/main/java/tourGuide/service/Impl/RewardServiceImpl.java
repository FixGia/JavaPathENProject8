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
    private final ExecutorService executorService = Executors.newFixedThreadPool(1000);


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

    public int getAttractionsRewardPoints(User user, AttractionRequest attraction) {

        return rewardMicroService.getRewardPoint(attraction.getAttractionId(), user.getUserId());


    }

    public UserReward getRewardsPoint(final User user, VisitedLocation visitedLocation, AttractionRequest attraction) {

        return new UserReward(
                visitedLocation,
                new Attraction(attraction.getAttractionName(), attraction.getCity(), attraction.getState(),attraction.getLocation(),attraction.getAttractionId()),
                rewardMicroService.getRewardPoint(attraction.getAttractionId(), user.getUserId()));

    }

    public CompletableFuture<?> calculateRewardsWithCompletableFuture (User user) {

        return CompletableFuture.runAsync(() -> calculateRewards(user),
                executorService);
    }
    public void calculateRewards(User user) {


        CopyOnWriteArrayList<VisitedLocation> userLocations = new CopyOnWriteArrayList<>(user.getVisitedLocations());

        CopyOnWriteArrayList<AttractionRequest> attractions = new CopyOnWriteArrayList<>(gpsMicroService.getAttractions());

        userLocations.forEach(visitedLocation -> {
            attractions.stream().filter(attractionRequest ->
                    distanceCalculator.isWithinAttractionProximity(attractionRequest, attractionRequest.getLocation()))
                    .forEach(attractionRequest -> {
                        user.addUserReward(getRewardsPoint(user,visitedLocation,attractionRequest)
                        );
                    } );
        });

                    }
                }

