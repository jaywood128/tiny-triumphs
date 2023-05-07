package com.tiny.triumph.config;

import com.tiny.triumph.repositories.UserRepository;
import com.tiny.triumph.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@TestConfiguration
public class TestConfig {

    @Autowired
    UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder mockBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userRepository);
    }

    @Bean
    public HandlerMappingIntrospector mvcHandlerMappingIntrospector() {
        return new HandlerMappingIntrospector();
    }

}
