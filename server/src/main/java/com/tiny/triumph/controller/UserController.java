package com.tiny.triumph.controller;

import com.tiny.triumph.exceptions.UserNotFoundException;
import com.tiny.triumph.model.User;
import com.tiny.triumph.services.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
    }

    @GetMapping(value="/users")
    public ResponseEntity<List <User>> getAllUsers(){
        System.out.println("Hello");
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.ACCEPTED);
    }
    @GetMapping(value="/users/{id}")
    public User getUserById(@PathVariable("id") int id) throws UserNotFoundException {
        User usr = userServiceImpl.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with "+id+" is Not Found!"));
        return usr;
    }

    @PutMapping(value="/users/{id}")
    public User updateUser(@PathVariable("id") int id, @Valid @RequestBody User newUser) throws UserNotFoundException {
        User usr = userServiceImpl.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with "+id+" is Not Found!"));
        usr.setFirstName(newUser.getFirstName());
        usr.setLastName(newUser.getLastName());
        usr.setEmail(newUser.getEmail());
        return userServiceImpl.save(usr);
    }
    @DeleteMapping(value="/users/{id}")
    public HttpStatus deleteUser(@PathVariable("id") int id) throws UserNotFoundException {
        User usr = userServiceImpl.findById(id)
                .orElseThrow(()->new UserNotFoundException("User with "+id+" is Not Found!"));
        userServiceImpl.deleteById(usr.getId());
        return HttpStatus.NO_CONTENT;
    }
}
