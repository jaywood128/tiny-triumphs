 package com.tiny.triumph.model;

 import com.fasterxml.jackson.annotation.JsonIgnore;
 import com.tiny.triumph.enums.Priority;
 import com.tiny.triumph.enums.Role;
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
    @NotEmpty
    public String name;

    @Size(min = 1, max = 300, message
            = "The description should be between 1 and 300 characters")
    @NotEmpty
    public String description;

    @Column(name = "is_complete")
    public boolean isComplete;
    /**
     * example usage dueDate = LocalDateTime.of(2023, 4, 15, 8, 30);
     */
    //@CreatedDate
    @Column(name = "due_date")
    public LocalDateTime dueDate;
    public Priority priority;

    public Todo() {
    }

    public Todo(String name, String description, boolean isComplete, LocalDateTime dueDate, Priority priority, User user) {
        this.name = name;
        this.description = description;
        this.isComplete = isComplete;
        this.dueDate = dueDate;
        this.priority = priority;
        this.user = user;
    }
    public Todo(int id, String name, String description, boolean isComplete, LocalDateTime dueDate, Priority priority,  User user) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.isComplete = isComplete;
        this.dueDate = dueDate;
        this.priority = priority;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public User updateTodo(int todoId, User user, Todo newTodo){
        // Find todo we want to update
        Todo updateMe = user.getTodos().stream()
                .filter(t -> t.getId() == todoId)
                .findFirst()
                .orElseThrow();

        List<Todo> updatedTodos = new ArrayList<>();
        User newUser = new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), updatedTodos, Role.USER);
        // copy existing todos
        List<Todo> existingTodos =  user.getTodos().stream().filter(t -> t.getId() != todoId)
                .collect(Collectors.toList());
        for(Todo todo1 : existingTodos){
            newUser.getTodos().add(todo1);
        }
        updatedTodos.add(newTodo);
        newUser.getTodos().addAll(updatedTodos);
        return newUser;
    }

    public int getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean getIsComplete() {
        return isComplete;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Todo todo)) return false;
        return getId() == todo.getId() && isComplete() == todo.isComplete() && Objects.equals(getUser(), todo.getUser()) && Objects.equals(getName(), todo.getName()) && Objects.equals(getDescription(), todo.getDescription()) && Objects.equals(getDueDate(), todo.getDueDate()) && getPriority() == todo.getPriority();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUser(), getName(), getDescription(), isComplete(), getDueDate(), getPriority());
    }
}
