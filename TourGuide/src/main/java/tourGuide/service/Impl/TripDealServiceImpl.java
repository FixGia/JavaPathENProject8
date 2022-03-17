package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.Dto.ProviderRequest;
import tourGuide.proxy.TripDealMicroService;
import tourGuide.model.Provider;
import tourGuide.model.User;
import tourGuide.model.UserReward;
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


    public TripDealServiceImpl(TripDealMicroService tripDealMicroService, UserService userService) {
        this.tripDealMicroService = tripDealMicroService;
        this.userService = userService;
    }

    public List<ProviderRequest> getTripDeals(String userName) {

        User user = userService.getUser(userName);

        if (user != null) {
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(UserReward::getRewardPoints).sum();
        List<ProviderRequest> providers = tripDealMicroService.getProviders(TRIP_PRICER_API_KEY,
                user.getUserId(),
                user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(),
                user.getUserPreferences().getTripDuration(),
                cumulativeRewardPoints);
        List<Provider> providerList = new ArrayList<>();
        for(ProviderRequest providerRequest : providers) {
            providerList.add(new Provider(providerRequest.getName(),providerRequest.getPrice(),providerRequest.getTripId()));
        }
        user.setTripDeals(providerList);

        log.info("TripDeals for user : {} : {}", userName, user.getTripDeals());
        return providers;
    }
        log.error("providersList wasn't displayed because user wasn't exist");
        return Collections.emptyList();
    }
}
