package com.project.rewardmicroservice.controller;


import com.project.rewardmicroservice.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/rewards")
@Slf4j
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @RequestMapping("/getRewardPoint/{attractionId}/{userId}")
    public int getRewardPoints(@PathVariable final UUID attractionId, @PathVariable final UUID userId) {

        int rewardPoint = rewardService.getRewardPoints(attractionId, userId);
        return rewardPoint;
    }
}
