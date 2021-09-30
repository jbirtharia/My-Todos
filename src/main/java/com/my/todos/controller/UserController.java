package com.my.todos.controller;

import com.my.todos.authentication.jwt.auth.resource.JwtTokenRequest;
import com.my.todos.dto.DuplicateUserDTO;
import com.my.todos.dto.UserDTO;
import com.my.todos.model.Welcome;
import com.my.todos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author JayendraB
 * Created on 11/09/21
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/users")
    public ResponseEntity<Void> createNewUser(@RequestBody JwtTokenRequest user){
        userService.createUser(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{username}")
    public DuplicateUserDTO isUsernameExist(@PathVariable String username){
        return userService.isUserExistInDB(username);
    }

    @PostMapping("/users/changePassword")
    public ResponseEntity<Void> changePassword(@RequestBody UserDTO user){
        userService.changePasswordInDB(user);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/message")
    public Welcome welcomeMessage() {
        throw new RuntimeException("Some Error has happened!!! Contact to Admin. ");
    }

    @GetMapping("/message/{user}")
    public Welcome welcomeMessageWithUser(@PathVariable String user) {
        user = user.substring(0,user.lastIndexOf("@"));
        return new Welcome("Welcome " + user + ".");
    }
}
