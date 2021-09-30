package com.my.todos.controller;

import com.my.todos.model.Todo;
import com.my.todos.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

/**
 * @author JayendraB
 * Created on 29/08/21
 */

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class TodoController {

    @Autowired
    TodoService todoService;

    @GetMapping("/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
      return todoService.retrieveTodos(username);
    }

    @GetMapping("/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable Integer id) {
        return todoService.getTodoById(id);
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<Void> saveTodo(@PathVariable String username, @RequestBody Todo todo) {
        Todo createdTodo = todoService.saveOrUpdateTodo(todo, username);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/users/{username}/todos")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username, @RequestBody Todo todo) {
        return new ResponseEntity<>(todoService.saveOrUpdateTodo(todo, username), HttpStatus.OK);
    }

    @DeleteMapping("/users/{username}/todos/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable Integer id) {
        if(null != todoService.deleteTodoById(id)){
            return  ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
