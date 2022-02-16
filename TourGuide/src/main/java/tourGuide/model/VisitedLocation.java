package tourGuide.model;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class VisitedLocation {

    public final UUID userId;
    public final Location location;
    public final Date timeVisited;

}
