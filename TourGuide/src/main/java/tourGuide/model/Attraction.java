package tourGuide.model;

import lombok.Data;

import java.util.UUID;

@Data
public class Attraction {

    public final String attractionName;
    public final String city;
    public final String state;
    public final Location location;
    public final UUID attractionId;

}
