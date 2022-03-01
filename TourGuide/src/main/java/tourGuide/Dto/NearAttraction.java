package tourGuide.Dto;

import lombok.*;
import org.springframework.stereotype.Service;
import tourGuide.model.Attraction;
import tourGuide.model.Location;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NearAttraction {

    private String attractionName;
    private Location location;
    private int distance;
    private int rewardPoint;


}
