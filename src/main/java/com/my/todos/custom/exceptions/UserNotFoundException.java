package com.my.todos.custom.exceptions;

/**
 * @author JayendraB
 * Created on 11/09/21
 */
public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message, Throwable cause){
        super(message, cause);
    }
}
