package com.tiny.triumph.controller;

import com.tiny.triumph.exceptions.UserNotFoundException;
import com.tiny.triumph.model.User;
import com.tiny.triumph.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value="/users")
    public ResponseEntity<List <User>> getAllUsers(){
        System.out.println("Hello");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.ACCEPTED);
    }
    @GetMapping(value="/users/{id}")
    public User getUserById(@PathVariable("id") @Min(1) int id) {
        User usr = userService.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with "+id+" is Not Found!"));
        return usr;
    }
    // Todo add field validation
    @PostMapping(value="/users")
    public User addUser(@Valid @RequestBody User usr) {
        return userService.save(usr);
    }
    @PutMapping(value="/users/{id}")
    public User updateUser(@PathVariable("id") @Min(1) int id, @Valid @RequestBody User newUser) {
        User usr = userService.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with "+id+" is Not Found!"));
        usr.setFirstName(newUser.getFirstName());
        usr.setLastName(newUser.getLastName());
        usr.setEmail(newUser.getEmail());
        return userService.save(usr);
    }
    @DeleteMapping(value="/users/{id}")
    public String deleteUser(@PathVariable("id") @Min(1) int id) {
        User usr = userService.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with "+id+" is Not Found!"));
        userService.deleteById(usr.getId());
        return "User with ID :"+id+" is deleted";
    }
}
