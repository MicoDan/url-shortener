package com.url.shortener.security.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Response object for successful authentication
 * Contains the JWT token for the authenticated user
 */
@Data
@AllArgsConstructor
public class JwtAuthenticationResponse {
    // JWT token for authenticated user
    private String token;
}
