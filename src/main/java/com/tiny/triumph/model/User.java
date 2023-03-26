package com.tiny.triumph.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    @NotEmpty(message = "First name is required")
    @Column(name = "first_name")
    public String firstName;

    @NotEmpty(message = "Last name is required")
    @Column(name = "last_name")
    public String lastName;
    @Email(message = "Email should be valid")
    @Column(unique=true)
    public String email;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Todo> todos = new ArrayList<>();

    public User(int id, String firstName, String lastName, String email, List<Todo> updatedTodos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.todos = updatedTodos;
    }
    public User() {
        // default constructor with no parameters
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && firstName.equals(user.firstName) && lastName.equals(user.lastName) && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email);
    }

    public Todo withIsCompleted(boolean isComplete, int todoId) {
        Todo todo = (Todo) this.todos.stream().filter(todoArg -> todoArg.getId() == todoId);
        return new Todo(todo.getDescription(), isComplete, todo.getDueDate(), this);
    }

//    public Todo withIsCompleted(boolean isCompleted) {
//        return new Todo(this this.description, isCompleted);
//    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }
}
