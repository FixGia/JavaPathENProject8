package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class Attraction {

    private String attractionName;

    private String city;

    private String state;

    private UUID attractionId;

}
