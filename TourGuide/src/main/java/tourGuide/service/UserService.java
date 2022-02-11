package tourGuide.service;

import tourGuide.model.User;

import java.util.List;

public interface UserService {

    public void addUser(User user);

    public User getUser(String userName);

    public List<User> getAllUsers();

}
