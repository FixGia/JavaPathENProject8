package tourGuide.model;
import gpsUtil.location.Location;
import lombok.Data;

import java.util.Date;
import java.util.UUID;


@Data
public class VisitedLocation {
    public final UUID userId;
    public final gpsUtil.location.Location location;
    public final Date timeVisited;

    public VisitedLocation(UUID userId, Location location, Date timeVisited) {
        this.userId = userId;
        this.location = location;
        this.timeVisited = timeVisited;
    }
}
