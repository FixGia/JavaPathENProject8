package com.project.rewardmicroservice;


import com.project.rewardmicroservice.Exception.DataNotFoundException;
import com.project.rewardmicroservice.service.Impl.RewardServiceImpl;
import com.project.rewardmicroservice.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import rewardCentral.RewardCentral;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("## Service from Reward MicroService - Unit Test")
@ExtendWith(SpringExtension.class)
public class UnitTestService {

    @Mock
    RewardCentral rewardCentral;

    @InjectMocks
    RewardServiceImpl rewardService;



    @DisplayName("Check Method = GetRewardsPoint"
    +" supply AttractionID, UserID"
    + "when GetRewardsPoint" +
    "return RewardsPoints")
    @Test
    public void GetRewardsPointTest() throws ExecutionException, InterruptedException {

        UUID userID = UUID.randomUUID();
        UUID attractionID = UUID.randomUUID();
        int rewardsPoints = 500;


        lenient().when(rewardCentral.getAttractionRewardPoints(attractionID, userID)).thenReturn(rewardsPoints);
        rewardService.getRewardPoints(attractionID, userID);
        verify(rewardCentral, times(1)).getAttractionRewardPoints(attractionID, userID);

        assertEquals(rewardsPoints, rewardService.getRewardPoints(attractionID, userID));

    }

    @DisplayName("Check Method = GetRewardsPoint"
            +" supply AttractionID, UserID but null"
            + "when GetRewardsPoint" +
            "return Exception")
    @Test
    public void GetRewardsPointButThrowsExceptionTest() throws ExecutionException, InterruptedException {


        int rewardsPoints = 500;

        lenient().when(rewardCentral.getAttractionRewardPoints(null, null)).thenReturn(rewardsPoints);
        assertThrows(DataNotFoundException.class, () -> rewardService.getRewardPoints(null, null));
    }
}
