package com.my.todos.repository;

import com.my.todos.model.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author JayendraB
 * Created on 29/08/21
 */
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    List<Todo> findByUser(String username);

}
