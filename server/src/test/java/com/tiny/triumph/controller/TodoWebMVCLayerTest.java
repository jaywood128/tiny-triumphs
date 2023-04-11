package com.tiny.triumph.controller;

import com.tiny.triumph.TodoApplication;
import com.tiny.triumph.enums.Priority;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import com.tiny.triumph.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

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
public class TodoWebMVCLayerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoRepository repository;

    @Autowired
    private TodoRepository todoRepository;
    @Autowired
    public UserRepository userRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Before
    public void init(){
        // User
        User user = new User("Ted", "Lasso", "tlasso@richmond.io");
        testEntityManager.persist(user);
        testEntityManager.flush();
        Todo todo = new Todo("Bake chocolate chip cookies", false, LocalDateTime.now(), Priority.HIGH, user);
        testEntityManager.persist(todo);
        testEntityManager.flush();

    }
    @Transactional
    @Test
    public void given_userId_return_todos_200() throws Exception {
        init();
        Optional<User> user = userRepository.findById(1);

        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl
                = "http://localhost:8080/api/todos/1";
        ResponseEntity<String> response
                = restTemplate.getForEntity(fooResourceUrl, String.class);
        Assertions.assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);
        String expectedResponse = "[{\"id\":1,\"description\":\"Learn football\",\"dueDate\":\"2023-04-15T08:30:00\",\"priority\":\"HIGH\",\"complete\":false}]";
        Assertions.assertEquals(expectedResponse, response.getBody());

    }
}
