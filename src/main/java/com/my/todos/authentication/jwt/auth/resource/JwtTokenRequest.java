package com.my.todos.authentication.jwt.auth.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author JayendraB
 * Created on 09/09/21
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenRequest implements Serializable {

	private static final long serialVersionUID = -5616176897013108345L;

	private String username;
	private String password;

}