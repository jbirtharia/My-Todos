package com.my.todos.controller;

import com.my.todos.model.AuthenticationBean;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author JayendraB
 * Created on 06/09/21
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
public class BasicAuthenticationController {

    @GetMapping("/basic-auth")
    public AuthenticationBean isUserAuthenticated(){
        return new AuthenticationBean("You are authenticated");
    }
}
