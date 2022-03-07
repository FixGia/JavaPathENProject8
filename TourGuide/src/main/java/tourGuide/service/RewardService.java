package tourGuide.service;

import tourGuide.Dto.AttractionRequest;
import tourGuide.model.Attraction;
import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface RewardService {

   List<UserReward> getUserRewards(String userName);


   void calculateRewards(User user);

   public void getAttractionsRewardPoints(User user, AttractionRequest attraction, UserReward userReward);

   public int getAttractionRewardPoints(User user, AttractionRequest attraction);

   public CompletableFuture<?> calculateRewardAsync(final User user );
}
