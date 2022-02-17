package com.project.gpsmicroservice.util;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.model.Location;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;

@Component
public class GpsMapper {

    public VisitedLocationRequest mapVisitedLocationToVisitedLocationRequest(final VisitedLocation visitedLocation) {

        return new VisitedLocationRequest(
                visitedLocation.userId,
                visitedLocation.location,
                visitedLocation.timeVisited);
    }

    public AttractionRequest mapAttractionTOAttractionRequest (final Attraction attraction) {

        return  new AttractionRequest(
                attraction.attractionName,
                attraction.city,
                attraction.state,
                attraction.attractionId,
                new Location(attraction.longitude, attraction.latitude));
    }

}
