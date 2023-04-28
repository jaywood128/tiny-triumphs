package com.tiny.triumph.controller;


import com.tiny.triumph.exceptions.TodoConstraintsViolationsException;
import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.exceptions.UserNotFoundException;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.payload.CreateTodoRequestBody;
import com.tiny.triumph.payload.TodoRequestBody;
import com.tiny.triumph.services.TodoService;
import com.tiny.triumph.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class TodoListController {
    UserServiceImpl userServiceImpl;
    TodoService todoService;
    @Autowired
    TodoListController(TodoService todoService, UserServiceImpl userServiceImpl){
        this.userServiceImpl = userServiceImpl;
        this.todoService = todoService;
    }

    @PostMapping(value = "/todo/{userId}",  produces = "application/json")
    public ResponseEntity<Todo> createTodo(@PathVariable String userId, @RequestBody CreateTodoRequestBody todoRequest) throws UserNotFoundException {

        // Todo refactor Todo validation layer by putting logic in TodoService https://www.baeldung.com/spring-service-layer-validation
        Optional<User> user = userServiceImpl.findById(Integer.parseInt(userId));
        if(user.isEmpty()){
            throw new UserNotFoundException("No user with Id " + userId + " does not exist");
        }
        Todo todo = new Todo(todoRequest.getDescription(), todoRequest.getIsComplete(), todoRequest.getDueDate(), todoRequest.getPriority(), user.get());

        try(ValidatorFactory factory = Validation.buildDefaultValidatorFactory()){
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<Todo>> violations = validator.validate(todo);

            if(violations.size() > 0){
                throw new TodoConstraintsViolationsException(violations);
            }
        }
        todoService.addTodo(Integer.valueOf(userId), todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    @GetMapping (value = "/todo/{id}",  produces = "application/json")
    public ResponseEntity<Todo> getTodo(@PathVariable String id) throws ResourceNotFoundException {
        // Ensure user passes valid token, and the token has read permission
        Optional<Todo> todo = todoService.findById(Integer.parseInt(id));
        if(todo.isEmpty()){
            throw new ResourceNotFoundException("A todo with " + id + " was not found");
        }
        return new ResponseEntity<>(todo.get(), HttpStatus.OK);
    }
    // get a user's todos
    @ExceptionHandler(value = { UserNotFoundException.class })
    @GetMapping (value = "/todos/{userId}",  produces = "application/json")
    public ResponseEntity<List<Todo>> getTodos(@PathVariable String userId) throws UserNotFoundException {
        // Ensure user passes valid token, and the token has read permission

        Optional<User> foundUser = userServiceImpl.findById(Integer.valueOf(userId));
        if(foundUser.isEmpty()){
            throw new UserNotFoundException("A user with the Id of " + userId + " was not found");
        }
        return new ResponseEntity<>(foundUser.get().getTodos(), HttpStatus.ACCEPTED);
    }

    @PutMapping (value = "/todo",  produces = "application/json")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoRequestBody todoRequest) throws ResourceNotFoundException {
        // Todo Ensure user passes valid token, and the token the correct permissions
        Optional<Todo> existingTodo = todoService.findById(todoRequest.getId());

        if(existingTodo.isEmpty()){
            throw new ResourceNotFoundException("There is no todo with id " + todoRequest.id);
        }

        Todo updatedTodo = new Todo(
                existingTodo.get().getId(),
                !todoRequest.getDescription().isEmpty() ? todoRequest.getDescription() : existingTodo.get().getDescription(),
                todoRequest.getIsComplete() != existingTodo.get().isComplete() ? todoRequest.getIsComplete() : existingTodo.get().isComplete(),
                todoRequest.getDueDate() != null ? todoRequest.getDueDate() : existingTodo.get().getDueDate(),
                todoRequest.getPriority() != null ? todoRequest.getPriority() : existingTodo.get().getPriority(),
                existingTodo.get().getUser()
        );
        todoService.updateTodo(updatedTodo, existingTodo.get().getUser());
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping(value = "/todo/{id}",  produces = "application/json")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable String id ){
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
