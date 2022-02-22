package tourGuide.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.model.Location;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AttractionRequest {

    private String attractionName;
    private String city;
    private String state;
    private UUID attractionId;
    private Location location;
}
