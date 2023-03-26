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
    public List<Todo> findAllTodos(int userId){
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        return user.get().todos;
    }

    @Transactional
    public Optional<Todo> findById(int todoId){
        Optional<Todo> todo = todoRepository.findById(todoId);
        return todo;
    }


    @Transactional
    public Todo addTodo(int userId, Todo copy) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        Todo todo = new Todo(copy.getDescription(), copy.isComplete(), copy.getDueDate(), user.get());
        todoRepository.save(todo);
        todo.addTodo(todo,user.get());
        userRepository.save(user.get());
        return todo;
    }

    @Transactional
    public void addTodos(int userId, List<Todo> addMe) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }
        user.get().todos.addAll(addMe);
        System.out.println("Todos added for user with id " + user.get().id);
    }
    // This should optionally pass both the description and isCompleted
    @Transactional
    public void updateTodo(Todo updatedTodo, User user){

        List<Todo> updateMe = user.todos.stream().filter(todo -> todo.getId() == updatedTodo.getId()).collect(Collectors.toList());
        Optional<Todo> existingTodo = todoRepository.findById(updatedTodo.getId());

        if(existingTodo.isEmpty()){
            throw new EntityNotFoundException("Todo with " + updatedTodo.getId() + " not found");
        }
        existingTodo.get().setDescription(updatedTodo.description);
        existingTodo.get().setComplete(updatedTodo.isComplete());
        existingTodo.get().setDueDate(updatedTodo.getDueDate());

        todoRepository.save(existingTodo.get());
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

