//package com.example.testasync.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class CorsWebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedHeaders("Content-Type", "Authorization")
//                .exposedHeaders("Custom-Header")
//                .allowedMethods("POST", "GET", "PUT", "PATCH")
//                .allowCredentials(true)
//                .allowedOriginPatterns("http://localhost:8080/")
//                .maxAge(3600L);
//        //nếu thêm cho role khác thì corsRegistry.addMapping().... tiếp
//    }
//}
