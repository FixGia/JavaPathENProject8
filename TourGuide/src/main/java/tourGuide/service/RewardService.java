package tourGuide.service;

import tourGuide.model.User;
import tourGuide.model.UserReward;

import java.util.List;

public interface RewardService {

   List<UserReward> getUserRewards(String userName);

   void calculateRewards(User user);

}
