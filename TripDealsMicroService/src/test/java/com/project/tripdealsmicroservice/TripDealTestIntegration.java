package com.project.tripdealsmicroservice;


import com.project.tripdealsmicroservice.service.TripDealService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("##Service from Gps MicroService - Integration Test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripDealTestIntegration {



    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    private static UUID userID;
    private final static String apiKey = "apiKey";

    @BeforeEach
    public void setUp() {

        userID= UUID.fromString("c8615b95-05d0-44aa-952a-531c91215967");

    }


    @DisplayName("Check Method = GetTripDeal"
            +" supply UserId"
            +" when Get TripDeal,"+
            " return 200 OK + List<ProviderRequest> ")
    @Test
    public void GetTripDealTest(){

        ResponseEntity<List> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/tripDeals/providers/"+apiKey+"/"+userID+"/"+100+"/"+100+"/"+100+"/"+100, List.class);

        assertNotNull(response);

        assertEquals(HttpStatus.OK.value(),
                response.getStatusCodeValue());


    }
    @DisplayName("Check Method = GetTripDeal"
            +" supply UserId"
            +" when Get TripDeal,"+
            " return BadRequest ")
    @Test
    public void GetTripDealButUserIdIsInvalidTest(){

        ResponseEntity<List> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/tripDeals/providers/"+apiKey+"/"+"sgsdgsgsg"+"/"+100+"/"+100+"/"+100+"/"+100, null);

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST.value(),
                response.getStatusCodeValue());

    }

    @DisplayName("Check Method = GetTripDeal"
            +" supply UserId"
            +" when Get TripDeal,"+
            " return BadRequest ")
    @Test
    public void GetTripDealButUserIdIsNullTest(){

        ResponseEntity<List> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/tripDeals/providers/"+apiKey+"/"+null+"/"+100+"/"+100+"/"+100+"/"+100, null);

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST.value(),
                response.getStatusCodeValue());

    }

}
