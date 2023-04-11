package com.tiny.triumph.services;

import com.tiny.triumph.model.User;

import java.util.List;
import java.util.Optional;

public interface IUser {
    List<User> getAllUsers();
    Optional<User> findById(int id);
    Optional<User> findByEmail(String email);
    User save(User std);
    void deleteById(int id);
}
