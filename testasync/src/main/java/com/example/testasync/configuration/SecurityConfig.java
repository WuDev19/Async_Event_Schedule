package com.example.testasync.configuration;

import com.example.testasync.utils.JwtFilters;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Autowired
    private JwtFilters jwtFilters;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(author ->
                        author
                                .requestMatchers(HttpMethod.GET, "/api/user/get/all")
                                .hasAuthority("SCOPE_ADMIN")

                                .anyRequest()
                                .permitAll()
                )
                .sessionManagement(sm ->
                        sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                //dùng oauth2 thì nên dùng hasAuthority, phải thêm decoder, nếu dùng hasRole thì thêm converter để oauth2 hiểu
                .oauth2ResourceServer(oauth2 ->
                        oauth2.jwt(jwtConfigurer -> jwtConfigurer
                                        .decoder(jwtDecoder())
//                                .jwtAuthenticationConverter(converter())
                        ))
                .build();
    }

    private JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withSecretKey(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    //nếu dùng hasRole thì phải dùng converter để cho oauth2 biết là role trong claim có ý nghĩa
//    private JwtAuthenticationConverter converter() {
//        JwtGrantedAuthoritiesConverter grant = new JwtGrantedAuthoritiesConverter();
//        grant.setAuthoritiesClaimName("role");
//        grant.setAuthorityPrefix("");
//        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
//        converter.setJwtGrantedAuthoritiesConverter(grant);
//        return converter;
//    }

}
