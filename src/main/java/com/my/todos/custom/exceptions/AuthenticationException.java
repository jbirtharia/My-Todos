package com.my.todos.custom.exceptions;

/**
 * @author JayendraB
 * Created on 09/09/21
 */
public class AuthenticationException extends RuntimeException {
	public AuthenticationException(String message, Throwable cause) {
		super(message, cause);
	}
}