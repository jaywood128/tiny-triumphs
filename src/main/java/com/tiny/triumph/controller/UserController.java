package com.tiny.triumph.controller;

import com.tiny.triumph.model.User;
import com.tiny.triumph.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping(value="/users")
    public List<User> getAllStudents(){
        return userService.getAllUsers();
    }
    @GetMapping(value="/users/{id}")
//    public User getStudentById(@PathVariable("id") @Min(1) int id) {
//        User usr = userService.findById(id)
//                .orElseThrow(()->new StudentNotFoundException("User with "+id+" is Not Found!"));
//        return usr;
//    }
    @PostMapping(value="/users")
    public User addStudent(@Valid @RequestBody User usr) {
        return userService.save(usr);
    }
    @PutMapping(value="/users/{id}")
//    public User updateStudent(@PathVariable("id") @Min(1) int id, @Valid @RequestBody User newUser) {
//        User usr = userService.findById(id)
//                .orElseThrow(()->new StudentNotFoundException("User with "+id+" is Not Found!"));
//        usr.setFirstName(newUser.getFirstName());
//        usr.setLastName(newUser.getLastName());
//        usr.setEmail(newUser.getEmail());
//        return userService.save(usr);
//    }
    @DeleteMapping(value="/users/{id}")
    public String deleteStudent(@PathVariable("id") @Min(1) int id) {
//        User usr = userService.findById(id)
//                .orElseThrow(()->new StudentNotFoundException("User with "+id+" is Not Found!"));
//        userService.deleteById(usr.getId());
//        return "User with ID :"+id+" is deleted";
        return "";
    }
}
