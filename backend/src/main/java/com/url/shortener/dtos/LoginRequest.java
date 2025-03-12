package com.url.shortener.dtos;

import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for user login requests
 * Contains credentials needed for authentication
 */
@Data
public class LoginRequest {
    // User's username for login
    private String username;
    
    // User's password for authentication
    private String password;
}
