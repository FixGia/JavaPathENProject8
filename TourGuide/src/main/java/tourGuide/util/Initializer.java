package tourGuide.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.config.GpsMicroService;
import tourGuide.service.LocationService;
import tourGuide.service.RewardService;
import tourGuide.service.UserService;

import javax.annotation.PostConstruct;


@Service
@Slf4j

public class Initializer {



    private final InternalTestHelper internalTestHelper;

    private final LocationService locationService;
    private final UserService userService;
    public Tracker tracker;


    public Initializer(InternalTestHelper internalTestHelper, LocationService locationService, UserService userService) {
        this.internalTestHelper = internalTestHelper;
        this.locationService = locationService;


        this.userService = userService;
    }

    @PostConstruct
    public void initialization() {



        log.info("TestMode enabled");
            log.debug("Initializing users");
            internalTestHelper.initializeInternalUsers();

            tracker = new Tracker(locationService, userService);

            tracker.startTracking();

            log.debug("Finished initializing users");

            addShutDownHook();

        }


    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> tracker.stopTracking()));
    }

}
