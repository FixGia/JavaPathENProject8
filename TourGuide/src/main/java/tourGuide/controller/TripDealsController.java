package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.Dto.ProviderRequest;
import tourGuide.service.TripDealService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TripDealsController {

    private final TripDealService tripDealService;

    public TripDealsController(TripDealService tripDealService) {
        this.tripDealService = tripDealService;
    }

    @GetMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {

        List<ProviderRequest> providers = tripDealService.getTripDeals(userName);
        Map<String, Double> providerMap=  new HashMap<>();
        providers.forEach(providerRequest -> providerMap.put(providerRequest.getName(), providerRequest.getPrice()));

        return JsonStream.serialize(providerMap);

    }


}
