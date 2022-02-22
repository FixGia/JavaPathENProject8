package com.project.tripdealsmicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tripPricer.TripPricer;

import java.util.Locale;

@Configuration
public class TripDealModule {


    @Bean
    public Locale getLocale() {
        Locale.setDefault(Locale.US);
        return Locale.getDefault();
    }

    @Bean
    public TripPricer getTripPrice(){

        return new TripPricer();
    }
}
