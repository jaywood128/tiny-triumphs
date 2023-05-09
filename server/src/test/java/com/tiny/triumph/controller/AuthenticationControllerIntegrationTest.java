package com.tiny.triumph.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiny.triumph.dto.AuthenticationRequest;
import com.tiny.triumph.dto.RegistrationRequest;
import com.tiny.triumph.exceptions.UserNotFoundException;
import com.tiny.triumph.security.token.TokenRepository;
import com.tiny.triumph.services.UserServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.json.Json;
import java.io.IOException;
import java.io.StringReader;

import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TokenRepository tokenRepository;

    @BeforeEach
    public void init() throws IOException {
        MockitoAnnotations.openMocks(this); // Initialize Mockito
        userServiceImpl.deleteAllUsers();
        tokenRepository.deleteAll();

    }
    @Transactional
    @Test
    public void register_user_success() throws Exception, UserNotFoundException {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RegistrationRequest("Rebbecca", "Welton", "rwelton@richmond.io", "abc12345")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isCreated()).andReturn();
                      assertTrue("User with an email tlasso@richmond.io is returned from database", userServiceImpl.findByEmail("rwelton@richmond.io").isPresent());
    }
    @Transactional
    @Test
    public void access_protected_resources_returns_unauthorized() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new RegistrationRequest("Ted", "Lasso", "tlasso@richmond.io", "abc12345")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isForbidden());
    }

    @Transactional
    @Test
    public void access_protected_resources_returns_success() throws Exception {

        MvcResult registerResult =   mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new RegistrationRequest("Roy", "Kent", "roy.kent@gmail.com", "abc12345")))
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        String responseBody = registerResult.getResponse().getContentAsString();
        String token = Json.createReader(new StringReader(responseBody)).readObject().get("token").toString();
        String authHeader = "Bearer " + token.replace("\"", "");

       mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", authHeader)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

        MvcResult authenticateResult =   mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/authenticate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new AuthenticationRequest( "roy.kent@gmail.com", "abc12345")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/logout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", authHeader)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
