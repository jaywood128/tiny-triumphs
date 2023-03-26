package com.tiny.triumph.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
public class SecurityConfig {

 @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
             return    http
                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("/api/users/**").permitAll()
                .anyRequest().anonymous()
                .and()
//                .httpBasic()
//                .authenticationEntryPoint((request, response, authException) -> response.sendError(
//                        HttpServletResponse.SC_UNAUTHORIZED))
//                .and()
                .formLogin().disable()
                .logout().disable().build();
    }
}