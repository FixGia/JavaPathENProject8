package tourGuide.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.javamoney.moneta.Money;

import javax.money.CurrencyUnit;
import javax.money.Monetary;


@Data
@NoArgsConstructor
public class UserPreferences {

	private int attractionProximity = Integer.MAX_VALUE;

	private CurrencyUnit currency = Monetary.getCurrency("USD");

	private Money lowerPricePoint = Money.of(0, currency);

	private Money highPricePoint = Money.of(Integer.MAX_VALUE, currency);

	private int tripDuration = 1;

	private int ticketQuantity = 1;

	private int numberOfAdults = 1;

	private int numberOfChildren = 0;

	/** Instantiate a new user preferences
	 *
	 * @param attractionProximity
	 * @param currency
	 * @param lowerPricePoint
	 * @param highPricePoint
	 * @param tripDuration
	 * @param ticketQuantity
	 * @param numberOfAdults
	 * @param numberOfChildren
	 *
	 */
	public UserPreferences(int attractionProximity,
						   CurrencyUnit currency,
						   Money lowerPricePoint,
						   Money highPricePoint,
						   int tripDuration,
						   int ticketQuantity,
						   int numberOfAdults,
						   int numberOfChildren) {

		this.attractionProximity = attractionProximity;
		this.currency = currency;
		this.lowerPricePoint = lowerPricePoint;
		this.highPricePoint = highPricePoint;
		this.tripDuration = tripDuration;
		this.ticketQuantity = ticketQuantity;
		this.numberOfAdults = numberOfAdults;
		this.numberOfChildren = numberOfChildren;
	}
}