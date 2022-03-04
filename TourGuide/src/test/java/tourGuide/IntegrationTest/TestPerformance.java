package tourGuide.IntegrationTest;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuide.config.GpsMicroService;
import tourGuide.model.User;
import tourGuide.service.LocationService;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
public class TestPerformance {


	@Autowired
	private GpsMicroService gpsMicroService;
	@Autowired
	private RewardService rewardService;
	@Autowired
	private UserService userService;
	@Autowired
	private LocationService locationService;

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
	
//	@Ignore
	@Test
	public void highVolumeTrackLocation() {


		List<User> allUsers = userService.getAllUsers();
		allUsers.forEach(User::clearVisitedLocations);

		List<Integer> initialCountVisitedLocation= allUsers.stream().map(user -> user.getVisitedLocations().size()).collect(Collectors.toList());

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		for(User user : allUsers) {
			locationService.trackUserLocation(user);
		}

		CompletableFuture<?>[] futures = allUsers.parallelStream()
				.map(locationService::trackUserLocation)
				.toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(futures).join();

		stopWatch.stop();

		List<Integer> newCountVisitedLocation= allUsers.parallelStream().map(user -> user.getVisitedLocations().size()).collect(Collectors.toList());

		for (int i = 0; i < initialCountVisitedLocation.size(); i++) {
			assertEquals(initialCountVisitedLocation.get(i) + 1, (int) newCountVisitedLocation.get(i));
		}

	System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
	assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
//	@Ignore
//	@Test
//	public void highVolumeGetRewards() {
//		GpsUtil gpsUtil = new GpsUtil();
//		RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
//
		// Users should be incremented up to 100,000, and test finishes within 20 minutes
//		InternalTestHelper.setInternalUserNumber(100);
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
//
//	    Attraction attraction = gpsUtil.getAttractions().get(0);
//		List<User> allUsers = new ArrayList<>();
//		allUsers = tourGuideService.getAllUsers();
//		allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocation(u.getUserId(), attraction, new Date())));
//
//	    allUsers.forEach(u -> rewardsService.calculateRewards(u));
//
//		for(User user : allUsers) {
//			assertTrue(user.getUserRewards().size() > 0);
//		}
//		stopWatch.stop();
//		tourGuideService.tracker.stopTracking();

//		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
//		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
//	}
	
}
