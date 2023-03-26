package com.tiny.triumph.services;

import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import com.tiny.triumph.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUser {
    UserRepository userRepository;
    TodoRepository todoRepository;

    @Autowired
    public UserService(UserRepository userRepository, TodoRepository todoRepository){
        this.userRepository = userRepository;
        this.todoRepository = todoRepository;
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User std) {
       return userRepository.save(std);
    }

    @Override
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }
}
