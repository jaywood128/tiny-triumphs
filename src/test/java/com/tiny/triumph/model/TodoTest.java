package com.tiny.triumph.model;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDateTime;
import java.util.Set;

public class TodoTest {


    @Test
    public void validateTodoDescriptionLength(){

        Todo todo = new Todo("", false, LocalDateTime.now());
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);

        assert violations.size() == 2;

    }
}
