package tourGuide.proxy;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "RewardMicroService", url = "${CLIENT_REWARD_BASE_URL:http://localhost:8082/rewards}")
public interface RewardMicroService {


    @GetMapping("/getRewardPoint/{attractionId}/{userId}")
    int getRewardPoint(@PathVariable final UUID attractionId, @PathVariable final UUID userId);

}
