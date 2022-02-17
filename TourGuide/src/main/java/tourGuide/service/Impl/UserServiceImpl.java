package tourGuide.service.Impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tourGuide.model.User;
import tourGuide.service.UserService;
import tourGuide.helper.InternalTestHelper;

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

        log.error("user {} wasn't exist", userToGet.getUserName());
        return  null;

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
    public void deleteUser() {

    }

    @Override
    public void updateUserPreferences() {

    }

    @Override
    public List<User> getAllUsers(){
        return new ArrayList<>(internalTestHelper.getInternalUserMap().values());
    }


}
