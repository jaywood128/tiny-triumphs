package com.tiny.triumph.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tiny.triumph.TodoApplication;
import com.tiny.triumph.enums.Priority;
import com.tiny.triumph.enums.Role;
import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.services.TodoService;
import com.tiny.triumph.services.UserServiceImpl;
import jakarta.servlet.ServletContext;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = TodoApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
@WithMockUser
public class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    public UserServiceImpl userServiceImpl;

    @Autowired
    public TodoService todoService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private Todo[] expectedTodos;

    private ClassPathResource classPathResource;

    @Autowired
    @Qualifier("mockBCryptPasswordEncoder")
    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public LocalDateTime localDateTime = LocalDateTime.now();


    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }
    @Transactional
    @Before
    public void init() throws IOException {

        MockitoAnnotations.openMocks(this); // Initialize Mockito

        // User
        User user = new User("Rebecca", "Welton", "rwelton@richmond.io", bCryptPasswordEncoder.encode("believe"), Role.USER);
        userServiceImpl.save(user);

        Todo todo = new Todo("Bake chocolate chip cookies", false, localDateTime, Priority.HIGH, user);
        todoService.addTodo(user.getId(), todo);

        classPathResource = new ClassPathResource("test-data/response-payload/todo-controller-integration-get-todos.json");
        expectedTodos = objectMapper.readValue(classPathResource.getFile(), Todo[].class);

    }
    @Test
    public void saved_User(){
        Optional<User> foundUser = userServiceImpl.findByEmail("rwelton@richmond.io");
        assertTrue(foundUser.get().getEmail().equals("rwelton@richmond.io"));
    }
    @Test
    public void saved_Todo() throws ResourceNotFoundException {
        Optional<Todo> foundTodo = todoService.findToDoByDescription("Bake chocolate chip cookies");
        assertTrue(foundTodo.isPresent());
        assertTrue(foundTodo.get().description.equals("Bake chocolate chip cookies"));
    }
    @Transactional
    @Test
    public void givenWac_whenServletContext_thenItProvidesGreetController() {
        ServletContext servletContext = webApplicationContext.getServletContext();
        Assert.assertNotNull(servletContext);
        Assert.assertTrue(servletContext instanceof MockServletContext);
        System.out.println("Bean definition names: " + webApplicationContext.getBeanDefinitionNames().toString());
    }

    @Transactional
    @Test
    public void when_todos_are_found() throws Exception, ResourceNotFoundException {
        Optional<User> user = userServiceImpl.findByEmail("rwelton@richmond.io");
        assertThat(todoService.findToDoByDescription("Bake chocolate chip cookies").get().getUser().getFirstName().equals("Rebecca"));
        String json = loadJsonFromFile("test-data/response-payload/todo-controller-integration-get-todos.json");
        System.out.println("Load todo JSON " + json);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/todos/{id}", user.get().getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].description").value(expectedTodos[0].getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].isComplete").value(expectedTodos[0].getIsComplete()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].priority").value(expectedTodos[0].getPriority().toString()))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        Todo[] actualTodos = objectMapper.readValue(responseContent, Todo[].class);
        // Assert that the response content matches the expectedTodo object
        Arrays.equals(actualTodos, expectedTodos);

    }
    @Test
    /**
     * successfully add todos and retrieve them from the database
     * */


    private String loadJsonFromFile(String fileName) throws IOException {
        Resource resource = new ClassPathResource(fileName);
        InputStream inputStream = resource.getInputStream();
        return IOUtils.toString(inputStream, StandardCharsets.UTF_8);
    }
}
