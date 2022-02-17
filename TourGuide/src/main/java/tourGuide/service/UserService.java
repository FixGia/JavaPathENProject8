package tourGuide.service;


import tourGuide.model.User;
import tourGuide.model.UserReward;
import tourGuide.model.VisitedLocation;

import java.util.List;

public interface UserService {

    User getUser(String userName);

     void addUser(User user);

     void deleteUser();

    void updateUserPreferences();

     List<User> getAllUsers();






}
