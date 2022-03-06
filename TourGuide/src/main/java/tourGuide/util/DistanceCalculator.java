package tourGuide.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;
import tourGuide.Dto.AttractionRequest;
import tourGuide.model.Location;
import tourGuide.model.VisitedLocation;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Service
public class DistanceCalculator {

    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    private int attractionProximityRange = 200;


    public boolean isWithinAttractionProximity(AttractionRequest attraction, Location location) {
        return !(getDistanceInMiles(attraction.getLocation(), location) > attractionProximityRange);
    }

    public boolean nearAttraction(Location userLocation, Location attractionLocation) {
        return !(getDistanceInMiles(attractionLocation, userLocation) > proximityBuffer);
    }

    public double getDistanceInMiles(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        return STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
    }

}
