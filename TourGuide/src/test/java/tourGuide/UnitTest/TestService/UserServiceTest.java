package tourGuide.UnitTest.TestService;


import org.javamoney.moneta.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import javax.money.Monetary;
import org.mockito.junit.jupiter.MockitoExtension;
import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.Exception.DataAlreadyExistException;
import tourGuide.Exception.DataNotFoundException;
import tourGuide.model.User;
import tourGuide.model.UserPreferences;
import tourGuide.service.Impl.UserServiceImpl;
import tourGuide.util.InternalTestHelper;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@DisplayName("## UserService from TourGuide - Unit Test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private InternalTestHelper internalTestHelper;


    private static UUID user1ID;

    private static UUID user2ID;

    private static User userTest1;

    private static User userTest2;


    private static Map<String, User> internalUser;

    private static List<User> userList;



    @BeforeEach
    public void setUp() {
        user1ID = UUID.randomUUID();
        user2ID = UUID.randomUUID();
        userTest1 = new User(user1ID, "testUser1", "000", "testUser1@gmail.com");
        userTest2 = new User(user2ID, "testUser2", "001", "testUser1@gmail.com");

        internalUser = new HashMap<>();
        internalUser.put("testUser1", userTest1);
        internalUser.put("testUser2", userTest2);
        userList = Arrays.asList(userTest1, userTest2);

    }


    @Test
    public void testGetAllUsers() {


        when(internalTestHelper.getInternalUserMap())
                .thenReturn(internalUser);


        List<User> users = userService.getAllUsers();

        assertNotNull(users);
        assertEquals(userList.size(), users.size());
    }

    @Test
    public void testGetUser(){

        when(internalTestHelper.getInternalUserMap())
                .thenReturn(internalUser);

        User user1= userService.getUser("testUser1");


        assertNotNull(user1);
        assertEquals("testUser1", user1.getUserName());
    }

    @Test
    public void testGetUserButDoesntExist() {

        when(internalTestHelper.getInternalUserMap())
                .thenReturn(new HashMap<>());

        assertThrows(DataNotFoundException.class, () -> userService.getUser("userdoesntexist"));

    }

    @Test
    public void TestAddUser(){
        when(internalTestHelper.getInternalUserMap())
                .thenReturn(internalUser);

        User userTest3 = new User(UUID.randomUUID(), "testUser3", "000", "testUser3@email.com");
        userService.addUser(userTest3);
        assertTrue(internalTestHelper.getInternalUserMap().containsValue(userTest3));
    }

    @Test
    public void TestAddUserButAlreadyExist(){
        when(internalTestHelper.getInternalUserMap())
                .thenReturn(internalUser);

        assertThrows(DataAlreadyExistException.class, () -> userService.addUser(userTest1));

    }

    @Test
    public void TestUpdateUserPreferences(){
        when(internalTestHelper.getInternalUserMap())
                .thenReturn(internalUser);

        User user= userService.getUser("testUser1");
        UserPreferencesRequest userPreferencesRequest = new UserPreferencesRequest(10,new BigDecimal(10),new BigDecimal(10),
                5,
                5,
                500,
              500);

        userService.updateUserPreferences("testUser1", userPreferencesRequest);
        assertEquals(user.getUserPreferences().getAttractionProximity(), userPreferencesRequest.getAttractionProximity());

    }

}
