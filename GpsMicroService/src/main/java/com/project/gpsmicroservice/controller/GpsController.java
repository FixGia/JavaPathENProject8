package com.project.gpsmicroservice.controller;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.Exception.DataNotFoundException;
import com.project.gpsmicroservice.service.GpsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/gps")
@Slf4j
public class GpsController {

 private final GpsService gpsService;

    public GpsController(GpsService gpsService) {
        this.gpsService = gpsService;
    }

    @GetMapping("/userLocation/{userID}")
    public VisitedLocationRequest getLocation(@PathVariable("userID") final UUID userId) throws ExecutionException, InterruptedException {


        VisitedLocationRequest userLocation = gpsService.submitUserLocation(userId);

        if (userLocation == null) {
            throw  new DataNotFoundException("Fail to get userLocation");
        }

        log.info("Get userLocation was a success");
        return userLocation;
    }

    @GetMapping("/attractions")
    public List<AttractionRequest> getAttractions(){

        return gpsService.getAttractions();
    }
}
