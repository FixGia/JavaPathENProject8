package com.project.gpsmicroservice.controller;

import com.project.gpsmicroservice.service.LocationService;
import gpsUtil.location.VisitedLocation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GpsController {

 private LocationService locationService;

    public GpsController(LocationService locationService) {
        this.locationService = locationService;
    }

    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) {

     return userName;

    }
}
