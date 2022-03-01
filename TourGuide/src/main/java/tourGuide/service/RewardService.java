package tourGuide.service;

import tourGuide.Dto.AttractionRequest;
import tourGuide.model.User;
import tourGuide.model.UserReward;

import java.util.List;
import java.util.Map;

public interface RewardService {

   List<UserReward> getUserRewards(String userName);


   void calculateRewards(User user);

   int getAttractionsRewardPoints(User user, Map.Entry<AttractionRequest,Double> attractionsMapEntry);

}
