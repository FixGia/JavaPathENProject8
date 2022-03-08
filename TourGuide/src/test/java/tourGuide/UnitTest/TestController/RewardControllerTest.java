package tourGuide.UnitTest.TestController;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
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
import tourGuide.model.Location;
import tourGuide.service.LocationService;
import tourGuide.service.RewardService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##RewardController from TourGuide- Unit Test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RewardControllerTest {

    @MockBean
    private RewardService rewardService;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @BeforeEach
    public void setUp(){

        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void getRewardTest() throws Exception {


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getRewards").param("userName", "userName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    public void getRewardWithoutParametersTest() throws Exception {


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getRewards")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertNotNull(mvcResult);
    }





}
