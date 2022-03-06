package tourGuide.IntegrationTest;

import org.apache.commons.lang3.time.StopWatch;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuide.Dto.AttractionRequest;
import tourGuide.config.GpsMicroService;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;
import tourGuide.util.Initializer;
import tourGuide.util.InternalTestHelper;
import tourGuide.util.Tracker;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPerformance {


	@Autowired
	private GpsMicroService gpsMicroService;
	@Autowired
	private RewardService rewardService;
	@Autowired
	Initializer initializer;
	@Autowired
	private UserService userService;
	@Autowired
	private Tracker tracker;

	/*
	 * A note on performance improvements:
	 *
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *
	 *
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent.
	 *
	 *     These are performance metrics that we are trying to hit:
	 *
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 *
	 *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */


	@Test
	public void highVolumeTrackLocation() {


		List<User> allUsers = userService.getAllUsers();
		System.out.println(allUsers.size());

		List<Integer> initialVisitedLocationsCount = allUsers
				.stream()
				.map(u -> u
						.getVisitedLocations().size())
				.collect(Collectors.toList());
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		CompletableFuture<?>[] futures = allUsers.parallelStream()
				.map(tracker::trackUserLocation)
				.toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(futures).join();
		stopWatch.stop();

		List<Integer> newVisitedLocationsCount = allUsers
				.parallelStream()
				.map(u -> u
						.getVisitedLocations().size())
				.collect(Collectors.toList());


		for (int i = 0; i < initialVisitedLocationsCount.size(); i++) {
			assertEquals(initialVisitedLocationsCount.get(i) + 1, (int) newVisitedLocationsCount.get(i));
		}

//
		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}




	@Test
	public void highVolumeGetRewards() {

		List<User> allUsers = userService.getAllUsers();
		allUsers.forEach(User::clearVisitedLocations);

		List<Integer> initialVisitedLocationsCount = allUsers
				.stream()
				.map(u -> u
						.getVisitedLocations().size())
				.collect(Collectors.toList());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		initializer.initialization();
		AttractionRequest attractionRequest = gpsMicroService.getAttractions().get(0);
	//	List<User> allUsers = userService.getAllUsers();
		allUsers.forEach(user -> {
			user.clearVisitedLocations();
			user.getUserRewards().clear();
			user.addToVisitedLocations(
					new VisitedLocation(
							user.getUserId(),
							attractionRequest.getLocation(),
							new Date()));
		});


		allUsers.forEach(user -> rewardService.calculateRewards(user));

		for(User user : allUsers) {
			assertTrue(user.getUserRewards().size()>0);
		}
		stopWatch.stop();
		tracker.stopTracking();

		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));




	}

	
}
