package com.example.testasync.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsWebConfig implements WebMvcConfigurer {

    @Bean
    public void addCorsConfig(CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**")
                .
    }

}
