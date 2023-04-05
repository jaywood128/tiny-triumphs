package com.tiny.triumph.repository;

import com.tiny.triumph.enums.Priority;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;


@RunWith(SpringRunner.class)
@DataJpaTest
@TestPropertySource(
        locations = "classpath:application-integration-test.properties")
public class TodoRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    TodoRepository todoRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @Test
    public void should_save_a_todo_success(){

        // given
        Todo todo = new Todo("Bake chocolate chip cookies", false, LocalDateTime.now(), Priority.HIGH, new User());
        Todo savedTodo = todoRepository.save(todo);

        assertThat(savedTodo.getId()).isNotNull();
        assertThat(savedTodo.getDescription()).isEqualTo(todo.getDescription());
        assertThat(savedTodo.isComplete()).isEqualTo(todo.isComplete());
        assertThat(savedTodo.getDueDate()).isEqualTo(todo.getDueDate());
        assertThat(savedTodo.getPriority()).isEqualTo(todo.getPriority());
        assertThat(savedTodo.getUser()).isEqualTo(todo.getUser());

    }

    @Test
    public void test_save_todo_success(){
        // User
        User user = new User("Ted", "Lasso", "tlasso@richmond.io");
        testEntityManager.persist(user);
        testEntityManager.flush();
//         given
        Todo todo = new Todo("Bake chocolate chip cookies", false, LocalDateTime.now(), Priority.HIGH, user);

        // when
        testEntityManager.persist(todo);
        testEntityManager.flush();

        Optional<Todo> foundTodo = todoRepository.findToDoByDescription(todo.getDescription());

        assertThat(foundTodo.get().getId()).isNotNull();
        assertThat(foundTodo.get().getDescription()).isEqualTo(todo.getDescription());
        assertThat(foundTodo.get().isComplete()).isEqualTo(todo.isComplete());
        assertThat(foundTodo.get().getDueDate()).isEqualTo(todo.getDueDate());
        assertThat(foundTodo.get().getPriority()).isEqualTo(todo.getPriority());
        assertThat(foundTodo.get().getUser()).isEqualTo(todo.getUser());
    }
    @Test
    public void should_delete_todo_success(){
        // Create a user
        User user = new User();
        user.setFirstName("Roy");
        user.setLastName("Kent");
        user.setEmail("rkent@richmond.io");

        // Persist the user
        entityManager.persist(user);
        entityManager.flush();

        // Create a todo and set the user
        Todo todo = new Todo("Bake chocolate chip cookies", false, LocalDateTime.now(), Priority.HIGH, user);

        // Persist the todo
        entityManager.persist(todo);
        entityManager.flush();

        // Delete the todo
        todoRepository.delete(todo);

        // Try to find it by id
        Optional<Todo> deletedTodo = todoRepository.findById(todo.getId());

        // Assert that it can't be found
        assertFalse(deletedTodo.isPresent());

    }
}
