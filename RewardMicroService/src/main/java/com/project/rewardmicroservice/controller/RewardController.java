package com.project.rewardmicroservice.controller;


import com.project.rewardmicroservice.service.RewardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/rewards")
@Slf4j
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }

    @GetMapping("/getRewardPoint/{attractionId}/{userId}")
    public int getRewardPoints(@PathVariable final UUID attractionId, @PathVariable final UUID userId) throws ExecutionException, InterruptedException {

        return rewardService.getRewardPoints(attractionId, userId);
    }
}
