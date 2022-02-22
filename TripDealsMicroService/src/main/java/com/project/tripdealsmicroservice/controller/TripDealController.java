package com.project.tripdealsmicroservice.controller;

import com.project.tripdealsmicroservice.dto.ProviderRequest;
import com.project.tripdealsmicroservice.service.TripDealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/tripDeals")
public class TripDealController {

    private final TripDealService tripDealService;

    public TripDealController(TripDealService tripDealService) {
        super();
        this.tripDealService = tripDealService;
    }

    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardPoints}")
    public List<ProviderRequest> getProviders(@PathVariable final String apiKey,
                                              @PathVariable final UUID userId,
                                              @PathVariable int adults,
                                              @PathVariable int children,
                                              @PathVariable int nightsStay,
                                              @PathVariable int rewardPoints) {

        List<ProviderRequest> providers = tripDealService.getProviders(apiKey,userId,adults,children,nightsStay,rewardPoints);
        if (providers.isEmpty()) {
            throw new RuntimeException();
        }
        log.info("provider List was got");

        return providers;
    }
}
