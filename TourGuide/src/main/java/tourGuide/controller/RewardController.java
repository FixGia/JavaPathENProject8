package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class RewardController {

    //TODO Make reward method's
    @RequestMapping("/getRewards")
    public String getRewards(@RequestParam String userName) {
       //
        // return JsonStream.serialize(tourGuideService.getUserRewards(getUser(userName)));
    return null;
    }
}
