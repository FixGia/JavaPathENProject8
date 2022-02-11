package tourGuide.model;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import gpsUtil.location.VisitedLocation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tripPricer.Provider;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private  UUID userId;

	private String userName;

	private String phoneNumber;

	private String emailAddress;

	private Date latestLocationTimestamp;

	private List<VisitedLocation> visitedLocations;

	private List<UserReward> userRewards;

	private UserPreferences userPreferences;

	private List<Provider> tripDeals;



	/**
	 * Instantiate new user.
	 *
	 * @param userId the userId
	 * @param userName the userName
	 * @param phoneNumber the phoneNumber
	 * @param emailAddress the email address
	 */
	public User(
			UUID userId,
			String userName,
			String phoneNumber,
			String emailAddress) {

		this.userId = userId;
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.emailAddress = emailAddress;
	}


	/** Add visited location
	 *
	 *
	 * @param visitedLocation the visited location
	 */

	public void addToVisitedLocations(VisitedLocation visitedLocation) {

		visitedLocations.add(visitedLocation);
	}

	/** Get last visited Location
	 *
	 * @return the last visited location
	 *
	 */
	public List<VisitedLocation> getVisitedLocations() {

		return visitedLocations;
	}

	/** clear visited locations
	 *
	 *
	 */
	public void clearVisitedLocations() {

		visitedLocations.clear();
	}

	/** add a User Reward
	 *
	 *
	 * @param userReward the reward
	 */

	public void addUserReward(UserReward userReward) {
		if(userRewards.stream().filter(r -> !r.attraction.attractionName.equals(userReward.attraction)).count() == 0)
		{

			userRewards.add(userReward);

		}
	}

}
