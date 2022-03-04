package com.project.rewardmicroservice;


import com.project.rewardmicroservice.controller.RewardController;
import com.project.rewardmicroservice.service.RewardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##Controller from Gps MicroService - Unit Test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(RewardController.class)
public class UnitTestController {

    @MockBean
    private RewardService rewardService;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @BeforeEach
    public void setUp() {

        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void GetRewardsRequestTest() throws Exception {

        UUID userID = UUID.fromString("c8615b95-05d0-44aa-952a-531c91215967");
        UUID attractionID = UUID.randomUUID();

        lenient().when(rewardService.getRewardPoints(attractionID, userID)).thenReturn(500);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/rewards//getRewardPoint/" + attractionID + "/" + userID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    public void GetRewardsRequestTestButUserIDIsNull() throws Exception {


        UUID attractionID = UUID.randomUUID();

        lenient().when(rewardService.getRewardPoints(attractionID, null)).thenReturn(500);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/rewards//getRewardPoint/" + attractionID + "/" + null)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertNotNull(mvcResult);
    }
    @Test
    public void GetRewardsRequestTestButAttractionIDIsNull() throws Exception {

        UUID userID = UUID.randomUUID();


        lenient().when(rewardService.getRewardPoints(null, userID)).thenReturn(500);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/rewards//getRewardPoint/" + null + "/" + userID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertNotNull(mvcResult);
    }

}
