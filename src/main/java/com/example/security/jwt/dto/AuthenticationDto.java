package com.example.security.jwt.dto;

import lombok.Data;

@Data
public class AuthenticationDto {

    private String username;
    private String password;
}
