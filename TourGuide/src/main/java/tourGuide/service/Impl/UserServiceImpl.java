package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.Exception.DataNotFoundException;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.service.UserService;
import tourGuide.util.InternalTestHelper;

import java.util.ArrayList;
import java.util.List;



@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final InternalTestHelper internalTestHelper;

    public UserServiceImpl(InternalTestHelper internalTestHelper) {
        this.internalTestHelper = internalTestHelper;
    }

    @Override
    public User getUser(String userName) {

        User userToGet =  internalTestHelper.getInternalUserMap().get(userName);

        if (userToGet != null) {
            log.info("user {} was found", userToGet.getUserName());
            return userToGet;
        }

        log.error("user {} wasn't exist", userName);
        return null;

    }


    @Override
    public void addUser(User user) {

        if(!internalTestHelper.getInternalUserMap().containsKey(user.getUserName())) {
            internalTestHelper.getInternalUserMap().put(user.getUserName(), user);
            log.info("user {} was added", user.getUserName());
        }

        log.error("user {} wasn't added", user.getUserName());

    }


    @Override
    public UserPreferences updateUserPreferences(final String username,
                                      final UserPreferencesRequest userPreferencesRequest) {

        User userToGet = getUser(username);
        if (userToGet != null) {

            UserPreferences userPreferences = userToGet.getUserPreferences();

            userPreferences.setAttractionProximity(userPreferencesRequest.getAttractionProximity());
            userPreferences.setTripDuration(userPreferencesRequest.getTripDuration());
            userPreferences.setTicketQuantity(userPreferencesRequest.getTicketQuantity());
            userPreferences.setNumberOfAdults(userPreferencesRequest.getNumberOfAdults());
            userPreferences.setNumberOfChildren(userPreferencesRequest.getNumberOfChildren());
            userPreferences.setLowerPricePoint(Money.of(userPreferencesRequest.getLowerPrincePoint(), userPreferences.getCurrency()));
            userPreferences.setHighPricePoint(Money.of(userPreferencesRequest.getHighPricePoint(), userPreferences.getCurrency()));

            log.info("User Preferences was updated");
            return userPreferences;
        }

        log.error("User Preferences wasn't updated because userToGet wasn't exist");
        throw new DataNotFoundException("User doesn't exist");

    }

    @Override
    public List<User> getAllUsers(){

        return new ArrayList<>(internalTestHelper.getInternalUserMap().values());

    }


}
