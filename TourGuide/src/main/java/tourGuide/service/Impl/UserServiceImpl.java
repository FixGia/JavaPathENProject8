package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.javamoney.moneta.Money;
import org.springframework.stereotype.Service;
import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.Exception.DataAlreadyExistException;
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


        throw new DataNotFoundException("user doesn't found");

    }


    @Override
    public void addUser(User user) {

        if (!internalTestHelper.getInternalUserMap().containsKey(user.getUserName())) {
            internalTestHelper.getInternalUserMap().put(user.getUserName(), user);
            log.info("user {} was added", user.getUserName());
        }
        else throw new DataAlreadyExistException("user exist already");
    }


    @Override
    public UserPreferences updateUserPreferences(final String username,
                                      final UserPreferencesRequest userPreferencesRequest) {

        User userToGet = getUser(username);
        if (userToGet != null) {

            UserPreferences userPreferences = userToGet.getUserPreferences();

            userPreferences.setAttractionProximity(userPreferencesRequest.getAttractionProximity());
            userPreferences.setLowerPricePoint(userPreferencesRequest.getLowerPricePoint());
            userPreferences.setHighPricePoint(userPreferencesRequest.getHighPricePoint());
            userPreferences.setTripDuration(userPreferencesRequest.getTripDuration());
            userPreferences.setTicketQuantity(userPreferencesRequest.getTicketQuantity());
            userPreferences.setNumberOfAdults(userPreferencesRequest.getNumberOfAdults());
            userPreferences.setNumberOfChildren(userPreferencesRequest.getNumberOfChildren());


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
