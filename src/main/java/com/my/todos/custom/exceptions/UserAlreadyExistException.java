package com.my.todos.custom.exceptions;

/**
 * @author JayendraB
 * Created on 11/09/21
 */
public class UserAlreadyExistException extends RuntimeException{
    public UserAlreadyExistException(String message, Throwable cause){
        super(message, cause);
    }
}
