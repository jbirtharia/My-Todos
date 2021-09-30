package com.my.todos.authentication.jwt.auth;

import com.my.todos.custom.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author JayendraB
 * Created on 10/09/21
 */
@Component
public class JWTAuthenticationAndAuthorizationManager {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService jwtUserDetailsService;

    public void authenticate(String username, String password) {
        if(null != username && null != password) {
            try {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            } catch (DisabledException e) {
                throw new AuthenticationException("USER_DISABLED", e);
            } catch (BadCredentialsException e) {
                throw new AuthenticationException("INVALID_CREDENTIALS", e);
            }
        }
        else {
            throw new AuthenticationException("INVALID_CREDENTIALS", null);
        }
    }

    public Boolean isUserAdmin(){
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(null != userDetails) {
            Optional<? extends GrantedAuthority> simpleGrantedAuthority =
                    userDetails.getAuthorities().stream().filter(role -> role.getAuthority().equals("ADMIN")).findFirst();
            if (simpleGrantedAuthority.isEmpty())
                throw new AuthenticationException("USER_NOT_AUTHORIZED_TO_TAKE_ACTION", null);
            else
                return true;
        }
        else
            throw new AuthenticationException("USER_NOT_AUTHENTICATED", null);
    }
}
