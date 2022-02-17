package com.project.gpsmicroservice.service;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;
import gpsUtil.location.Attraction;

import java.util.List;
import java.util.UUID;

public interface GpsService {

    public VisitedLocationRequest getUserLocation(final UUID userId);

    public List<AttractionRequest> getAttractions();
}
