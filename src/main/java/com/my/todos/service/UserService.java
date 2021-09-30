package com.my.todos.service;

import com.my.todos.authentication.jwt.auth.resource.JwtTokenRequest;
import com.my.todos.custom.exceptions.UserAlreadyExistException;
import com.my.todos.custom.exceptions.UserNotFoundException;
import com.my.todos.dto.DuplicateUserDTO;
import com.my.todos.dto.UserDTO;
import com.my.todos.model.User;
import com.my.todos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author JayendraB
 * Created on 11/09/21
 */
@Service
public class UserService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    public void createUser(JwtTokenRequest user){
        User newUser = new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), "USER");
        try {
            userRepository.save(newUser);
        }
        catch (Exception e){
            throw new UserAlreadyExistException("USER_ALREADY_EXIST",e);
        }
    }

    public DuplicateUserDTO isUserExistInDB(String username){
        Optional<User> user = userRepository.findByUsername(username);
        if(user.isEmpty())
            return new DuplicateUserDTO("Username is present");
        else
            throw new UserAlreadyExistException("USER_ALREADY_EXIST",null);
    }

    public void changePasswordInDB(UserDTO userDTO){
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());
        if(user.isEmpty())
            throw new UserNotFoundException("USER_NOT_FOUND",null);
        else {
            user.get().setPassword(passwordEncoder.encode(userDTO.getPassword()));
            userRepository.save(user.get());
        }
    }
}
