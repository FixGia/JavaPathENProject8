package com.project.gpsmicroservice.util;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import com.project.gpsmicroservice.model.Location;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Component
public class GpsMapper {

    public VisitedLocationRequest mapVisitedLocationToVisitedLocationRequest(final CompletableFuture<VisitedLocation> visitedLocation) throws ExecutionException, InterruptedException {

        return new VisitedLocationRequest(
                visitedLocation.get().userId,
                visitedLocation.get().location,
                visitedLocation.get().timeVisited);
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
