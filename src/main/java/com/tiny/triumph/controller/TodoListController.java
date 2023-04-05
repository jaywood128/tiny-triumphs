package com.tiny.triumph.controller;


import com.tiny.triumph.exceptions.ConstraintsViolationsException;
import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.payload.CreateTodoRequestBody;
import com.tiny.triumph.payload.TodoRequestBody;
import com.tiny.triumph.services.TodoService;
import com.tiny.triumph.services.UserService;
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
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class TodoListController {
    UserService userService;
    TodoService todoService;
    @Autowired
    TodoListController(TodoService todoService, UserService userService){
        this.userService = userService;
        this.todoService = todoService;
    }

    // Assuming that you have a method that throws a ConstraintViolationExceptio

    @PostMapping(value = "/todo/{userId}",  produces = "application/json")
    public ResponseEntity<Todo> createTodo(@PathVariable String userId, @RequestBody CreateTodoRequestBody todoRequest){

        // Todo refactor Todo validation layer by putting logic in TodoService https://www.baeldung.com/spring-service-layer-validation
        Optional<User> user = userService.findById(Integer.valueOf(userId));

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Todo todo = new Todo(todoRequest.getDescription(), todoRequest.getIsComplete(), todoRequest.getDueDate(), todoRequest.getPriority(), user.get());

        Set<ConstraintViolation<Todo>> violations = validator.validate(todo);

        if(violations.size() > 0){
            throw new ConstraintsViolationsException(violations);
        }

        todoService.addTodo(Integer.valueOf(userId), todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }
    @ExceptionHandler(value = { ResourceNotFoundException.class })
    @GetMapping (value = "/todo/{id}",  produces = "application/json")
    public ResponseEntity<Todo> getTodo(@PathVariable String id){
        // Ensure user passes valid token, and the token has read permission
        Optional<Todo> todo = todoService.findById(Integer.valueOf(id));
        if(todo.isEmpty()){
            throw new ResourceNotFoundException("A todo with " + id + " was not found");
        }
        return new ResponseEntity<>(todo.get(), HttpStatus.ACCEPTED);
    }
    // get all of a user's todos
    @GetMapping (value = "/todos/{userId}",  produces = "application/json")
    public ResponseEntity<List<Todo>> getTodos(@PathVariable String userId){
        // Ensure user passes valid token, and the token has read permission
        Optional<User> foundUser = userService.findById(Integer.valueOf(userId));
        return new ResponseEntity<>(foundUser.get().todos, HttpStatus.ACCEPTED);
    }

    @PutMapping (value = "/todo",  produces = "application/json")
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoRequestBody todoRequest){
        // Todo Ensure user passes valid token, and the token the correct permissions
        Optional<Todo> existingTodo = todoService.findById(todoRequest.getId());

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
