package tourGuide.util;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;

import org.springframework.stereotype.Component;
import tourGuide.config.GpsMicroService;
import tourGuide.model.User;

import tourGuide.service.LocationService;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class Tracker extends Thread {

	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);

	ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final LocationService locationService;
	private final UserService userService;
	private final Map<User, Boolean> completedTrackingMap = new HashMap<>();
	private boolean stop = false;

	public Tracker(LocationService locationService, UserService userService) {

		this.locationService = locationService;
		this.userService = userService;

	}


	public void startTracking() {
		stop = false;
		executorService.submit(this);
	}

	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}


	@Override
	public void run() {

		StopWatch stopWatch = new StopWatch();

		while (true) {

			if (Thread.currentThread().isInterrupted() || stop) {
				log.debug("Tracker stopping");
				break;
			}



			List<User> users = userService.getAllUsers();
			log.debug("Begin Tracker. Tracking " + users.size() + " users.");

			stopWatch.start();
			users.forEach(user -> completedTrackingMap.put(user,false));
			users.forEach(this::trackUser);

			boolean notFinished = true;

			while(notFinished){

				try {
					TimeUnit.MILLISECONDS.sleep(100);
				} catch (InterruptedException e) {
					break;
				}

				if(!completedTrackingMap.containsValue(false)) {
					notFinished = false;
				}
			}

			completedTrackingMap.clear();
			stopWatch.stop();
			log.debug("Tracker Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			stopWatch.reset();

			try {
				log.debug("Tracker sleeping");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				break;
			}
		}
	}

	public synchronized void finalizeTrack(User user) {

		completedTrackingMap.put(user, true);

	}


	public void trackUser(User user) {

		locationService.trackUserLocation(user);

		finalizeTrack(user);

	}






}

