package com.project.gpsmicroservice.service;

import com.project.gpsmicroservice.Dto.AttractionRequest;
import com.project.gpsmicroservice.Dto.VisitedLocationRequest;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

public interface GpsService {

    public VisitedLocationRequest submitUserLocation(final UUID userId) throws ExecutionException, InterruptedException;

    public List<AttractionRequest> getAttractions();
}
