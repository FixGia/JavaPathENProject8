package tourGuide.model;
import lombok.Data;



@Data
public class Location {
    public final double longitude;
    public final double latitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}

