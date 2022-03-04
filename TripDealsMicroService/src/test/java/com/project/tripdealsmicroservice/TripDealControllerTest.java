package com.project.tripdealsmicroservice;


import com.project.tripdealsmicroservice.controller.TripDealController;
import com.project.tripdealsmicroservice.dto.ProviderRequest;
import com.project.tripdealsmicroservice.service.TripDealService;
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

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##Controller from Gps MicroService - Unit Test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(TripDealController.class)
public class TripDealControllerTest {

    @MockBean
    private TripDealService tripDealService;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    @DisplayName("Check Method = GetTripDeal"+
            "supply UserId,"+"when GetTripDeal," +
            "return status : 200 OK ")
    public void GetTripDealRequestTest() throws Exception {

        UUID userIdTest = UUID.randomUUID();
        UUID tripIDTest1 = UUID.randomUUID();
        UUID tripIDTest2 = UUID.randomUUID();

        ProviderRequest provider1 = new ProviderRequest("providerTest1", 100, tripIDTest1);
        ProviderRequest provider2 = new ProviderRequest("providerTest2", 200, tripIDTest2);
        String apiKey = "apiKey";
        lenient().when(tripDealService
                        .getProviders(
                                apiKey, userIdTest, 100, 100, 100, 100))
                .thenReturn(Arrays.asList(provider1, provider2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/tripDeals/providers/"+apiKey+"/"+userIdTest+"/"+100+"/"+100+"/"+100+"/"+100)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    @DisplayName("Check Method = GetTripDeal"+
            "supply UserId,"+"when GetTripDeal," +
            "return status : 200 OK ")
    public void GetTripDealRequestButUserIdIsNullTest() throws Exception {

        UUID tripIDTest1 = UUID.randomUUID();
        UUID tripIDTest2 = UUID.randomUUID();

        ProviderRequest provider1 = new ProviderRequest("providerTest1", 100, tripIDTest1);
        ProviderRequest provider2 = new ProviderRequest("providerTest2", 200, tripIDTest2);
        String apiKey = "apiKey";
        lenient().when(tripDealService
                        .getProviders(
                                apiKey, null, 100, 100, 100, 100))
                .thenReturn(Arrays.asList(provider1, provider2));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/tripDeals/providers/"+apiKey+"/"+null+"/"+100+"/"+100+"/"+100+"/"+100)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertNotNull(mvcResult);
    }

}
