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
public class JwtTokenResponse implements Serializable {

	private static final long serialVersionUID = 8317676219297719109L;

	private String token;

	private String refreshTokenPath;
}