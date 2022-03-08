package tourGuide.UnitTest.TestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tourGuide.Dto.UserPreferencesRequest;
import tourGuide.service.TripDealService;
import tourGuide.service.UserService;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##UserController from TourGuide- Unit Test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @BeforeEach
    public void setUp(){


        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }


    @Test
    public void getUserTest() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getUser").param("userName", "userName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);
    }


    @Test
    public void getUserWithoutParametersTest() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getUser")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    public void getAllUsersTest() throws Exception {

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getAllUsers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);
    }

     @Test
    public void UpdateUsersPreferences() throws Exception {

         UserPreferencesRequest userPreferencesRequest = new UserPreferencesRequest(400,
                 new BigDecimal(400),
                 new BigDecimal(400),
                 400,
                 400,
                 400,
                 400);

         ObjectMapper objectMapper = new ObjectMapper();

         MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.put("/updateUserPreferences")
                         .param("userName", "userName").content(objectMapper.writeValueAsString(userPreferencesRequest))
                         .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk()).andReturn();

         assertNotNull(mvcResult);
     }
}
