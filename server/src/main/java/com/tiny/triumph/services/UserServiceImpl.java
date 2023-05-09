package com.tiny.triumph.services;

import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl {
    UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    @Transactional

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    @Transactional
    public User save(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }
    @Transactional
    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllUsers(){
        userRepository.deleteAll();
    }
}
