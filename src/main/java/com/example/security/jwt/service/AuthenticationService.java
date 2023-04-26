package com.example.security.jwt.service;

import com.example.security.jwt.dto.AuthenticationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtService jwtService;

    public String authenticateUser(AuthenticationDto dto) {
        Authentication authentication= authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails user = userDetailsService.loadUserByUsername(authentication.getName());
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<String> rolesNames = new ArrayList<>();
        user.getAuthorities().forEach(r-> rolesNames.add(r.getAuthority()));
        return jwtService.generateToken(user.getUsername(),rolesNames);
    }
}
