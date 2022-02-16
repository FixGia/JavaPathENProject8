package tourGuide.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private UUID userId;
	private String userName;
	private String phoneNumber;
	private String emailAddress;
	private Date latestLocationTimestamp;
	private List<VisitedLocation> visitedLocations = new ArrayList<>();
	private List<UserReward> userRewards = new ArrayList<>();
	private UserPreferences userPreferences = new UserPreferences();
	private List<Provider> tripDeals = new ArrayList<>();

	public User(UUID userId,
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

	/** Get last visited Location
	 *
	 * @return last visited location
	 */
	public VisitedLocation getLastVisitedLocation() {
		return visitedLocations.get(visitedLocations.size() - 1);
	}

}
