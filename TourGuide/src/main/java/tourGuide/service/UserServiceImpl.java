package tourGuide.service;

import org.springframework.stereotype.Service;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final InternalTestHelper internalTestHelper;

    public UserServiceImpl(InternalTestHelper internalTestHelper) {
        this.internalTestHelper = internalTestHelper;
    }


    public List<User> getAllUsers() {
        return new ArrayList<>(internalTestHelper.getInternalUserMap().values());
    }

    public User getUser(String userName) {

        return internalTestHelper.getInternalUserMap().get(userName);
    }

    public void addUser(User user) {

        if(!internalTestHelper.getInternalUserMap().containsKey(user.getUserName())) {

            internalTestHelper.getInternalUserMap().put(user.getUserName(), user);
        }
    }

}
