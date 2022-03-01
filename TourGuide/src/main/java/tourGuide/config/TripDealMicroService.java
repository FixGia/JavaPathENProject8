package tourGuide.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import tourGuide.Dto.ProviderRequest;
import java.util.List;
import java.util.UUID;

@FeignClient(value = "TripDealMicroService", url = "${CLIENT_PROVIDER_BASE_URL:http://localhost:8083/tripDeals}")
public interface TripDealMicroService {

    @GetMapping("/providers/{apiKey}/{userId}/{adults}/{children}/{nightsStay}/{rewardPoints}")
    List<ProviderRequest> getProviders(@PathVariable final String apiKey,
                                              @PathVariable final UUID userId,
                                              @PathVariable int adults,
                                              @PathVariable int children,
                                              @PathVariable int nightsStay,
                                              @PathVariable int rewardPoints);
}
