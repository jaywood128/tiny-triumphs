package com.tiny.triumph.repositories;

import com.tiny.triumph.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Query User by email
    Optional<User> findByEmail(String email);
}
