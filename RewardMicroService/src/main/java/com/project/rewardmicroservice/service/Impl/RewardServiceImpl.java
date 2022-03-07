package com.project.rewardmicroservice.service.Impl;

import com.project.rewardmicroservice.Exception.DataNotFoundException;
import com.project.rewardmicroservice.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.zip.DataFormatException;

@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

    private final RewardCentral rewardCentral;
    ExecutorService executorService = Executors.newFixedThreadPool(1000);

    public RewardServiceImpl(RewardCentral rewardCentral) {
        this.rewardCentral = rewardCentral;
    }

    /**
     * Gets the Attraction's RewardPoint
     *
     * @param attractionId the attraction id
     * @param userId       the user id
     * @return the rewardPoint for this attraction
     */
    public int getRewardPoints(final UUID attractionId, final UUID userId) throws ExecutionException, InterruptedException {

        if(userId == null || attractionId == null) {
            throw new DataNotFoundException("AttractionId or userId was null");
        }

        CompletableFuture<Integer> rewardPoints = CompletableFuture.supplyAsync(() -> rewardCentral.getAttractionRewardPoints(attractionId, userId),executorService);
        return rewardPoints.get();
    }
}
