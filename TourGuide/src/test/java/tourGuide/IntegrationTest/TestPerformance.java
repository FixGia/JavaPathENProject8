package tourGuide.IntegrationTest;

import org.apache.commons.lang3.time.StopWatch;


import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import tourGuide.Dto.AttractionRequest;
import tourGuide.proxy.GpsMicroService;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;
import tourGuide.service.LocationService;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;
import tourGuide.util.InternalTestHelper;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestPropertySource("/Test-mode.properties")
public class TestPerformance {


	@Autowired
	private GpsMicroService gpsMicroService;
	@Autowired
	LocationService locationService;
	@Autowired
	private RewardService rewardService;

	@Autowired
	private UserService userService;


	@BeforeEach
	public void setUp(){

	}
	/*
	 * A note on performance improvements:
	 *
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *
	 *     		InternalTestHelper.setInternalUserNumber(100000);
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

		StopWatch stopWatch = new StopWatch();
		stopWatch.start();

		List<User> allUsers = userService.getAllUsers();
		allUsers.forEach(User::clearVisitedLocations);
		System.out.println(allUsers.size());


		List<Integer> initialVisitedLocationsCount = allUsers
				.stream()
				.map(u -> u
						.getVisitedLocations().size())
				.collect(Collectors.toList());


		CompletableFuture<?>[] futures = allUsers.parallelStream()
				.map(locationService::trackUserLocation)
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

		System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}




	@Test
	public void highVolumeGetRewards() {


		StopWatch stopWatch = new StopWatch();

		AttractionRequest attractionRequest = gpsMicroService.getAttractions().get(0);
		List<User> allUsers = userService.getAllUsers();


		allUsers.forEach(user -> {
			user.clearVisitedLocations();
			user.getUserRewards().clear();});

		List<Integer> initialRewardCount = allUsers
				.stream()
				.map(u -> u
						.getUserRewards().size())
				.collect(Collectors.toList());

		stopWatch.start();

		allUsers.forEach(user -> {
			user.clearVisitedLocations();
			user.getUserRewards().clear();
			VisitedLocation visitedLocation = new VisitedLocation(user.getUserId(), attractionRequest.getLocation(), new Date());
			user.addToVisitedLocations(visitedLocation);
		});



		CompletableFuture<?>[] futures = allUsers.parallelStream()
				.map(rewardService :: calculateRewardAsync)
				.toArray(CompletableFuture[]::new);
		CompletableFuture.allOf(futures).join();

		for(User user : allUsers) {
			assertTrue(user.getUserRewards().size() > 0);
		}

		stopWatch.stop();

		List<Integer> newRewardCount = allUsers
				.parallelStream()
				.map(u -> u
						.getUserRewards().size())
				.collect(Collectors.toList());


		for (int i = 0; i < initialRewardCount.size(); i++) {
			assertEquals(initialRewardCount.get(i) + 1, (int) newRewardCount.get(i));
		}


		System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
		assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));


	}


}
