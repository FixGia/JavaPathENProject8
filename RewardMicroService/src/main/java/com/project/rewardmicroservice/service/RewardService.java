package com.project.rewardmicroservice.service;

import java.util.UUID;

public interface RewardService  {

    public int getRewardPoints(final UUID attractionId, final UUID userId);

}
