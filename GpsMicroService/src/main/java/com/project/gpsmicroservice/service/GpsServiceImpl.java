package com.project.gpsmicroservice.service;
import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.Exception.DataNotFoundException;
import com.project.gpsmicroservice.util.GpsMapper;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@Slf4j
public class GpsServiceImpl implements GpsService {

private final GpsUtil gpsUtil;
private final GpsMapper gpsMapper;

    public GpsServiceImpl(GpsUtil gpsUtil, GpsMapper gpsMapper) {
        this.gpsUtil = gpsUtil;
        this.gpsMapper = gpsMapper;
    }


    /**
     * Get the User's location
     * @param userID the user id
     * @return the user's location
     */
    public VisitedLocationRequest getUserLocation(final UUID userID) {

        if (userID == null) {
            throw new DataNotFoundException ("UserID wasn't found");
        }
        VisitedLocation visitedLocation = gpsUtil
                .getUserLocation(userID);

        log.info("Get Location's User with UUID {} was a success", userID);
        return gpsMapper.mapVisitedLocationToVisitedLocationRequest(visitedLocation) ;
    }

    /**
     * Get all Attractions
     *
     * @return a list of all attractions
     */
    @Override
    public List<AttractionRequest> getAttractions() {

        List<AttractionRequest> attractionRequestList = new ArrayList<>();
        List<Attraction> attractions = gpsUtil.getAttractions();

        attractions.forEach(attraction -> {
            attractionRequestList.add(gpsMapper.mapAttractionTOAttractionRequest(attraction));
        });
        log.info("Get All attractions was a success");
        return attractionRequestList;
    }

}
