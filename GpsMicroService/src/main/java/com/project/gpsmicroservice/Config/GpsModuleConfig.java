package com.project.gpsmicroservice.Config;

import gpsUtil.GpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;

import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class GpsModuleConfig {

    private Logger logger = LoggerFactory.getLogger(GpsModuleConfig.class);

    @Bean
    public Locale getLocale() {
        Locale.setDefault(Locale.US);
        return Locale.getDefault();
    }

    @Bean
    public GpsUtil getGpsUtil(){

        logger.info(" GpsUtil() BEAN called");
        return new GpsUtil();

    }

    @Bean
    public ExecutorService getExecutorService() {
        return Executors.newFixedThreadPool(1000);
    }



}
