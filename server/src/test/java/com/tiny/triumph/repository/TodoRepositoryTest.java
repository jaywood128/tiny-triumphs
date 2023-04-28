package com.tiny.triumph.repository;

import com.tiny.triumph.config.TestConfig;
import com.tiny.triumph.enums.Priority;
import com.tiny.triumph.enums.Role;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
@Import(TestConfig.class)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    @InjectMocks
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public TodoRepositoryTest() {
    }

    @Before
    public void init() {

        MockitoAnnotations.openMocks(this); // Initialize Mockito

        // Mock PasswordEncoder
        // Example using Mockito:
        String encodedPassword = bCryptPasswordEncoder.encode("believe");
        // User
        User user = new User("Ted", "Lasso", "tlasso@richmond.io", bCryptPasswordEncoder.encode(encodedPassword), Role.USER);
        testEntityManager.persist(user);
        testEntityManager.flush();
        Todo todo = new Todo("Bake chocolate chip cookies", false, LocalDateTime.now(), Priority.HIGH, user);
        testEntityManager.persist(todo);
        testEntityManager.flush();

    }

    @Test
    public void should_save_a_todo_success() {
        Optional<Todo> savedTodo = todoRepository.findToDoByDescription("Bake chocolate chip cookies");
        assertThat(savedTodo.isPresent()).isEqualTo(true);
        assertThat(savedTodo.get().description.equals("Bake chocolate chip cookies")).isEqualTo(true);
        assertThat(savedTodo.get().getUser().getEmail().equals("tlasso@richmond.io")).isEqualTo(true);
    }

    @Test
    public void should_delete_todo_success() {
        Optional<Todo> deleteMe = todoRepository.findToDoByDescription("Bake chocolate chip cookies");

        assertThat(deleteMe.isPresent()).isEqualTo(true);


        // Delete the todo
        todoRepository.delete(deleteMe.get());

        // Try to find it by id
        Optional<Todo> deletedTodo = todoRepository.findById(deleteMe.get().getId());

        // Assert that it can't be found
        assertTrue(deletedTodo.isEmpty());

    }
}
