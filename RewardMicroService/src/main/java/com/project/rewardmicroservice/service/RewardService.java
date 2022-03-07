package com.project.rewardmicroservice.service;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface RewardService  {

    public int getRewardPoints(final UUID attractionId, final UUID userId) throws ExecutionException, InterruptedException;

}
