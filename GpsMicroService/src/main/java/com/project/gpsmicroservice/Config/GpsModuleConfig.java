package com.project.gpsmicroservice.Config;

import gpsUtil.GpsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

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



}
