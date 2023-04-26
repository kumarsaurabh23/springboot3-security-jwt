package com.example.security.jwt.controller;

import com.example.security.jwt.dto.AuthenticationDto;
import com.example.security.jwt.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/security")
public class Controller {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/home")
    public String welcome() {
        return "Welcome to Spring Jwt Security!";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Welcome Admin User!";
    }

    @GetMapping("/normal")
    public String normal() {
        return "Welcome Normal User!";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestBody AuthenticationDto dto) {
        return authenticationService.authenticateUser(dto);
    }
}
