package com.tiny.triumph.services;

import com.tiny.triumph.model.Todo;
import com.tiny.triumph.model.User;
import com.tiny.triumph.repositories.TodoRepository;
import com.tiny.triumph.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Optional<Todo> findById(int todoId){
        return todoRepository.findById(todoId);
    }


    @Transactional
    public Todo addTodo(int userId, Todo copy) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        Todo todo = new Todo(copy.getDescription(), copy.isComplete(), copy.getDueDate(), copy.getPriority(), user.get());
        todoRepository.save(todo);
        // This should be user.addTodo()
        user.get().getTodos().add(todo);
        userRepository.save(user.get());
        return todo;
    }

    @Transactional
    public void addTodos(int userId, List<Todo> addMe) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        user.get().getTodos().addAll(addMe);
        System.out.println("Todos added for user with id " + user.get().getId());
    }
    // This should optionally pass both the description and isCompleted
    @Transactional
    public void updateTodo(Todo updatedTodo, User user){
        User updatedUser = new User(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());

        List<Todo> withoutTodo = user.getTodos().stream().filter(todo -> todo.getId() != updatedTodo.getId()).collect(Collectors.toList());
        updatedUser.getTodos().addAll(withoutTodo);
        updatedUser.getTodos().add(updatedTodo);
        userRepository.save(updatedUser);
    }
    @Transactional
    public void deleteTodo(String id){

        Optional<Todo> existingTodo = todoRepository.findById(Integer.parseInt(id));

        if(existingTodo.isEmpty()){
            throw new EntityNotFoundException("Todo with " + existingTodo.get().getId()+ " not found");
        }
        todoRepository.delete(existingTodo.get());
    }


}

