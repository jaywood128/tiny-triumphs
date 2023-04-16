package com.tiny.triumph.controller;

import com.tiny.triumph.TodoApplication;
import com.tiny.triumph.enums.Priority;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import com.tiny.triumph.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDateTime;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TodoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
@AutoConfigureTestEntityManager
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final LocalDateTime localDateTime = LocalDateTime.now();


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Before
    public void init() {

        MockitoAnnotations.openMocks(this); // Initialize Mockito

        // User
        User user = new User("Rebecca", "Welton", "rwelton@richmond.io", bCryptPasswordEncoder.encode("believe"));
        testEntityManager.persist(user);
        testEntityManager.flush();

        Todo todo = new Todo("Bake chocolate chip cookies", false, localDateTime, Priority.HIGH, user);
        testEntityManager.persist(todo);
        testEntityManager.flush();

    }

    @Transactional
    @Test
    public void given_userId_return_todos_200() throws Exception {
        Optional<User> user = userRepository.findByEmail("rwelton@richmond.io");
        assertThat(user.isPresent()).isEqualTo(true);

        mockMvc.perform(get("/api/todo/{userId}", user.get().id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.description", is("Bake chocolate chip cookies")))
                .andExpect(jsonPath("$.dueDate", is(localDateTime.toString())));

    }
}
