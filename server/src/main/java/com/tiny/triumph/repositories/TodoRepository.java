package com.tiny.triumph.repositories;

import com.tiny.triumph.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    // Query User by email
    Optional<Todo> findToDoByDescription(String description);
}

