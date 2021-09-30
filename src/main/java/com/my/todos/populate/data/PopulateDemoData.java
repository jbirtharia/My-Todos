package com.my.todos.populate.data;

import com.my.todos.model.Todo;
import com.my.todos.model.User;
import com.my.todos.repository.TodoRepository;
import com.my.todos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * @author JayendraB
 * Created on 10/09/21
 */
@Component
public class PopulateDemoData implements ApplicationRunner {

    @Autowired
    TodoRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        repository.save(new Todo("jbirtharia", "Learn Java", LocalDate.now(), true));
        repository.save(new Todo("jbirtharia", "Learn Angular", LocalDate.now(), true));
        repository.save(new Todo("jbirtharia", "Learn JavaScript", LocalDate.now(), true));
        repository.save(new Todo("jbirtharia", "Learn NodeJS", LocalDate.now(), false));
        repository.save(new Todo("jbirtharia", "Learn Python", LocalDate.now(), false));
        repository.save(new Todo("sachin", "Learn Java", LocalDate.now(), false));
        repository.save(new Todo("sachin", "Learn Python", LocalDate.now(), false));

        repository.flush();

        userRepository.save(new User("jbirtharia@gmail.com",passwordEncoder.encode("123"),"ADMIN"));
        userRepository.save(new User("sachin@gmail.com",passwordEncoder.encode("123"),"USER"));

    }
}
