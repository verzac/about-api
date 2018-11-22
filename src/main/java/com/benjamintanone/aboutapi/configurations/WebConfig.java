package com.benjamintanone.aboutapi.configurations;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private CorsConfigProperties cors;

    public void addCorsMappings(CorsRegistry registry) {
        log.info("Adding the following allowedOrigins: " + Arrays.toString(cors.getAllowed_origins().toArray()));
        registry.addMapping("/**").allowedOrigins(cors.getAllowed_origins().toArray(new String[cors.getAllowed_origins().size()]));
    }
}
