package tourGuide.util;


import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.service.LocationService;
import tourGuide.service.UserService;

import javax.annotation.PostConstruct;


@Service
@Slf4j
public class Initializer {



    private final InternalTestHelper internalTestHelper;

    private final LocationService locationService;
    private final UserService userService;

    @Value("${test.mode.enabled}")
    private boolean TestMode;

    public Tracker tracker;




    public Initializer(InternalTestHelper internalTestHelper, LocationService locationService, UserService userService) {
        this.internalTestHelper = internalTestHelper;
        this.locationService = locationService;


        this.userService = userService;
    }

    @PostConstruct
    public void initialization() {


        if(TestMode) {

            log.info("TestMode enabled");

            log.debug("Initializing users");

            internalTestHelper.initializeInternalUsers();

            log.debug("Finished initializing users");

            addShutDownHook();

             } else

             log.info("NormalMode enabled");

        tracker = new Tracker(locationService,userService);
        tracker.startTracking();

             addShutDownHook();
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> tracker.stopTracking()));
    }

}
