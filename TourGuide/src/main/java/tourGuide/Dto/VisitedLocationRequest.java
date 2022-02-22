package tourGuide.Dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tourGuide.model.Location;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitedLocationRequest {

    private UUID userId;
    private Location location;
    private Date timeVisited;


}
