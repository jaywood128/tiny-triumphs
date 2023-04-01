 package com.tiny.triumph.model;

 import com.fasterxml.jackson.annotation.JsonIgnore;
 import jakarta.persistence.*;

 import javax.validation.constraints.NotEmpty;
 import javax.validation.constraints.Size;
 import java.time.LocalDateTime;
 import java.util.ArrayList;
 import java.util.List;
 import java.util.Objects;
 import java.util.stream.Collectors;

@Entity
@Table(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @JsonIgnore
    @ManyToOne
    // @JoinColumn annotation defines the actual physical mapping on the owning side //
    @JoinColumn(name="user_id")
    public User user;

    @Size(min = 1, max = 300, message
            = "The description should be between 1 and 300 characters")
    @NotEmpty
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
    public Todo(int id, String description, boolean isComplete, LocalDateTime dueDate, User user) {
        this.id = id;
        this.description = description;
        this.isComplete = isComplete;
        this.dueDate = dueDate;
        this.user = user;
    }

    public Todo(String description, boolean isComplete, LocalDateTime dueDate) {
        this.description = description;
        this.isComplete = isComplete;
        this.dueDate = dueDate;
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

    public void setUser(User user) {
        this.user = user;
    }

    public void addTodo(Todo todo, User user){
        user.todos.add(todo);
       for(Todo todo1 : user.todos){
           System.out.println(todo1.getId() + " " + todo1.getDescription() + " " + todo1.getDueDate());
       }
    }

    public User updateTodo(int todoId, User user, Todo newTodo){
        // Find todo we want to update
        Todo updateMe = user.todos.stream()
                .filter(t -> t.getId() == todoId)
                .findFirst()
                .orElseThrow();

        List<Todo> updatedTodos = new ArrayList<>();
        User newUser = new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), updatedTodos);
        // copy existing todos
        List<Todo> existingTodos =  user.todos.stream().filter(t -> t.getId() != todoId)
                .collect(Collectors.toList());
        for(Todo todo1 : existingTodos){
            newUser.todos.add(todo1);
        }
        updatedTodos.add(newTodo);
        newUser.todos.addAll(updatedTodos);
        return newUser;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
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
