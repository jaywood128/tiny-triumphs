package com.tiny.triumph.controller;


import com.tiny.triumph.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class TodoListController {
    UserService userService;
    @Autowired
    TodoListController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(value = "/todos", produces = "application/json")
    public String getTodos(){
        return "Get milk, buy eggs";
    }

    // create a todo
    @PostMapping(value = "/todos/{userId}",  produces = "application/json")
    public HttpStatus createTodo(@PathVariable String userId, @RequestBody String createTodoRequest){
        return HttpStatus.CREATED;
    }
    // get all todos for a user

    // update a todo

    // delete a todo
}
