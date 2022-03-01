package tourGuide.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.model.Location;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionRecommendationRequest {

    private Location userIsHere;

    private List<NearAttraction> nearAttraction;

}
