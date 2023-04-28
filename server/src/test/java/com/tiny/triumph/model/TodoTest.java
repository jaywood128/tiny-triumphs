package com.tiny.triumph.model;

import com.tiny.triumph.config.TestConfig;
import com.tiny.triumph.repositories.TodoRepository;
import com.tiny.triumph.services.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@DataJpaTest
@Import(TestConfig.class)
@ComponentScan
public class TodoTest {
    public TodoTest() {
    }

    @Mock
    private Todo todoMock;

    @Mock
    private User userMock;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this); // Initialize Mockito
    }

    @Test
    public void test_validateTodoDescriptionLength() {
        Todo todo = new Todo("", false, LocalDateTime.now());

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Todo>> violations = validator.validate(todo);

            assertThat(violations.size() == 2).isEqualTo(true);
        }
    }
    @Test
    public void test_description(){

        todoMock.setDescription("Bake some cookies");

        // set the expected return value for the getDescription() method
        when(todoMock.getDescription()).thenReturn("Test todo");

        // associate the mock Todo object with the mock User object
        when(userMock.getTodos()).thenReturn(List.of(todoMock));

        // set user's first name
        when(userMock.getFirstName()).thenReturn("Ted");

        // call the getDescription() method on the Todo object associated with the User object
        String description = userMock.getTodos().get(0).getDescription();

        // get actual first name

        String firstName = userMock.getFirstName();

        // assert that the returned value is equal to the expected value
        assertEquals("Test todo", description);
        assertEquals("Ted", firstName);
    }

    @Test
    public void test_dueDate(){
        LocalDateTime expectedDueDate = LocalDateTime.now();
        todoMock.setDueDate(expectedDueDate);

        // set the expected return value for the getDescription() method
        when(todoMock.getDueDate()).thenReturn(expectedDueDate);

        // associate the mock Todo object with the mock User object
        when(userMock.getTodos()).thenReturn(List.of(todoMock));

        // set user's first name
        when(userMock.getFirstName()).thenReturn("Ted");

        // call the getDueDate() method on the Todo object associated with the User object
        LocalDateTime actualDueDate = userMock.getTodos().get(0).getDueDate();

        // assert that the returned value is equal to the expected value
        assertEquals(expectedDueDate, actualDueDate);
    }
}
