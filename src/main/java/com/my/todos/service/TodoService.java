package com.my.todos.service;

import com.my.todos.authentication.jwt.auth.JWTAuthenticationAndAuthorizationManager;
import com.my.todos.custom.exceptions.UserNotFoundException;
import com.my.todos.model.Todo;
import com.my.todos.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author JayendraB
 * Created on 29/08/21
 */

@Service
public class TodoService {

    @Autowired
    private TodoRepository repository;

    @Autowired
    private JWTAuthenticationAndAuthorizationManager jwtAuthenticationAndAuthorizationManager;

    public List<Todo> retrieveTodos(String username) {
        try {
            username = username.substring(0, username.lastIndexOf("@"));
            return repository.findByUser(username);
        } catch (Exception e) {
            throw new UserNotFoundException("USER_NOT_FOUND", e);
        }
    }

    public Todo getTodoById(Integer id){
        Optional<Todo> isTodoPresent = repository.findById(id);
        return isTodoPresent.orElse(null);
    }

    public Todo saveOrUpdateTodo(Todo todo, String username){
        if(Boolean.TRUE.equals(jwtAuthenticationAndAuthorizationManager.isUserAdmin())) {
            username = username.substring(0, username.lastIndexOf("@"));
            todo.setUser(username);
            if (null != todo.getId()) {
                repository.findById(todo.getId());
            }
            return repository.save(todo);
        }
        return null;
    }

    public Todo deleteTodoById(Integer id){
        if(Boolean.TRUE.equals(jwtAuthenticationAndAuthorizationManager.isUserAdmin())) {
            Optional<Todo> isTodoPresent = repository.findById(id);
            if (isTodoPresent.isPresent()) {
                Todo todo = isTodoPresent.get();
                repository.delete(todo);
                return todo;
            }
        }
        return null;
    }
}
