package tourGuide.tracker;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tourGuide.service.GpsService;
import tourGuide.service.UserService;

import javax.annotation.PostConstruct;


@Service
@Slf4j

public class Initializer {


    private boolean isTestMode ;
    private boolean isPerformanceTest;
    private InternalTestHelper internalTestHelper;
    private UserService userService;
    private GpsService gpsService;
    private Tracker tracker;

    public Initializer(InternalTestHelper internalTestHelper) {
        this.internalTestHelper = internalTestHelper;
    }

    @PostConstruct
    public void initialization() {


       // if (isTestMode) {

            log.info("TestMode enabled");

            log.debug("Initializing users");

            internalTestHelper.initializeInternalUsers();

            log.debug("Finished initializing users");


        }

        //  if (!isPerformanceTest) {

        //    this.tracker = new Tracker(gpsService, userService);

        //  log.info("## Tracker instance initiated");

        // tracker.start();
        // }

        //  addShutDownHook();

        //  }
 //   }
    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> tracker.stopTracking()));
    }
}
