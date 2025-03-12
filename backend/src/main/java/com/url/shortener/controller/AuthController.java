package com.url.shortener.controller;

import com.url.shortener.dtos.LoginRequest;
import com.url.shortener.dtos.RegisterRequest;
import com.url.shortener.models.User;
import com.url.shortener.service.RateLimiterService;
import com.url.shortener.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

/**
 * Controller responsible for handling authentication-related endpoints
 * including user login and registration
 */
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private UserService userService;
    private RateLimiterService rateLimiterService;

    /**
     * Handles user login requests
     * @param loginRequest Contains user login credentials
     * @param request HTTP request object to get client IP
     * @return ResponseEntity containing JWT token on success or error message
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        // Get client IP address for rate limiting
        String clientIp = request.getRemoteAddr();

        // Check if request is within rate limits
        if (!rateLimiterService.allowRequest(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded!");
        }

        return ResponseEntity.ok(userService.authenticateUser(loginRequest));
    }

    /**
     * Handles new user registration
     * @param registerRequest Contains user registration details
     * @param request HTTP request object to get client IP
     * @return ResponseEntity with success message or error
     */
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) {
        // Get client IP address for rate limiting
        String clientIp = request.getRemoteAddr();

        // Check if request is within rate limits
        if (!rateLimiterService.allowRequest(clientIp)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body("Rate limit exceeded!");
        }

        // Create new user object from registration request
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(registerRequest.getPassword());
        user.setEmail(registerRequest.getEmail());
        user.setRole("ROLE_USER");
        
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }
}