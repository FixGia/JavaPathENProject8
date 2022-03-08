package tourGuide.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;
import tourGuide.Dto.UserPreferencesRequest;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
public class UserPreferences {

	private int attractionProximity = Integer.MAX_VALUE;

	private CurrencyUnit currency = Monetary.getCurrency("USD");

	private BigDecimal lowerPricePoint = BigDecimal.valueOf(0);

	private BigDecimal highPricePoint = BigDecimal.valueOf(Integer.MAX_VALUE);

	private int tripDuration = 1;

	private int ticketQuantity = 1;

	private int numberOfAdults = 1;

	private int numberOfChildren = 0;

	/** Instantiate a new user preferences
	 *
	 * @param attractionProximity
	 * @param lowerPricePoint
	 * @param highPricePoint
	 * @param tripDuration
	 * @param ticketQuantity
	 * @param numberOfAdults
	 * @param numberOfChildren
	 *
	 */
	public UserPreferences(int attractionProximity,
						   BigDecimal lowerPricePoint,
						   BigDecimal highPricePoint,
						   int tripDuration,
						   int ticketQuantity,
						   int numberOfAdults,
						   int numberOfChildren) {

		this.attractionProximity = attractionProximity;
		this.lowerPricePoint = lowerPricePoint;
		this.highPricePoint = highPricePoint;
		this.tripDuration = tripDuration;
		this.ticketQuantity = ticketQuantity;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
	}

}