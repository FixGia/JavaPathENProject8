package com.project.rewardmicroservice.service.Impl;

import com.project.rewardmicroservice.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;
import java.util.zip.DataFormatException;

@Service
@Slf4j
public class RewardServiceImpl implements RewardService {

    private final RewardCentral rewardCentral;

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
    public int getRewardPoints(final UUID attractionId, final UUID userId) {
        //TODO user try catch ??
        int rewardPoint = rewardCentral.getAttractionRewardPoints(attractionId, userId);
        log.info("RewardPoint to user's id {} from attraction's with id {}", userId, attractionId);
        return rewardPoint;
    }
}
