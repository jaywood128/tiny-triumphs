package com.tiny.triumph.model;

import com.tiny.triumph.repositories.TodoRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TodoTest {
    public TodoTest() {
    }

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private TodoRepository todoRepository;

    @Test
    public void test_validateTodoDescriptionLength(){

        Todo todo = new Todo("", false, LocalDateTime.now());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);

        assertThat(violations.size() == 2);

    }
}
