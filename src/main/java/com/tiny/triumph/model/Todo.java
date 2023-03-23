package com.tiny.triumph.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @ManyToOne
    // @JoinColumn annotation defines the actual physical mapping on the owning side //
    @JoinColumn(name="user_id")
    public User user;

    public String description;
    @Column(name = "is_complete")
    boolean isComplete;
    /**
     * example usage dueDate = LocalDateTime.of(2023, 4, 15, 8, 30);
     */
    //@CreatedDate
    @Column(name = "due_date")
    public LocalDateTime dueDate;

    public Todo() {
    }

    public Todo(String description, boolean isComplete, LocalDateTime dueDate, User user) {
        this.description = description;
        this.isComplete = isComplete;
        this.dueDate = dueDate;
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Todo todo = (Todo) o;
        return isComplete == todo.isComplete && Objects.equals(id, todo.id) && description.equals(todo.description) && Objects.equals(dueDate, todo.dueDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description, isComplete, dueDate);
    }
}
