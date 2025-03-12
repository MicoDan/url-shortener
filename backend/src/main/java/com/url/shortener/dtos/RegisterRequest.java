package com.url.shortener.dtos;

import lombok.Data;

import java.util.Set;

/**
 * Data Transfer Object for user registration requests
 * Contains all necessary information for creating a new user
 */
@Data
public class RegisterRequest {
    // Username for the new account
    private String username;
    
    // Email address for the account
    private String email;
    
    // Set of roles assigned to the user
    private Set<String> role;
    
    // Password for the account
    private String password;
}
