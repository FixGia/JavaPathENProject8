package tourGuide.model;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserReward {

	public final VisitedLocation visitedLocation;

	public final Attraction attraction;

	private int rewardPoints;



}