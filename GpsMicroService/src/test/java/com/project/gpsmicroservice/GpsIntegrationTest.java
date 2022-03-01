package com.project.gpsmicroservice;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.Exception.DataNotFoundException;
import com.project.gpsmicroservice.service.GpsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("##Service from Gps MicroService - Integration Test")
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class GpsIntegrationTest {

    @Autowired
    private GpsService gpsService;

    private static UUID userId;

    @BeforeEach
    public void setUp() {

        userId= UUID.fromString("c8615b95-05d0-44aa-952a-531c91215967");

    }


    @DisplayName("Check Method = GetUserLocation"
            +" supply UserId,"
            +" when Get UserLocation,"+
            " return User expected location match with VisitedLocationRequest")
    @Test
    public void testGetUserLocation() {


        VisitedLocationRequest requestResult = gpsService.getUserLocation(userId);

        assertNotNull(requestResult);
        assertNotNull(requestResult.getLocation().longitude);

    }

    @DisplayName("Check Method = GetUserLocation"
            +" supply UserId,"
            +" when Get UserLocation,"+
            " return Exception")
    @Test
    public void testGetUserLocationUserIdNullValue() {
        assertThrows(DataNotFoundException.class, ()
                -> gpsService.getUserLocation(null));
    }


    @DisplayName("Check Method = GetAttractions "
            + " supply an User list,"
            + " WHEN gpsService.AttractionRecommendedForUser,"
            + " then return Attractions list as expected")
    @Test
    public void testGetAttractions() {


        List<AttractionRequest> attractionList = gpsService.getAttractions();

        List<AttractionRequest> result = gpsService
                .getAttractions();

        assertNotNull(result);
        assertEquals(attractionList.size(), result.size());
        assertTrue(result.size() > 0);

    }


}
