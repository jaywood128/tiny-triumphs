package com.tiny.triumph.model;

import jakarta.persistence.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user_")
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
    @Size(min = 6, max = 15)
    @Column(name = "password", nullable = false)
    private String password;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    public List<Todo> todos = new ArrayList<>();

    public User(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.todos = new ArrayList<>();
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.todos = new ArrayList<>();
    }
    public User() {
        // default constructor with no parameters
    }

    public User(int id, String firstName, String lastName, String email, List<Todo> todos) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.todos = todos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(todos, user.todos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, password, todos);
    }

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

    public String getPassword(){return this.password;}

    public void setPassword(String password) {
        this.password = password;
    }
}
