package com.project.gpsmicroservice;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.controller.GpsController;
import com.project.gpsmicroservice.model.Location;
import com.project.gpsmicroservice.service.GpsService;
import org.junit.jupiter.api.Assertions;
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
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##Controller from Gps MicroService - Unit Test")
@ExtendWith(SpringExtension.class)
@WebMvcTest(GpsController.class)
public class ControllerTest {


    @MockBean
    private GpsService gpsService;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;


    @BeforeEach
    public void setUp(){

        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }


    @Test
    @DisplayName("Check Method = GetUserLocation"
            +" supply UserId,"
            +" when GetUserLocation,"+
            " return status : 200 OK ")
    public void GetUserLocationRequestTest() throws Exception {

        UUID userID = UUID.fromString("c8615b95-05d0-44aa-952a-531c91215967");

        VisitedLocationRequest visitedLocationRequest = new VisitedLocationRequest(userID, new Location(33.817595, -117.922008), new Date());
        lenient().when(gpsService.getUserLocation(userID)).thenReturn(visitedLocationRequest);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/gps/userLocation/"+userID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);

    }

    @Test
    @DisplayName("Check Method = GetUserLocation"
            +" supply UserId,"
            +" when GetUserLocation,"+
            " return status : BAD REQUEST ")
    public void GetUserLocationRequestButUserIdIsNullTest() throws Exception{

        MvcResult mvcResult = mvc
                .perform(MockMvcRequestBuilders
                        .get("/gps/userLocation/"+"")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();


        assertNotNull(mvcResult);

    }

    @Test
    @DisplayName("Check Method = GetAttractions"
            +" when GetAttractions,"+
            " return status : 200 OK ")
    public void GetAttractionsRequestTest() throws Exception {
        AttractionRequest attractionRequest1 = new AttractionRequest("testName1",
                "testCity1",
                "testState1",
                UUID.randomUUID(),
                new Location(33.817595, -117.922008));


        AttractionRequest attractionRequest2 = new AttractionRequest(
                "testName2",
                "testCity2",
                "testState2",
                UUID.randomUUID(),
                new Location(33.817595, -117.922008));
        List<AttractionRequest> attractionRequestList = Arrays.asList(attractionRequest1,attractionRequest2);

        lenient().when(gpsService.getAttractions()).thenReturn(attractionRequestList);

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/gps/attractions")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);

    }
}

