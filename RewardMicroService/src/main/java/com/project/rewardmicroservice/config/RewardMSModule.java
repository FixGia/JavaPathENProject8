package com.project.rewardmicroservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

import java.util.Locale;

@Configuration
public class RewardMSModule {


    private Logger logger = LoggerFactory.getLogger(RewardMSModule.class);

    @Bean
    public Locale getLocale() {
        Locale.setDefault(Locale.US);
        return Locale.getDefault();
    }

    @Bean
    public RewardCentral getRewardCentral(){

        logger.info(" GpsUtil() BEAN called");
        return new RewardCentral();
    }

}
