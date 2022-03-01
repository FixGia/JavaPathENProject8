package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.Dto.ProviderRequest;
import tourGuide.Exception.DataNotFoundException;
import tourGuide.config.Mapper;
import tourGuide.config.TripDealMicroService;
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.service.TripDealService;
import tourGuide.service.UserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class TripDealServiceImpl implements TripDealService {

    private final TripDealMicroService tripDealMicroService;
    private final UserService userService;
    private static final String TRIP_PRICER_API_KEY = "test-server-api-key";
    private final Mapper mapper;

    public TripDealServiceImpl(TripDealMicroService tripDealMicroService, UserService userService, Mapper modelMapper) {
        this.tripDealMicroService = tripDealMicroService;
        this.userService = userService;

        this.mapper = modelMapper;
    }

    public List<Provider> getTripDeals(String userName) {

        User user = userService.getUser(userName);

        if (user != null) {
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
        List<ProviderRequest> providers = tripDealMicroService.getProviders(TRIP_PRICER_API_KEY,
                user.getUserId(),
                user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(),
                user.getUserPreferences().getTripDuration(),
                cumulativeRewardPoints);
        List<Provider> providerList = new ArrayList<>();
        for(ProviderRequest providerRequest : providers) {
            mapper.modelMapper().map(providerRequest, Provider.class);
            providerList.add(mapper.modelMapper().map(providerRequest, Provider.class));
        }
        user.setTripDeals(providerList);

        log.info("TripDeals for user : {} : {}", userName, user.getTripDeals());
        return providerList;
    }
        log.error("providersList wasn't displayed because user wasn't exist");
        return Collections.emptyList();
    }
}
