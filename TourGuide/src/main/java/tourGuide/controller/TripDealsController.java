package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.model.Provider;
import tourGuide.service.TripDealService;

import java.util.List;

@RestController
public class TripDealsController {

    private final TripDealService tripDealService;

    public TripDealsController(TripDealService tripDealService) {
        this.tripDealService = tripDealService;
    }

    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {

        List<Provider> providers = tripDealService.getTripDeals(userName);
        return JsonStream.serialize(providers);

    }


}
