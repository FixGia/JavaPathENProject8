package tourGuide.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tourGuide.model.Location;
import tourGuide.model.User;
import tourGuide.model.VisitedLocation;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

@Component
@Slf4j
@AllArgsConstructor
@Getter
@Setter
public class InternalTestHelper {

    private static int internalUserNumber = 1000;
    private static final String TRIP_PRICER_API_KEY = "test-server-api-key";
    private final Map<String, User> internalUserMap = new ConcurrentHashMap<>();


    public void initializeInternalUsers() {


        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i-> {

            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(user);

            internalUserMap.put(userName, user);

        });

        log.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    private static int getInternalUserNumber() {
        return InternalTestHelper.internalUserNumber;
    }

    public static void setInternalUserNumber(int internalUserNumber) {
        InternalTestHelper.internalUserNumber = internalUserNumber;
    }

    private void generateUserLocationHistory(User user) {

        IntStream.range(0, 3).forEach(i -> {

            user.addToVisitedLocations(new VisitedLocation(user.getUserId(),
                    new Location(generateRandomLatitude(),
                            generateRandomLongitude()),
                    getRandomTime()));

        });
    }

    private double generateRandomLongitude() {

        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    private double generateRandomLatitude() {

        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);

    }

    private Date getRandomTime() {

        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));

    }


}
