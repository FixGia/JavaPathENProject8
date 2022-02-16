package com.project.gpsmicroservice.service;
import com.project.gpsmicroservice.Exception.DataNotFoundException;
import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
public class LocationServiceImpl implements LocationService {

private final GpsUtil gpsUtil;

    public LocationServiceImpl(GpsUtil gpsUtil) {
        this.gpsUtil = gpsUtil;
    }

    //TODO create my own exception to replace EXCEPTION
    public VisitedLocation getUserLocation(final UUID userId) {

        if (userId == null) {
            throw new DataNotFoundException ("User not found");
        }
        VisitedLocation visitedLocation = gpsUtil
                .getUserLocation(userId);

        return visitedLocation;
    }

}
