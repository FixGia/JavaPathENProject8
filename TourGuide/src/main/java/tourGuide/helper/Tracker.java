package tourGuide.helper;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.springframework.stereotype.Component;
import tourGuide.model.User;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


@Component
@Slf4j
public class Tracker extends Thread {

	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private final LocationService gpsService;
	private final UserService userService;
	private boolean stop = false;

	public Tracker(LocationService gpsService, UserService userService) {
		this.gpsService = gpsService;
		this.userService = userService;
	}


	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}

	/** Add task to executorService
	 *
	 */
	public void startTracking(){
		stop = false;
		executorService.submit(this);
	}

	@Override
	public void run() {
		StopWatch stopWatch = new StopWatch();
		while(true) {
			if(Thread.currentThread().isInterrupted() || stop) {
				log.debug("Tracker stopping");
				break;
			}
			List<User> users = userService.getAllUsers();
			log.debug("Begin Tracker. Tracking " + users.size() + " users.");
			stopWatch.start();
			users.forEach(gpsService::trackUserLocation);
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
}
