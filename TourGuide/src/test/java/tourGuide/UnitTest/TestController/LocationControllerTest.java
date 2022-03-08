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
import tourGuide.Dto.AttractionRecommendationRequest;
import tourGuide.model.Location;
import tourGuide.service.LocationService;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.lenient;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("##LocationController from TourGuide- Unit Test")
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LocationControllerTest {

    @MockBean
    private LocationService locationService;

    @Autowired
    MockMvc mvc;

    @Autowired
    private WebApplicationContext applicationContext;


    @BeforeEach
    public void setUp(){

        mvc = MockMvcBuilders.webAppContextSetup(applicationContext).build();
    }


    @Test
    public void GetLocationTest() throws Exception {

        lenient().when(locationService.getUserLocation(ArgumentMatchers.any(String.class))).thenReturn(new Location(33.454154,78.454545));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getLocation").param("userName", "userName")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    public void GetLocationWithoutParameterTest() throws Exception {

        lenient().when(locationService.getUserLocation(ArgumentMatchers.any(String.class))).thenReturn(new Location(33.454154,78.454545));

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/getLocation")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    public void GetNearbyAttractionsTest() throws Exception{

        lenient().when(locationService.AttractionRecommendedForUser("userName")).thenReturn(new AttractionRecommendationRequest());

        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/nearbyAttractions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("userName", "userName"))
                .andExpect(status().isOk())
                .andReturn();

        assertNotNull(mvcResult);
    }

    @Test
    public void testGetNearbyAttractionsWithouParameters() throws Exception {


        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                        .get("/nearbyAttractions")
                        .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest())
                .andReturn();
        assertNotNull(mvcResult);
    }

}
