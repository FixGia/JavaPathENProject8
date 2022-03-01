package tourGuide.controller;

import com.jsoniter.output.JsonStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.service.RewardService;

import javax.validation.constraints.NotNull;

@RestController
public class RewardController {

    private final RewardService rewardService;

    public RewardController(RewardService rewardService) {
        this.rewardService = rewardService;
    }


    @RequestMapping("/getRewards")
    public String getRewards(@RequestParam @NotNull final String userName) {

        return JsonStream.serialize(rewardService.getUserRewards(userName));

    }


}
