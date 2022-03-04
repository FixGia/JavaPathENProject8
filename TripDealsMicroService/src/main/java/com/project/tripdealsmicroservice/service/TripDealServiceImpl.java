package com.project.tripdealsmicroservice.service;

import com.project.tripdealsmicroservice.dto.ProviderRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class TripDealServiceImpl implements TripDealService {


    private final TripPricer tripPricer;

    public TripDealServiceImpl(TripPricer tripPricer) {
        this.tripPricer = tripPricer;
    }

    @Override
    public List<ProviderRequest> getProviders(final String apiKey,
                                       final UUID userId,
                                       int adults,
                                       int children,
                                       int nightsStay,
                                       int rewardPoints) {

        List<ProviderRequest> providerRequestList = new ArrayList<>();
        List<Provider> providerList = tripPricer.getPrice(apiKey,
                userId,
                adults,
                children,
                nightsStay,
                rewardPoints);

        providerList.forEach(provider -> providerRequestList.add(new ProviderRequest(provider.name,provider.price,provider.tripId)));

        log.info("provider list : {}",providerRequestList);
        return providerRequestList;
        }

    }

