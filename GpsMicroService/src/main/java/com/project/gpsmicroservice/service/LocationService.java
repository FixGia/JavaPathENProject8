package com.project.gpsmicroservice.service;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;

import java.util.UUID;

public interface LocationService {

    public VisitedLocation getUserLocation(final UUID userId);

    public Attraction getAttractions();
}
