package com.project.tripdealsmicroservice;


import com.project.tripdealsmicroservice.dto.ProviderRequest;
import com.project.tripdealsmicroservice.service.TripDealService;
import com.project.tripdealsmicroservice.service.TripDealServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.lenient;

@DisplayName("##Service from TripDeals MicroService Unit Test")
@ExtendWith(MockitoExtension.class)
public class UnitTestService {

    @Mock
    private TripPricer tripPricer;

    @InjectMocks
    TripDealServiceImpl tripDealService;


    @Test
    public void GetProvidersTest(){

        UUID userIdTest = UUID.randomUUID();
        UUID tripIDTest1 = UUID.randomUUID();
        UUID tripIDTest2 = UUID.randomUUID();

        Provider provider1 = new Provider(tripIDTest1, "providerTest1", 100);
        Provider provider2 = new Provider(tripIDTest2, "providerTest2", 200);

        lenient().when(tripPricer
                        .getPrice("apiKey", userIdTest, 100, 100, 100, 100))
                .thenReturn(Arrays.asList(provider1, provider2));

        List<ProviderRequest> resultList = tripDealService.getProviders("apiKey", userIdTest, 100,100,100,100);

        assertNotNull(resultList);
        assertEquals(2, resultList.size());
        assertEquals(provider1.name,resultList.get(0).getName());
        assertEquals(provider2.name,resultList.get(1).getName());

    }


}
