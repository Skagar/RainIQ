package com.rainiq.auth_service.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
        http.csrf(csrf ->csrf.disable()).authorizeHttpRequests(auth ->
                auth.requestMatchers(
                        "/api/auth/**",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/api-docs/**"
                ).permitAll().anyRequest().authenticated()).sessionManagement(
                        session ->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) //STATELESS=>Never create a session. Never store authentication in memory. Every request must carry its own proof of identity — a JWT token
        );
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
