package tourGuide.service;


import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;

import java.util.List;

public interface UserService {

    User getUser(String userName);

    void addUser(User user);

    UserPreferences updateUserPreferences(String username, UserPreferencesRequest userPreferencesRequest);

     List<User> getAllUsers();






}
