package com.project.tripdealsmicroservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tripPricer.TripPricer;

import java.util.Locale;

@Configuration
public class TripDealModule {

    private Logger logger = LoggerFactory.getLogger(TripDealModule.class);

    @Bean
    public Locale getLocale() {
        Locale.setDefault(Locale.US);
        return Locale.getDefault();
    }

    @Bean
    public TripPricer getTripPrice(){

        logger.info(" TripPricer has been called");
        return new TripPricer();
    }
}
