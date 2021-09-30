package com.my.todos.controller;


import javax.servlet.http.HttpServletRequest;

import com.my.todos.authentication.jwt.auth.JWTAuthenticationAndAuthorizationManager;
import com.my.todos.authentication.jwt.auth.JwtTokenUtil;
import com.my.todos.custom.exceptions.AuthenticationException;
import com.my.todos.authentication.jwt.auth.resource.JwtTokenRequest;
import com.my.todos.authentication.jwt.auth.resource.JwtTokenResponse;
import com.my.todos.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * @author JayendraB
 * Created on 09/09/21
 */
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationRestController {

	@Value("${jwt.http.request.header}")
	private String tokenHeader;

	@Value("${jwt.refresh.token.uri}")
	private String refreshTokenUri;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@Autowired
	private JWTAuthenticationAndAuthorizationManager jwtAuthenticationAndAuthorizationManager;

	@PostMapping("${jwt.get.token.uri}")
	public ResponseEntity<JwtTokenResponse> createAuthenticationToken(@RequestBody JwtTokenRequest authenticationRequest)
			throws AuthenticationException {

		jwtAuthenticationAndAuthorizationManager.authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtTokenResponse(token,refreshTokenUri));
	}

	@GetMapping("${jwt.refresh.token.uri}")
	public ResponseEntity<Object> refreshAndGetAuthenticationToken(HttpServletRequest request) {
		String authToken = request.getHeader(tokenHeader);
		final String token = authToken.substring(7);
		String username = jwtTokenUtil.getUsernameFromToken(token);
		User user = (User) jwtInMemoryUserDetailsService.loadUserByUsername(username);

		if(null != user) {
			if (Boolean.TRUE.equals(jwtTokenUtil.canTokenBeRefreshed(token))) {
				String refreshedToken = jwtTokenUtil.refreshToken(token);
				return ResponseEntity.ok(new JwtTokenResponse(refreshedToken, refreshTokenUri));
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		}
		else {
			return ResponseEntity.badRequest().body(null);
		}
	}
}
