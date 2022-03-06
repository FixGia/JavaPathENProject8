package tourGuide.service;

import tourGuide.Dto.AttractionRequest;
import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface RewardService {

   List<UserReward> getUserRewards(String userName);


   void calculateRewards(User user);

   public CompletableFuture<?> calculateRewardsWithCompletableFuture (User user);

   int getAttractionsRewardPoints(User user, AttractionRequest attraction);

}
