package com.example.testasync.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;

/*
Khi gọi request thì nó sẽ check cors trước, nó sẽ vào các policy mình cấu hình để check, nếu thỏa mãn thì mới cho đi tiếp
Có 4 cách cấu hình CORS:
- Cách 1: Dùng @CrossOrigin trên từng api cụ thể hoặc trên đầu của 1 class Controller
- Cách 2: Dùng WebMvcConfigurer, tuy nhiên nếu project có spring security thì sẽ bị ghi đè (nên dùng khi project ko cấu hình Spring Security)
- Cách 3: Dùng CorsConfigurationSource với Spring Security (Cách này chuẩn nhất)
- Cách 4: Dùng CorsFilter khi ko dùng Spring Security
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsConfiguration() {
        CorsConfiguration config = getCorsConfiguration();
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**", config); //tham số pattern /** là dùng cho mọi api, nếu muốn chỉ dùng cho 1 loại api cụ thể thì như này /api/product/** (** là vô số cấp đằng sau, còn * là chỉ 1 cấp sau nó)
        return new CorsFilter(corsConfigurationSource);
    }

    private static CorsConfiguration getCorsConfiguration() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Collections.singletonList("https://your-ui-domain.com")); //Cho phép nguồn cụ thể
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE")); //Cho phép các method cần thiết
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); //Cho phép Header quan trọng
        config.setAllowCredentials(true); //Cho phép gửi Cookie/Token, bật cái này lên thì ko dùng addAllowedOrigin("*") được
        config.setMaxAge(3600L); //Cache request pre-flight trong 1 tiếng
        return config;
    }

}
