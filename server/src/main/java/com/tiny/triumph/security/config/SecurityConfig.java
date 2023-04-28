package com.tiny.triumph.security.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(form -> {
                    try {
                        form
                                .loginPage("/api/login")
                                    .permitAll()
                                .and()
                                    .logout()
                                    .logoutSuccessUrl("/")
                                    .permitAll()
                                .and()
                                .   authorizeHttpRequests((authz) -> {
                                        try {
                                            authz
                                                .requestMatchers("/api/register").permitAll()
                                                .requestMatchers("/api/logout").permitAll()
                                                .requestMatchers("/api/users").permitAll()
                                                .requestMatchers("/api/users/*").permitAll()
                                                .requestMatchers("/api/todos/*").permitAll()
                                                    .requestMatchers("/api/todo/*").permitAll()
                                                .requestMatchers("/error*").permitAll();
                                        } catch (Exception e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                )
                                .httpBasic(withDefaults());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
        http.cors().and().csrf().disable();
        return http.build();
    }

    @Bean
    @Primary
    public PasswordEncoder delegatingPasswordEncoder() {
        PasswordEncoder defaultEncoder = new StandardPasswordEncoder();
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put("bcrypt", new BCryptPasswordEncoder());

        DelegatingPasswordEncoder passwordEncoder = new DelegatingPasswordEncoder(
                "bcrypt", encoders);
        passwordEncoder.setDefaultPasswordEncoderForMatches(defaultEncoder);

        return passwordEncoder;
    }

    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedMethods(List.of(
                HttpMethod.GET.name(),
                HttpMethod.PUT.name(),
                HttpMethod.POST.name(),
                HttpMethod.DELETE.name()
        ));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration.applyPermitDefaultValues());
        return source;
    }
}
