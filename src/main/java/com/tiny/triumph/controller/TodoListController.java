package com.tiny.triumph.controller;


import com.tiny.triumph.exceptions.ResourceNotFoundException;
import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.services.TodoService;
import com.tiny.triumph.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping(value = "/todos/{userId}",  produces = "application/json")
    public ResponseEntity<Todo> createTodo(@PathVariable String userId, @RequestBody Todo todoRequest){
        Optional<User> user = userService.findById(Integer.valueOf(userId));
        // grab each field off the request body to create a new Todo
        Todo todo = new Todo(todoRequest.getDescription(), todoRequest.isComplete(), todoRequest.getDueDate(), user.get());
        todoService.addTodo(Integer.valueOf(userId), todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }
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

    // update a todo

    @PutMapping (value = "/todos/{id}",  produces = "application/json")
    public ResponseEntity<Todo> updateTodo(@PathVariable String userId, @RequestBody Todo todoRequest){
        // Ensure user passes valid token, and the token the correct permissions
        Optional<Todo> existingTodo = todoService.findById(Integer.valueOf(userId));
        //Pull off each field from the request body but use the existing id of the todo
        Todo updatedTodo = new Todo(todoRequest.getId(), todoRequest.getDescription(), todoRequest.isComplete(), todoRequest.getDueDate(), todoRequest.user);
        todoService.updateTodo(updatedTodo, existingTodo.get().getUser());
        return ResponseEntity.ok(updatedTodo);
    }

    @DeleteMapping(value = "/todos/{id}",  produces = "application/json")
    public ResponseEntity<HttpStatus> deleteTodo(@PathVariable String id ){
        todoService.deleteTodo(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
