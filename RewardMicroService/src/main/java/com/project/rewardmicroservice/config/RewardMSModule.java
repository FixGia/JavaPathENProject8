package com.project.rewardmicroservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rewardCentral.RewardCentral;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

        logger.info(" RewardCentral has been called");
        return new RewardCentral();
    }

    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(1000);
    }

}
