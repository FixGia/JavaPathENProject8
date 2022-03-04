package com.project.gpsmicroservice;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.Exception.DataNotFoundException;
import com.project.gpsmicroservice.model.Location;
import com.project.gpsmicroservice.service.GpsServiceImpl;
import com.project.gpsmicroservice.util.GpsMapper;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.lenient;

@DisplayName("Service from Gps MicroService Unit Test")
@ExtendWith(MockitoExtension.class)
public class UnitTestService {

    @Mock
    private GpsUtil gpsUtil;

    @Mock
    private GpsMapper gpsMapper;

    private static UUID userID;

    private static Location location2;

    private static VisitedLocation visitedLocation;

    private static Attraction attraction1;

    private static Attraction attraction2;


    @InjectMocks
    GpsServiceImpl gpsService;

    @BeforeEach
    public void setUp(){

        userID = UUID.fromString("c8615b95-05d0-44aa-952a-531c91215967");

        gpsUtil.location.Location location1 = new gpsUtil.location.Location(33.817595, -117.922008);
        location2 = new Location(43.582767, -110.821999);

        Date date = new Date();

        visitedLocation = new VisitedLocation(userID, location1, date);

        attraction1 = new Attraction("nameTest1", "CityTest1", "StateTest", 33.817595, -117.922008);
        attraction2 = new Attraction("NameTest2", "CityTest2" , "StateTEst2",
                211.021844, -54.18879);

    }



    @DisplayName("Check Method = GetUserLocation"
            +" supply UserId,"
            +" when GetUserLocation,"+
            " return User expected location match with VisitedLocationRequest")
    @Test
    public void TestGetUserLocation(){

        VisitedLocationRequest visitedLocationRequest = new VisitedLocationRequest(userID,location2,new Date());

        lenient().when( gpsUtil.getUserLocation(any(UUID.class))).thenReturn(visitedLocation);

        lenient().when( gpsMapper.mapVisitedLocationToVisitedLocationRequest(any(VisitedLocation.class))).thenReturn(visitedLocationRequest);

        VisitedLocationRequest resultVisitedLocation = gpsService.getUserLocation(userID);

        assertNotNull(resultVisitedLocation);
        assertEquals(visitedLocationRequest, resultVisitedLocation);
    }

    @DisplayName("Check Method = GetUserLocation"
            +" supply UserId but null"
            +" when GetUserLocation,"+
            " return DataNotFoundException")
    @Test
    public void TestGetUserLocationButUserNotFound(){

        assertThrows(DataNotFoundException.class, ()
                -> gpsService.getUserLocation(null));
    }

    @DisplayName("Check Method = GetAttractions"
            +" when GetAttractions,"+
            " return Attraction's List")
    @Test
    public void TestGetAttractions(){

        AttractionRequest attractionRequest1 = new AttractionRequest("testName1",
                "testCity1",
                "testState1",
                attraction1.attractionId,
                new Location(33.817595, -117.922008));


        AttractionRequest attractionRequest2 = new AttractionRequest(
                "testName2",
                "testCity2",
                "testState2",
                attraction2.attractionId,
                new Location(33.817595, -117.922008));
        List<AttractionRequest> attractionRequestList = Arrays.asList(attractionRequest1,attractionRequest2);

        lenient().when(gpsUtil.getAttractions()).thenReturn(Arrays.asList(attraction1, attraction2));
        lenient().when(gpsMapper.mapAttractionTOAttractionRequest(attraction1)).thenReturn(attractionRequest1);
        lenient().when(gpsMapper.mapAttractionTOAttractionRequest(attraction2)).thenReturn(attractionRequest2);

        List<AttractionRequest> resultAttractionRequests = gpsService.getAttractions();

        assertNotNull(resultAttractionRequests);
        assertEquals(attractionRequestList, resultAttractionRequests);
        assertEquals(2, resultAttractionRequests.size());

    }
}
