package com.project.tripdealsmicroservice.service;

import com.project.tripdealsmicroservice.dto.ProviderRequest;

import java.util.List;
import java.util.UUID;

public interface TripDealService {

    List<ProviderRequest> getProviders(final String apiKey,
                                       final UUID userId,
                                       int adults,
                                       int children,
                                       int nightsStay,
                                       int rewardPoints);
}
