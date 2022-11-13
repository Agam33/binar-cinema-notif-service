package com.ra.notification.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ra.notification.security.filters.AuthorizationJwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthEntryPoint authEntryPoint;

    private final AuthorizationJwtFilter authFilter;

    public SecurityConfig(AuthEntryPoint authEntryPoint, AuthorizationJwtFilter authFilter) {
        this.authEntryPoint = authEntryPoint;
        this.authFilter = authFilter;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin().disable();

        http
        .cors().and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/api/notification/**").hasRole("ADMIN")

        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authEntryPoint)

        .and()
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        
        ;
        return http.build();
    }
}
