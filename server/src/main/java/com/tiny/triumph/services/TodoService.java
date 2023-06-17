package com.tiny.triumph.services;

import com.tiny.triumph.exceptions.ResourceNotFoundException;
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

    public TodoService(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    public TodoService(){
    }

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
        Todo todo = new Todo(copy.getName(), copy.getDescription(), copy.isComplete(), copy.getDueDate(), copy.getPriority(), user.get());
        todoRepository.save(todo);
        // This should be user.addTodo()
        user.get().getTodos().add(todo);
        userRepository.save(user.get());
        return todo;
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
    @Transactional
    public Optional<Todo> findToDoByDescription(String description) throws ResourceNotFoundException {
       return Optional.ofNullable(todoRepository.findToDoByDescription(description).orElseThrow(() -> new ResourceNotFoundException("Todo not found")));
    }


}

