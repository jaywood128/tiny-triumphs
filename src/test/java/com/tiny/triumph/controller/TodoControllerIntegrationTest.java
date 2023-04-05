package com.tiny.triumph.controller;

import com.tiny.triumph.TodoApplication;
import com.tiny.triumph.enums.Priority;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TodoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")

public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoRepository repository;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    public User create_user_with_todos(){
        // User
        User user = new User("Ted", "Lasso", "tlasso@richmond.io");
        testEntityManager.persist(user);
        testEntityManager.flush();
        Todo todo = new Todo("Bake chocolate chip cookies", false, LocalDateTime.now(), Priority.HIGH, user);
        testEntityManager.persist(user);
        testEntityManager.flush();
        testEntityManager.persist(todo);
        testEntityManager.flush();
        return user;

    }

    @Test
    public void given_userId_return_todos_200() throws Exception {
        User user = create_user_with_todos();
        mvc.perform(get("/api/todos/" + user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", Matchers.is("")));
    }
}
