package tourGuide.service;

import gpsUtil.location.Attraction;
import tourGuide.model.User;

public interface RewardService {

    public void calculateRewards(User user);

    public int getRewardPoints(Attraction attraction, User user);
}
