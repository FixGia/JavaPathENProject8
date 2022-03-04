package tourGuide.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import tourGuide.constant.Constraints;

import javax.validation.constraints.Min;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPreferencesRequest {

    @Min(value = Constraints.PROXIMITY_LOWER_LIMIT,
            message = "Valid value required")
    private int attractionProximity;

    @Min(value = Constraints.TRIP_DURATION_MIN_VALUE,
            message = "Minimum value should be 1")
    private int tripDuration;

    @Min(value = Constraints.TICKET_QUANTITY_MIN_VALUE,
            message = "Minimum 1 ticket required")
    private int ticketQuantity;

    @Min(value = Constraints.ADULT_NUMBER_MIN_VALUE,
            message = "Minimum 1 adult's ticket required")
    private int numberOfAdults;

    @Min(value = Constraints.CHILD_NUMBER_MIN_VALUE,
            message = "Valid value required")
    private int numberOfChildren;

    @Min(value = Constraints.LOWER_PRICE_POINT_VALUE,
            message = "Valid value required")
    private int lowerPrincePoint;

    @Min(value = Constraints.HIGH_PRICE_POINT_VALUE,
            message = "Valid value required")
    private int highPricePoint;


}
