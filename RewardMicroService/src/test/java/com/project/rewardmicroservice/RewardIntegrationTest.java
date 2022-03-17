package com.project.rewardmicroservice;


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

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("##Service from Gps MicroService - Integration Test")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardIntegrationTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private static UUID userID;

    private static UUID attractionID;



    @BeforeEach
    public void setUp(){
        userID = UUID.fromString("c8615b95-05d0-44aa-952a-531c91215967");

        attractionID = UUID.fromString("c8615b95-05d0-44aa-952a-531c91215969");
    }


    @Test
    public void testGetRewardPoints(){

        ResponseEntity<Integer> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/rewards/getRewardPoint/"+attractionID+"/"+userID, Integer.class);

        assertNotNull(response);

        assertEquals(HttpStatus.OK.value(),
                response.getStatusCodeValue());

    }

    @Test
    public void testGetRewardPointsButUserIdIsNull(){


        ResponseEntity<Integer> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/rewards/getRewardPoint/"+attractionID+"/"+ 0, null);

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST.value(),
                response.getStatusCodeValue());

    }

    @Test
    public void testGetRewardPointsButUserIdIsInvalid(){

        ResponseEntity<Integer> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/rewards/getRewardPoint/"+attractionID+"/"+ "sdgfhgdfhdfhdf", null);

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST.value(),
                response.getStatusCodeValue());

    }

    @Test
    public void testGetRewardPointsButAttractionIdIsInvalid(){

        ResponseEntity<Integer> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/rewards/getRewardPoint/"+"fsgsgsdgsdvsdvsd"+"/"+ userID, null);

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST.value(),
                response.getStatusCodeValue());

    }

    @Test
    public void testGetRewardPointsButAttractionIdIsNull(){

        ResponseEntity<Integer> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/rewards/getRewardPoint/"+ 0 + "/" + userID, null);

        assertNotNull(response);

        assertEquals(HttpStatus.BAD_REQUEST.value(),
                response.getStatusCodeValue());

    }





}
