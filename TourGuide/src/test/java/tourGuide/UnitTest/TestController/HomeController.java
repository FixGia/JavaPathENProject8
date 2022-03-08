package tourGuide.UnitTest.TestController;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##HomeController from TourGuide- Unit Test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HomeController {

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;

    @BeforeEach
    public void setUp(){

        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }

    @Test
    public void HomeTestController() throws Exception {


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/")
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andReturn();

        Assertions.assertNotNull(mvcResult);
        String content = mvcResult.getResponse().getContentAsString();
        Assertions.assertEquals("Greetings from TourGuide!", content);

        }
}
