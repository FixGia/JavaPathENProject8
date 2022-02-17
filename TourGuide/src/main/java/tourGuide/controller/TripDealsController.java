package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.model.Provider;

import java.util.List;

@RestController
public class TripDealsController {

    //TODO make tripDealsMethod's
    @RequestMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) {
    //    List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
      //
        // return JsonStream.serialize(providers);
        return null;
    }
}
