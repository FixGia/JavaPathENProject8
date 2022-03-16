package tourGuide.service;

import tourGuide.Dto.AttractionRequest;
import tourGuide.model.User;
import tourGuide.model.UserReward;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface RewardService {

   List<UserReward> getUserRewards(String userName);


   int getAttractionRewardPoints(User user, AttractionRequest attraction);

    CompletableFuture<?> calculateRewardAsync(final User user );
}
